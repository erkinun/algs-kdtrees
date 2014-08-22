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

        //roots parent is null
        root = insert(root, null,  p, depth);
    }

    public boolean contains(Point2D p) {
        // does the set contain the point p?
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
        drawInner(root.lb, root, depth);
        drawInner(root.rt, root, depth);
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
                    yMin = 0.0;
                    yMax = parent.p.y();
                }
                else {
                    //above rectange
                    yMin = parent.p.y();
                    yMax = 1.0;
                }

            }
            else {
                int xComp = Double.compare(parent.p.x(), p.x());

                yMin = parent.rect.ymin();
                yMax = parent.rect.ymax();

                if (xComp > 0) {
                    //left rectangle
                    xMax = parent.p.x();
                    xMin = 0.0;
                }
                else {
                    //right rectange
                    xMin = parent.p.x();
                    xMax = 1.0;
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
        else if (comp < 0) {
            n.rt = insert(n.rt, n, point2D, nextDepth);
        }
    }

    private Node get(Node node, Point2D p, int depth) {

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
            return get(node.lb, p, nextDepth);
        }
        else if (comp < 0) {
            return get(node.rt, p, nextDepth);
        }
        else {
            return node;
        }
    }

    private void drawInner(Node node, Node parent, int depth) {

        //draw the node
        if (node == null) {
            return;
        }

        drawPoint(node);

        if (depth % 2 == 0) {
            //draw red vertical
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius();
            if (parent.rt == node) {
                //draw top of parent
                StdDraw.line(node.p.x(), parent.p.y(), node.p.x(), 1.0);
            }
            else {
                //draw below parent
                StdDraw.line(node.p.x(), parent.p.y(), node.p.x(), 0.0);
            }
        }
        else {
            //draw blue horizontal
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius();

            if (parent.rt == node) {
                StdDraw.line(parent.p.x(), node.p.y(), 1.0, node.p.y());
            }
            else {
                StdDraw.line(0.0, node.p.y(), parent.p.x(), node.p.y());
            }
        }

        int nextDepth = depth + 1;

        drawInner(node.lb, node, nextDepth);
        drawInner(node.rt, node, nextDepth);
    }

    private void drawPoint(Node node) {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
        StdDraw.point(node.p.x(), node.p.y());
    }
}
