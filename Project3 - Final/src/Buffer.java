/**
 * Buffer which holds an index and byte array that corresponds to it's location
 * and data in the disk file. Also knows whether or not to write to disk file
 * based on dirty status.
 *
 * @author dmjimeno0105
 */
public class Buffer {
    private int index;
    private byte[] data;
    private boolean dirty;

    /**
     * Create a new Buffer object with an index and data
     *
     * @param index
     *            start location of buffer in disk file
     * @param data
     *            matching byte array from disk file
     */
    public Buffer(int index, byte[] data) {
        this.index = index;
        this.data = data;
        dirty = false;
    }


    /**
     * Returns index
     *
     * @return index
     */
    public int index() {
        return index;
    }


    /**
     * Returns data
     *
     * @return data
     */
    public byte[] data() {
        return data;
    }


    /**
     * Marks buffer dirty; when this buffer is cleared from the buffer pool, its
     * updated contents are written to disk
     */
    public void markDirty() {
        dirty = true;
    }


    /**
     * Checks whether this buffer is dirty or not
     *
     * @return dirty status
     */
    public boolean isDirty() {
        return dirty;
    }


    /**
     * Sets a value at desired index within data byte array
     *
     * @param index
     *            desired location within byte array
     * @param value
     *            to be set in byte array
     */
    public void setByte(int index, byte value) {
        data[index] = value;
        dirty = true;
    }
}
