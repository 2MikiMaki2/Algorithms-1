import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Queue;

public class KdTree {

    private Node root;
    private int size;

    private static class Node {
        private final Point2D point;
        private RectHV rectangle;
        private Node lb;
        private Node rt;

        public Node(Point2D p, RectHV rect) {
            point = p;
            rectangle = rect;
            lb = null;
            rt = null;
        }
    }

    public KdTree() {
        size = 0;
    }
    public boolean isEmpty() {
        return size == 0;
    }
    public int size() {
        return size;
    }
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        root = insert(root, p, true, 0, 0, 1 , 1);
    }

    private Node insert(Node x, Point2D point, boolean splitVert, double xmin, double ymin, double xmax, double ymax) {
        if (x == null) {
            size++;
            return new Node(point, new RectHV(xmin, ymin, xmax, ymax));
        }

        x.rectangle = new RectHV(xmin, ymin, xmax, ymax);
        int cmp = cmp(point, x.point, splitVert);
        if (cmp < 0) {
            if (splitVert) {
                x.lb = insert(x.lb, point, false, xmin, ymin, x.point.x(), ymax);
            } else {
                x.lb = insert(x.lb, point, true, xmin, ymin, xmax, x.point.y());
            }
        } else if (cmp > 0) {
            if (splitVert) {
                x.rt = insert(x.rt, point, false, x.point.x(), ymin, xmax, ymax);
            } else {
                x.rt = insert(x.rt, point, true, xmin, x.point.y(), xmax, ymax);
            }
        }

        return x;
    }

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        return contains(root, p, true) != null;
    }

    private Node contains(Node x, Point2D point, boolean splitVert) {
        if (x == null) {
            return null;
        }

        int cmp = cmp(point, x.point, splitVert);
        if (cmp < 0) {
            return contains(x.lb, point, !splitVert);
        } else if (cmp > 0) {
            return contains(x.rt, point, !splitVert);
        }

        return x;
    }
    public void draw() {
        StdDraw.clear();
        draw(root, true);
    }

    private void draw(Node x, boolean splitVert) {
        if (x == null) {
            return;
        }

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        x.point.draw();

        if (splitVert) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius();
            RectHV vRect = new RectHV(x.point.x(), x.rectangle.ymin(), x.point.x(), x.rectangle.ymax());
            vRect.draw();
            draw(x.lb, false);
            draw(x.rt, false);
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius();
            RectHV hRect = new RectHV(x.rectangle.xmin(), x.point.y(), x.rectangle.xmax(), x.point.y());
            hRect.draw();
            draw(x.lb, true);
            draw(x.rt, true);
        }

    }
    public Iterable<Point2D> range(RectHV rect) {
        Queue<Point2D> pointsQ = new Queue<>();
        range(root, rect, pointsQ);
        return pointsQ;
    }

    private void range(Node x, RectHV rect, Queue<Point2D> queue) {
        if (x == null || !x.rectangle.intersects(rect)) {
            return;
        }

        if (rect.contains(x.point)) {
            queue.enqueue(x.point);
        }

        range(x.lb, rect, queue);
        range(x.rt, rect, queue);
    }
    public Point2D nearest(Point2D p) {
        if (isEmpty()) {
            return null;
        } else {
            return nearest(root, p, null);
        }
    }

    private Point2D nearest(Node x, Point2D point, Point2D nearest) {
        if (x != null) {
            if (nearest == null) {
                nearest = x.point;
            }

            if (point.distanceSquaredTo(nearest) >= x.rectangle.distanceSquaredTo(point)) {
                if (x.point.distanceSquaredTo(point) < nearest.distanceSquaredTo(point)) {
                    nearest = x.point;
                }

                if (x.lb != null && x.lb.rectangle.contains(point)) {
                    nearest = nearest(x.lb, point, nearest);
                    nearest = nearest(x.rt, point, nearest);
                } else {
                    nearest = nearest(x.rt, point, nearest);
                    nearest = nearest(x.lb, point, nearest);
                }
            }
        }

        return nearest;
    }

    private int cmp(Point2D p1, Point2D p2, boolean splitVert) {
        if (splitVert) {
            if (p1.x() < p2.x()) {
                return -1;
            } else if (p1.x() > p2.x()) {
                return 1;
            } else if (p1.y() < p2.y()) {
                return -1;
            } else if (p1.y() > p2.y()) {
                return 1;
            }
        } else {
            if (p1.y() < p2.y()) {
                return -1;
            } else if (p1.y() > p2.y()) {
                return 1;
            } else if (p1.x() < p2.x()) {
                return -1;
            } else if (p1.x() > p2.x()) {
                return 1;
            }
        }

        return 0;
    }

    public static void main(String[] args) {

    }
}
