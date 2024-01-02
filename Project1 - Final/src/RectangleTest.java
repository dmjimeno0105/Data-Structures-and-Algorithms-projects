/**
 * Rectangle test
 *
 * @author Dominic Jimeno (dmjimeno0105)
 *
 * @version 9/5/2022
 *
 */
public class RectangleTest extends student.TestCase {
    private Rectangle reference;

    /**
     * Setup method for testing
     */
    public void setUp() {
        reference = new Rectangle("reference", 400, 400, 70, 50);
    }


    /**
     * Tests getter methods
     */
    public void testGetters() {
        Rectangle differentX = new Rectangle("referenceX", 410, 400, 70, 50);
        Rectangle differentY = new Rectangle("referenceY", 400, 420, 70, 50);
        // name()
        assertEquals("reference", reference.name());

        // x()
        assertEquals(400, reference.x());
        assertEquals(410, differentX.x());

        // y()
        assertEquals(400, reference.y());
        assertEquals(420, differentY.y());

        // width()
        assertEquals(70, reference.width());

        // height()
        assertEquals(50, reference.height());
    }


    /**
     * Tests valid()
     */
    public void testValid() {
        Rectangle outsideWorldBox = new Rectangle("invalid", -1, -1, 1, 1);
        Rectangle zeroWidth = new Rectangle("invalid", 0, 0, 0, 1);

        assertTrue(reference.valid());
        assertFalse(outsideWorldBox.valid());
        assertFalse(zeroWidth.valid());
    }


    /**
     * Tests toString()
     */
    public void testToString() {
        assertEquals("(reference, 400, 400, 70, 50)", reference.toString());
    }


    /**
     * Tests compareTo()
     */
    public void testCompareTo() {
        Rectangle referenceCopy = new Rectangle("reference", 400, 400, 70, 50);
        Rectangle smallerArea = new Rectangle("smallerArea", 400, 400, 60, 40);
        Rectangle equalArea = new Rectangle("equalArea", 400, 400, 70, 50);
        Rectangle largerArea = new Rectangle("largerArea", 400, 400, 80, 60);

        // rectangles exactly the same
        assertEquals(0, reference.compareTo(referenceCopy));

        // this rectangle's area is smaller
        assertEquals(-1, reference.compareTo(largerArea));

        // this rectangle's area is equal
        assertEquals(-1, reference.compareTo(equalArea));

        // this rectangle's area is larger
        assertEquals(1, reference.compareTo(smallerArea));
    }


    /**
     * Tests equals()
     */
    public void testEquals() {
        Rectangle equal = new Rectangle("reference", 400, 400, 70, 50);
        Rectangle notEqual = new Rectangle("notEqual", 400, 400, 70, 50);

        assertTrue(reference.equals(equal));
        assertFalse(reference.equals(notEqual));
    }


    /**
     * Tests rectangleIntersects()
     */
    public void testRectangleIntersects() {
        // reference = new Rectangle("reference", 400, 400, 70, 50);
        Rectangle topLeft = new Rectangle("topLeft", 350, 360, 70, 50);
        Rectangle top = new Rectangle("top", 400, 360, 70, 50);
        Rectangle topRight = new Rectangle("topRight", 450, 360, 70, 50);
        Rectangle right = new Rectangle("right", 440, 400, 70, 50);
        Rectangle bottomRight = new Rectangle("bottomRight", 450, 440, 70, 50);
        Rectangle bottom = new Rectangle("bottom", 400, 440, 70, 50);
        Rectangle bottomLeft = new Rectangle("bottomLeft", 350, 440, 70, 50);
        Rectangle left = new Rectangle("left", 360, 400, 70, 50);
        Rectangle completelyInside = new Rectangle("completelyInside", 410, 405,
            50, 40);
        Rectangle blanketOver = new Rectangle("blanketOver", 390, 395, 90, 60);
        Rectangle completelyOutside = new Rectangle("completelyOutside", 0, 0,
            50, 50);
        Rectangle abut = new Rectangle("abut", 470, 400, 70, 50);

        Rectangle outsideAbove = new Rectangle("outsideAbove", 400, 340, 70,
            50);
        Rectangle outsideBelow = new Rectangle("outsideBelow", 400, 460, 70,
            50);
        Rectangle outsideLeft = new Rectangle("outsideLeft", 320, 400, 70, 50);
        Rectangle outsideRight = new Rectangle("outsideRight", 480, 400, 70,
            50);

        assertTrue(reference.rectangleIntersects(topLeft));
        assertTrue(reference.rectangleIntersects(top));
        assertTrue(reference.rectangleIntersects(topRight));
        assertTrue(reference.rectangleIntersects(right));
        assertTrue(reference.rectangleIntersects(bottomRight));
        assertTrue(reference.rectangleIntersects(bottom));
        assertTrue(reference.rectangleIntersects(bottomLeft));
        assertTrue(reference.rectangleIntersects(left));
        assertTrue(reference.rectangleIntersects(completelyInside));
        assertTrue(reference.rectangleIntersects(blanketOver));
        assertFalse(reference.rectangleIntersects(completelyOutside));
        assertFalse(reference.rectangleIntersects(abut));
        assertTrue(topLeft.rectangleIntersects(reference));
        assertTrue(top.rectangleIntersects(reference));
        assertTrue(topRight.rectangleIntersects(reference));
        assertTrue(right.rectangleIntersects(reference));
        assertTrue(bottomRight.rectangleIntersects(reference));
        assertTrue(bottom.rectangleIntersects(reference));
        assertTrue(bottomLeft.rectangleIntersects(reference));
        assertTrue(left.rectangleIntersects(reference));
        assertTrue(completelyInside.rectangleIntersects(reference));
        assertTrue(blanketOver.rectangleIntersects(reference));
        assertFalse(completelyOutside.rectangleIntersects(reference));
        assertFalse(abut.rectangleIntersects(reference));

        assertFalse(reference.rectangleIntersects(outsideAbove));
        assertFalse(reference.rectangleIntersects(outsideBelow));
        assertFalse(reference.rectangleIntersects(outsideLeft));
        assertFalse(reference.rectangleIntersects(outsideRight));
    }
}
