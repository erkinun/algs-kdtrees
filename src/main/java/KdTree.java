import java.util.ArrayList;
import java.util.List;

/**
 * Created by unlue on 21/08/14.
 */
public class KdTree {

    private Node root;
    private int size;

    public KdTree() {
        // construct an empty set of points
        root = null;
        size = 0;
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

        //roots parent is null
        root = insert(root, null,  p, depth);
    }

    public boolean contains(Point2D p) {
        // does the set contain the point p?

        if (root == null) {
            return false;
        }

        int depth = 0;
        return get(root, p, depth) != null;
    }

    public void draw() {
        //get the depth

        if (root == null) {
            return;
        }

        drawPoint(root);

        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setPenRadius();
        StdDraw.line(root.p.x(), 0.0, root.p.x(), 1.0);

        int depth = 1;
        drawInner(root.lb, depth);
        drawInner(root.rt, depth);
    }

    public Iterable<Point2D> range(RectHV rect) {
        // all points in the set that are inside the rectangle
        List<Point2D> pointList = new ArrayList<Point2D>();

        if (root != null && root.rect.intersects(rect)) {

            if (rect.contains(root.p)) {
                pointList.add(root.p);
            }

            innerRange(root.lb, rect, pointList);
            innerRange(root.rt, rect, pointList);
        }

        return pointList;
    }

    public Point2D nearest(Point2D p) {
        // a nearest neighbor in the set to p; null if set is empty

        if (root == null) {
            return null;
        }

        //last as the current nearest
        return nearestInner(p, root, root);
    }

    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree
    }


    private Node createNode(Point2D p, Node parent, int depth) {
        Node nd = new Node();
        nd.p = p;
        nd.lb = null;
        nd.rt = null;

        RectHV rect;
        if (parent == null) {
            //you are root
            rect = new RectHV(0.0, 0.0, 1.0, 1.0);
        }
        else {

            double xMin;
            double xMax;
            double yMin;
            double yMax;

            if (depth % 2 == 0) {
                //if you are vertical line, check
                int yComp = Double.compare(parent.p.y(), p.y());

                xMin = parent.rect.xmin();
                xMax = parent.rect.xmax();

                if (yComp > 0) {
                    //below rectangle
                    //xmin parent rect xmin
                    yMin = parent.rect.ymin();
                    yMax = parent.p.y();
                }
                else {
                    //above rectange
                    yMin = parent.p.y();
                    yMax = parent.rect.ymax();
                }

            }
            else {
                int xComp = Double.compare(parent.p.x(), p.x());

                yMin = parent.rect.ymin();
                yMax = parent.rect.ymax();

                if (xComp > 0) {
                    //left rectangle
                    xMax = parent.p.x();
                    xMin = parent.rect.xmin();
                }
                else {
                    //right rectange
                    xMin = parent.p.x();
                    xMax = parent.rect.xmax();
                }
            }

            rect = new RectHV(xMin, yMin, xMax, yMax);
        }

        nd.rect = rect;
        size++;

        return nd;
    }

    private Node insert(Node n, Node parent, Point2D point2D, int depth) {

        if (n == null) {
            return createNode(point2D, parent, depth);
        }

        if (n.p.equals(point2D)) {
            //we already have the same node
            return n;
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
            n.lb = insert(n.lb, n, point2D, nextDepth);
        }
        else if (comp <= 0) {
            n.rt = insert(n.rt, n, point2D, nextDepth);
        }
    }

    private Node get(Node node, Point2D p, int depth) {

        if (node.p.equals(p)) {
            return node;
        }

        int nextDepth = depth + 1;
        if (depth % 2 == 0) {
            //check x
            int xComp = Double.compare(node.p.x(), p.x());
            return getInner(node, p, nextDepth, xComp);
        }
        else {
            //check y
            int yComp = Double.compare(node.p.y(), p.y());
            return  getInner(node, p, nextDepth, yComp);
        }

    }

    private Node getInner(Node node, Point2D p, int nextDepth, int comp) {
        if (comp > 0) {

            if (node.lb == null) {
                return null;
            }

            return get(node.lb, p, nextDepth);
        }
        else { //if (comp <= 0)

            if (node.rt == null) {
                return null;
            }

            return get(node.rt, p, nextDepth);
        }
    }

    private void drawInner(Node node, int depth) {

        //draw the node
        if (node == null) {
            return;
        }

        drawPoint(node);

        if (depth % 2 == 0) {
            //draw red vertical
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius();

            StdDraw.line(node.p.x(), node.rect.ymin(), node.p.x(), node.rect.ymax());

        }
        else {
            //draw blue horizontal
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius();

            StdDraw.line(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.p.y());

        }

        int nextDepth = depth + 1;

        drawInner(node.lb, nextDepth);
        drawInner(node.rt, nextDepth);
    }

    private void drawPoint(Node node) {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
        StdDraw.point(node.p.x(), node.p.y());
    }

    private void innerRange(Node node, RectHV rect, List<Point2D> pointList) {

        if (node == null) {
            return;
        }

        if (rect.contains(node.p)) {
            pointList.add(node.p);
        }

        if (node.lb != null && node.lb.rect.intersects(rect)) {
            innerRange(node.lb, rect, pointList);
        }

        if (node.rt != null && node.rt.rect.intersects(rect)) {
            innerRange(node.rt, rect, pointList);
        }
    }

    private Point2D nearestInner(Point2D p, Node node, Node nearest) {

        Node newNearest;

        if (Double.compare(node.p.distanceSquaredTo(p), nearest.p.distanceSquaredTo(p)) < 0) {
            newNearest = node;
        }
        else {
            newNearest = nearest;
        }

        double currentNearest = newNearest.p.distanceSquaredTo(p);

        double leftDist = Double.POSITIVE_INFINITY;
        if (node.lb != null) {
            leftDist = node.lb.rect.distanceSquaredTo(p);
        }

        double rightDist = Double.POSITIVE_INFINITY;
        if (node.rt != null) {
            rightDist = node.rt.rect.distanceSquaredTo(p);
        }

        if (Double.compare(currentNearest, leftDist) <= 0
                && Double.compare(currentNearest, rightDist) <= 0) {
            return newNearest.p;
        }

        if (Double.compare(leftDist, rightDist) < 0) {
            return nearestInner(p, node.lb, newNearest);
        }
        else {
            return nearestInner(p, node.rt, newNearest);
        }
    }
}
