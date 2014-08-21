/**
 * Created by unlue on 21/08/14.
 */
public class KdTree {

    private Node root;
    private int size;

    public KdTree() {
        // construct an empty set of points
        root = null;
    }
    public boolean isEmpty() {
        return root == null;
    }
    public int size() {
        // number of points in the set
        return size;
    }
    public void insert(Point2D p) {
        // add the point p to the set (if it is not already in the set)

        int depth = 0;

        root = insert(root, p, depth);
    }

    public boolean contains(Point2D p) {
        // does the set contain the point p?
        throw new UnsupportedOperationException("Not Implemented");
    }
    public void draw() {
        // draw all of the points to standard draw
        throw new UnsupportedOperationException("Not Implemented");
    }
    public Iterable<Point2D> range(RectHV rect) {
        // all points in the set that are inside the rectangle
        throw new UnsupportedOperationException("Not Implemented");
    }
    public Point2D nearest(Point2D p) {
        // a nearest neighbor in the set to p; null if set is empty
        throw new UnsupportedOperationException("Not Implemented");
    }

    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree
    }


    private Node createNode(Point2D p) {
        Node nd = new Node();
        nd.p = p;
        nd.lb = null;
        nd.rt = null;

        size++;

        return nd;
    }

    private Node insert(Node n, Point2D point2D, int depth) {

        if (n == null) {
            return createNode(point2D);
        }

        int nextDepth = depth + 1;

        if (depth % 2 == 0) {
            //check x coordinate

            int xComp = Double.compare(n.p.x(), point2D.x());
            insertInner(n, point2D, nextDepth, xComp);
        }
        else {
            //check y coordinate
            int yComp = Double.compare(n.p.y(), point2D.y());
            insertInner(n, point2D, nextDepth, yComp);
        }

        return n;
    }

    private void insertInner(Node n, Point2D point2D, int nextDepth, int comp) {
        if (comp > 0) {
            n.lb = insert(n.lb, point2D, nextDepth);
        }
        else if (comp < 0) {
            n.rt = insert(n.rt, point2D, nextDepth);
        }
    }
}
