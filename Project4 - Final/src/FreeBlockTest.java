import student.TestCase;

/**
 * Tests FreeBlock.
 *
 * @author Dominic Jimeno (dmjimeno0105)
 * @version 1.0
 */
public class FreeBlockTest extends TestCase {
    private FreeBlock freeBlock;

    /**
     * Sets up.
     */
    public void setUp() {
        freeBlock = new FreeBlock(5, 10);
    }


    /**
     * Tests compareTo(FreeBlock o).
     */
    public void testCompareTo() {
        FreeBlock before = new FreeBlock(0, 10);
        FreeBlock same = new FreeBlock(5, 10);
        FreeBlock after = new FreeBlock(10, 10);

        assertTrue(before.compareTo(freeBlock) < 0);
        assertTrue(same.compareTo(freeBlock) == 0);
        assertTrue(after.compareTo(freeBlock) > 0);
    }


    /**
     * Tests equals(Object o).
     */
    @SuppressWarnings("unlikely-arg-type")
    public void testEquals() {
        FreeBlock equal = new FreeBlock(5, 10);
        FreeBlock notEqual = new FreeBlock(10, 5);
        String stringBlock = new String("(5,10)");

        assertTrue(freeBlock.equals(freeBlock));
        assertFalse(freeBlock.equals(null));
        assertTrue(freeBlock.equals(equal));
        assertFalse(freeBlock.equals(notEqual));
        assertFalse(freeBlock.equals(stringBlock));
    }


    /**
     * Tests index().
     */
    public void testIndex() {
        assertEquals(5, freeBlock.index());
    }


    /**
     * Tests setIndex(int index).
     */
    public void testSetIndex() {
        freeBlock.setIndex(6);
        assertEquals(6, freeBlock.index());
    }


    /**
     * Tests setSize(int size).
     */
    public void testSetSize() {
        freeBlock.setSize(11);
        assertEquals(11, freeBlock.size());
    }


    /**
     * Tests size().
     */
    public void testSize() {
        assertEquals(10, freeBlock.size());
    }


    /**
     * Tests toString().
     */
    public void testToString() {
        assertTrue(freeBlock.toString().equals("(5,10)"));
    }
}
