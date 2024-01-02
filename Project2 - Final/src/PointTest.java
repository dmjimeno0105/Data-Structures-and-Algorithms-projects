/**
 * Point class test
 *
 * @author Dominic Jimeno (dmjimeno0105)
 *
 * @version 9/27/2022
 *
 */
public class PointTest extends student.TestCase {
    private Point point;

    /**
     * Sets up testing
     */
    public void setUp() {
        point = new Point("name", 0, 0);
    }


    /**
     * Tests getter methods
     */
    public void testGetters() {
        assertEquals(0, point.x());
        assertEquals(0, point.y());
        assertTrue(point.name().equals("name"));
    }


    /**
     * Tests compareTo()
     */
    public void testCompareTo() {
        Point point1 = new Point("point1", 0, 0);
        Point point2 = new Point("point2", 0, 0);
        Point point3 = new Point("point3", 0, 0);

        assertEquals(1, point2.compareTo(point1));
        assertEquals(0, point2.compareTo(point2));
        assertEquals(-1, point2.compareTo(point3));
    }


    /**
     * Tests equalNames()
     */
    public void testEqualNames() {
        Point point1 = new Point("name", 1, 1);
        Point point2 = new Point("point2", 0, 0);

        assertTrue(point.equalsName(point1.name()));
        assertFalse(point.equalsName(point2.name()));
    }


    /**
     * Tests equalCoordinates()
     */
    public void testEqualCoorindates() {
        Point point1 = new Point("point1", 0, 0);
        Point point2 = new Point("point2", 1, 1);

        assertTrue(point.equalsCoordinates(point1));
        assertFalse(point.equalsCoordinates(point2));
    }


    /**
     * Tests equalsPoint()
     */
    public void testEqualsPoint() {
        Point point1 = new Point("name", 0, 0);
        Point point2 = new Point("point2", 1, 1);
        Point point3 = new Point("point3", 1, 1);

        assertTrue(point.equalsPoint(point1));
        assertFalse(point.equalsPoint(point2));
        assertFalse(point.equalsPoint(point3));
    }


    /**
     * Tests toString()
     */
    public void testToString() {
        assertTrue(point.toString().equals("name, 0, 0"));
    }
}
