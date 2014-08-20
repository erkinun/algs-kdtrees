import java.util.Iterator;
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
        // all points in the set that are inside the rectangle
        throw new UnsupportedOperationException("Not Implemented");
    }
    public Point2D nearest(Point2D p) {
        // a nearest neighbor in the set to p; null if set is empty
        throw new UnsupportedOperationException("Not Implemented");
    }

    public static void main(String[] args) {
    }
}
