import student.TestCase;

/**
 * Tests Handle.
 *
 * @author Dominic Jimeno (dmjimeno0105)
 * @version 1.0
 */
public class HandleTest extends TestCase {
    private Handle handle;

    /**
     * Sets up.
     */
    public void setUp() {
        handle = new Handle(13);
    }


    /**
     * Tests equals(Object o).
     */
    @SuppressWarnings("unlikely-arg-type")
    public void testEquals() {
        Handle equal = new Handle(13);
        Handle notEqual = new Handle(31);
        int integerHandle = 13;

        assertTrue(handle.equals(handle));
        assertFalse(handle.equals(null));
        assertTrue(handle.equals(equal));
        assertFalse(handle.equals(notEqual));
        assertFalse(handle.equals(integerHandle));
    }


    /**
     * Tests index().
     */
    public void testIndex() {
        assertEquals(13, handle.index());
    }


    /**
     * Tests setIndex(int index).
     */
    public void testSetIndex() {
        handle.setIndex(42);
        assertEquals(42, handle.index());
    }
}
