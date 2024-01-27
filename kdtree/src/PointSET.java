import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.RectHV;
import java.util.ArrayList;

/**
 * I, Maksim Pavlicic, attest that this code is my original work and was written in compliance with the class Academic
 * Integrity and Collaboration Policy found in the syllabus.
 */

public class PointSET {

    private SET<Point2D> pointSet;

    public PointSET() {
        pointSet = new SET<>();
    }
    public boolean isEmpty() {
        return pointSet.isEmpty();
    }
    public int size() {
        return pointSet.size();
    }
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        if (!contains(p)) {
            pointSet.add(p);
        }
    }
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        return pointSet.contains(p);
    }
    public void draw() {
        for (Point2D point : pointSet) {
            point.draw();
        }
    }
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }

        ArrayList<Point2D> points = new ArrayList<>();

        for (Point2D point : pointSet) {
            if (rect.contains(point)) {
                points.add(point);
            }
        }

        return points;
    }
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        if (isEmpty()) {
            return null;
        }

        Point2D nearest = null;
        for (Point2D point : pointSet) {
            if (nearest == null || p.distanceTo(point) < p.distanceTo(nearest)) {
                nearest = point;
            }
        }

        return nearest;
    }

    public static void main(String[] args) {

    }
}
