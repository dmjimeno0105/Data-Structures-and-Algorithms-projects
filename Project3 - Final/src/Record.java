import java.nio.ByteBuffer;

/**
 * Record
 *
 * @author dmjimeno0105
 */
public class Record implements Comparable<Record> {
    /**
     * SIZE_IN_BYTES
     */
    public static final int SIZE_IN_BYTES = 4;
    /**
     * BYTE_INDEX_KEY
     */
    public static final int BYTE_INDEX_KEY = 0;
    /**
     * BYTE_INDEX_VALUE
     */
    public static final int BYTE_INDEX_VALUE = 2;
    /**
     * KEY_MAXIMUM
     */
    public static final int KEY_MAXIMUM = 30000;

    // This tiny ByteBuffer holds both the key and value as bytes
    private ByteBuffer bb;

    private byte[] bytes;

    /**
     * Makes a record and its backing ByteBuffer, useful for testing
     *
     * @param key
     * @param val
     */
    public Record(short key, short val) {
        bb = ByteBuffer.allocate(SIZE_IN_BYTES);
        bb.putShort(BYTE_INDEX_KEY, key);
        bb.putShort(BYTE_INDEX_VALUE, val);
        bytes = bb.array();
    }


    /**
     * getKey
     *
     * @return short
     */
    public short getKey() {
        return bb.getShort(BYTE_INDEX_KEY);
    }


    /**
     * getValue
     *
     * @return short
     */
    public short getValue() {
        return bb.getShort(BYTE_INDEX_VALUE);
    }


    /**
     * getBytes
     *
     * @return byte[]
     */
    public byte[] getBytes() {
        return bytes;
    }


    /**
     * getByte
     *
     * @param index
     * @return byte
     */
    public byte getByte(int index) {
        return bytes[index];
    }


    /**
     * Makes quick testing even easier
     *
     * @param key
     * @param val
     */
    public Record(int key, int val) {
        this((short)key, (short)val);
    }


    /**
     * Constructs using a given byte array. Does NOT copy but refers
     *
     * @param bytes
     */
    public Record(byte[] bytes) {
        bb = ByteBuffer.wrap(bytes);
        this.bytes = bytes;
    }


    /**
     * Makes a whole array of records that are backed by the given byte array
     * Caution: Changing the array will change records and vice versa!
     *
     * @param binaryData
     * @return Record[]
     */
    public static Record[] toRecArray(byte[] binaryData) {
        int numRecs = binaryData.length / SIZE_IN_BYTES;
        Record[] recs = new Record[numRecs];
        for (int i = 0; i < recs.length; i++) {
            int byteOffset = i * SIZE_IN_BYTES;
            ByteBuffer bb = ByteBuffer.wrap(binaryData, byteOffset,
                SIZE_IN_BYTES);
            recs[i] = new Record(bb.slice());
        }
        return recs;
    }


    // Constructs using a given byte buffer. Does NOT copy but refers
    private Record(ByteBuffer bb) {
        this.bb = bb;
    }


    /**
     * Copies the contents of another record. This is a DEEP copy.
     *
     * @param other
     */
    public void setTo(Record other) {
        bb.putShort(BYTE_INDEX_KEY, other.getKey());
        bb.putShort(BYTE_INDEX_VALUE, other.getValue());
    }


    @Override
    public int compareTo(Record o) {
        return Short.compare(this.getKey(), o.getKey());
    }


    // A nice overview of the Record's contents.
    public String toString() {
        StringBuilder sb = new StringBuilder("Record: (");
        sb.append(this.getKey());
        sb.append(", ");
        sb.append(this.getValue());
        sb.append(")");
        return sb.toString();
    }
}
