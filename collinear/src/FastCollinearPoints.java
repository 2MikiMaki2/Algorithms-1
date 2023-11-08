import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private final LineSegment[] segments;

    public FastCollinearPoints(Point[] points) {
        for (int i = 0; i < points.length; ++i) {
            if (points[i] == null) {
                throw new java.lang.NullPointerException();
            }
        }

        Point[] pointsCopyOriginal = Arrays.copyOf(points, points.length);
        Point[] pointsCopySorted = Arrays.copyOf(points, points.length);
        ArrayList<LineSegment> segmentsList = new ArrayList<>();
        Arrays.sort(pointsCopySorted);
        checkRepeatedPoints(pointsCopySorted);
        for (int i = 0; i < pointsCopyOriginal.length; ++i) {
            Point origin = pointsCopyOriginal[i];
            Arrays.sort(pointsCopySorted);
            Arrays.sort(pointsCopySorted, origin.slopeOrder());
            int count = 1;
            Point startPointLine = null;
            for (int j = 0; j < pointsCopySorted.length - 1; ++j) {
                if (pointsCopySorted[j].slopeTo(origin) == pointsCopySorted[j + 1].slopeTo(origin)) {
                    count++;
                    if (count == 2) {
                        startPointLine = pointsCopySorted[j];
                        count++;
                    }
                    else if (count >= 4 && j + 1 == pointsCopySorted.length - 1) {
                        if (startPointLine.compareTo(origin) > 0) {
                            segmentsList.add(new LineSegment(origin, pointsCopySorted[j + 1]));
                        }
                        count = 1;
                    }
                }
                else if (count >= 4) {
                    if (startPointLine.compareTo(origin) > 0) {
                        segmentsList.add(new LineSegment(origin, pointsCopySorted[j]));
                    }
                    count = 1;
                }
                else {
                    count = 1;
                }
            }
        }
        segments = segmentsList.toArray(new LineSegment[segmentsList.size()]);
    }

    public int numberOfSegments() {
        return segments.length;
    }

    public LineSegment[] segments() {
        return Arrays.copyOf(segments, numberOfSegments());
    }

    private void checkRepeatedPoints(Point[] points) {
        for (int i = 0; i < points.length - 1; ++i) {
            if (points[i].compareTo(points[i+1]) == 0) {
                throw new java.lang.IllegalArgumentException();
            }
        }
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}