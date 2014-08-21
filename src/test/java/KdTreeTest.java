import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class KdTreeTest {
    
    private KdTree kdTree;
    
    @Before
    public void setUp() throws Exception {
        kdTree = new KdTree();
    }

    @Test
    public void testIsEmpty() throws Exception {
        Assert.assertTrue(kdTree.isEmpty());
    }

    @Test
    public void testNotEmpty() throws Exception {
        kdTree.insert(new Point2D(0.00, 0.00));
        Assert.assertFalse(kdTree.isEmpty());
    }

    @Test
    public void testSize() throws Exception {
        Assert.assertEquals(0, kdTree.size());
    }

    @Test
    public void testSizeIncrease() throws Exception {
        kdTree.insert(new Point2D(0.00, 0.00));
        Assert.assertEquals(1, kdTree.size());
    }

    @Test
    public void testInsert() throws Exception {
        Point2D point2D = new Point2D(0.00, 0.00);
        kdTree.insert(point2D);

        Assert.assertTrue(kdTree.contains(point2D));
        Assert.assertEquals(1, kdTree.size());
    }

    @Test
    public void testContains() throws Exception {
        Point2D point2D = new Point2D(0.00, 0.00);
        kdTree.insert(point2D);

        Assert.assertTrue(kdTree.contains(point2D));
    }

    @Test
    public void testDraw() throws Exception {

    }

    @Test
    public void testRange() throws Exception {

        Point2D point1 = new Point2D(0.10, 0.10);
        kdTree.insert(point1);
        Point2D point2 = new Point2D(0.20, 0.20);
        kdTree.insert(point2);
        Point2D point3 = new Point2D(0.30, 0.30);
        kdTree.insert(point3);
        Point2D point4 = new Point2D(2.00, 0.00);
        kdTree.insert(point4);

        RectHV rectHV = new RectHV(0.00, 0.00, 1.00, 1.00);
        Iterable<Point2D> points = kdTree.range(rectHV);

        Assert.assertNotNull(points);
    }

    @Test
    public void testNearest() throws Exception {
        Point2D point1 = new Point2D(0.10, 0.10);
        kdTree.insert(point1);
        Point2D point2 = new Point2D(0.20, 0.20);
        kdTree.insert(point2);
        Point2D point3 = new Point2D(0.30, 0.30);
        kdTree.insert(point3);
        Point2D point4 = new Point2D(2.00, 0.00);
        kdTree.insert(point4);

        Point2D nearest = kdTree.nearest(new Point2D(0.11, 0.11));

        Assert.assertTrue(nearest.equals(point1));
    }
}