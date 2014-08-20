import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;


public class PointSETTest {

    private PointSET pointSET;

    @Before
    public void setUp() {
        pointSET = new PointSET();
    }

    @Test
    public void testIsEmpty() throws Exception {
        Assert.assertTrue(pointSET.isEmpty());
    }

    @Test
    public void testNotEmpty() throws Exception {
        pointSET.insert(new Point2D(0.00, 0.00));
        Assert.assertFalse(pointSET.isEmpty());
    }

    @Test
    public void testSize() throws Exception {
        Assert.assertEquals(0, pointSET.size());
    }

    @Test
    public void testSizeIncrease() throws Exception {
        pointSET.insert(new Point2D(0.00, 0.00));
        Assert.assertEquals(1, pointSET.size());
    }

    @Test
    public void testInsert() throws Exception {
        Point2D point2D = new Point2D(0.00, 0.00);
        pointSET.insert(point2D);

        Assert.assertTrue(pointSET.contains(point2D));
        Assert.assertEquals(1, pointSET.size());
    }

    @Test
    public void testContains() throws Exception {
        Point2D point2D = new Point2D(0.00, 0.00);
        pointSET.insert(point2D);

        Assert.assertTrue(pointSET.contains(point2D));
    }

    @Test
    public void testDraw() throws Exception {

    }

    @Test
    public void testRange() throws Exception {

        Point2D point1 = new Point2D(0.10, 0.10);
        pointSET.insert(point1);
        Point2D point2 = new Point2D(0.20, 0.20);
        pointSET.insert(point2);
        Point2D point3 = new Point2D(0.30, 0.30);
        pointSET.insert(point3);
        Point2D point4 = new Point2D(2.00, 0.00);
        pointSET.insert(point4);

        RectHV rectHV = new RectHV(0.00, 0.00, 1.00, 1.00);
        Iterable<Point2D> points = pointSET.range(rectHV);

        Assert.assertNotNull(points);
    }

    @Test
    public void testNearest() throws Exception {
        Point2D point1 = new Point2D(0.10, 0.10);
        pointSET.insert(point1);
        Point2D point2 = new Point2D(0.20, 0.20);
        pointSET.insert(point2);
        Point2D point3 = new Point2D(0.30, 0.30);
        pointSET.insert(point3);
        Point2D point4 = new Point2D(2.00, 0.00);
        pointSET.insert(point4);

        Point2D nearest = pointSET.nearest(new Point2D(0.11, 0.11));

        Assert.assertTrue(nearest.equals(point1));
    }
}