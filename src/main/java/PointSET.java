import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by ERKIN on 20/08/2014.
 */
public class PointSET {

    private TreeSet<Point2D> treeSet;

    public PointSET() {
        // construct an empty set of points
        treeSet = new TreeSet<Point2D>();
    }
    public boolean isEmpty() {
        // is the set empty?
        return treeSet.isEmpty();
    }
    public int size() {
        // number of points in the set
        return treeSet.size();
    }
    public void insert(Point2D p) {
        // add the point p to the set (if it is not already in the set)
        treeSet.add(p);
    }
    public boolean contains(Point2D p) {
        // does the set contain the point p?
        return treeSet.contains(p);
    }
    public void draw() {
        // draw all of the points to standard draw

        Iterator<Point2D> point2DIterator = treeSet.descendingIterator();

        while (point2DIterator.hasNext()) {
            Point2D point2D = point2DIterator.next();
            StdDraw.point(point2D.x(), point2D.y());
        }
    }
    public Iterable<Point2D> range(RectHV rect) {

        Iterator<Point2D> items = treeSet.iterator();

        List<Point2D> point2Ds = new ArrayList<Point2D>();
        while (items.hasNext()) {
            Point2D point = items.next();

            if (rect.contains(point)) {
                point2Ds.add(point);
            }
        }

        return point2Ds;

    }
    public Point2D nearest(Point2D p) {
        // a nearest neighbor in the set to p; null if set is empty
        if (treeSet.isEmpty()) {
            return null;
        }

        Iterator<Point2D> items = treeSet.iterator();
        double nearestLen = Double.POSITIVE_INFINITY;
        Point2D nearest = null;
        while (items.hasNext()) {
            Point2D point2D = items.next();

            if (Double.compare(nearestLen, p.distanceTo(point2D)) > 0) {
                nearestLen = p.distanceTo(point2D);
                nearest = point2D;
            }
        }

        return nearest;
    }

    public static void main(String[] args) {
    }
}
