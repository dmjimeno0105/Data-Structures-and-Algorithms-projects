import student.TestCase;

/**
 * Tests Buffer
 *
 * @author dmjimeno0105
 */
public class BufferTest extends TestCase {
    private Buffer buffer;

    public void setUp() {
        int index = 0;
        byte[] data = { 1, 2, 3, 4, 5 };
        buffer = new Buffer(index, data);
    }


    /**
     * Tests index()
     */
    public void testIndex() {
        assertEquals(0, buffer.index());
    }


    /**
     * Tests data()
     */
    public void testData() {
        byte[] data = { 1, 2, 3, 4, 5 };
        for(int i = 0; i < data.length; i++) {
            assertEquals(data[i], buffer.data()[i]);
        }
    }


    /**
     * Tests setByte(int index, byte value)
     */
    public void testSetByte() {
        byte[] data = { 1, 2, 127, 4, 5 };
        buffer.setByte(2, (byte)127);
        assertEquals(data[2], buffer.data()[2]);
    }


    /**
     * Tests isDirty()
     */
    public void testIsDirty() {
        assertFalse(buffer.isDirty());
        buffer.setByte(4, (byte)6);
        assertTrue(buffer.isDirty());
    }


    /**
     * Tests markDirty()
     */
    public void testMarkDirty() {
        assertFalse(buffer.isDirty());
        buffer.markDirty();
        assertTrue(buffer.isDirty());
    }
}
