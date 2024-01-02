import java.nio.ByteBuffer;
import student.TestCase;

/**
 * RecordTest
 *
 * @author dmjimeno0105
 */
public class RecordTest extends TestCase {
    private Record record;

    public void setUp() {
        record = new Record((short)13, (short)42);
    }


    /**
     * Tests getKey()
     */
    public void testGetKey() {
        assertEquals((short)13, record.getKey());
    }


    /**
     * Tests getValue()
     */
    public void testGetValue() {
        assertEquals((short)42, record.getValue());
    }


    /**
     * Tests getBytes()
     */
    public void testGetBytes() {
        // create reference byte array
        ByteBuffer bb = ByteBuffer.allocate(Record.SIZE_IN_BYTES);
        bb.putShort((short)13);
        bb.putShort((short)42);
        byte[] byteArray = bb.array();

        for (int i = 0; i < 4; i++) {
            assertEquals(byteArray[i], record.getBytes()[i]);
        }
    }


    /**
     * Tests getByte()
     */
    public void testGetByte() {
        // create reference bytes
        ByteBuffer bb = ByteBuffer.allocate(Record.SIZE_IN_BYTES);
        bb.putShort((short)13);
        bb.putShort((short)42);
        byte byte0 = bb.array()[0];
        byte byte1 = bb.array()[1];
        byte byte2 = bb.array()[2];
        byte byte3 = bb.array()[3];

        assertEquals(byte0, record.getByte(0));
        assertEquals(byte1, record.getByte(1));
        assertEquals(byte2, record.getByte(2));
        assertEquals(byte3, record.getByte(3));
    }


    /**
     * Tests toRecArray()
     */
    public void testToRecArray() {
        // create reference record array
        Record record2 = new Record(2, 22);
        Record record3 = new Record(3, 33);
        Record[] referenceArray = { record, record2, record3 };

        // create record array
        ByteBuffer bb = ByteBuffer.allocate(Record.SIZE_IN_BYTES * 3);
        bb.putShort((short)13);
        bb.putShort((short)42);
        bb.putShort((short)2);
        bb.putShort((short)22);
        bb.putShort((short)3);
        bb.putShort((short)33);
        Record[] recordArray = Record.toRecArray(bb.array());

        assertEquals(3, recordArray.length);
        for (int i = 0; i < 3; i++) {
            assertEquals(referenceArray[i].getKey(), recordArray[i].getKey());
            assertEquals(referenceArray[i].getValue(), recordArray[i]
                .getValue());
        }
    }


    /**
     * Tests setTo()
     */
    public void testSetTo() {
        // byte buffer array to create new record
        ByteBuffer bb = ByteBuffer.allocate(Record.SIZE_IN_BYTES);
        bb.putShort((short)5);
        bb.putShort((short)2001);
        byte[] byteArray = bb.array();

        // create new record using byte array constructor
        Record newRecord = new Record(byteArray);

        record.setTo(newRecord);

        assertEquals(newRecord.getKey(), record.getKey());
        assertEquals(newRecord.getValue(), record.getValue());
    }


    /**
     * Tests compareTo()
     */
    public void testCompareTo() {
        Record larger = new Record(14, 3);
        Record same = new Record(13, 2);
        Record smaller = new Record(12, 1);

        // test when record key is smaller; expected -1
        assertTrue(record.compareTo(larger) < 0);
        // test when record key is same; expected 0
        assertTrue(record.compareTo(same) == 0);
        // test when record key is larger; expected 1
        assertTrue(record.compareTo(smaller) > 0);
    }


    /**
     * Tests toString()
     */
    public void testToString() {
        // create reference string
        String referenceString = "Record: (13, 42)";

        assertTrue(referenceString.equals(record.toString()));
    }
}
