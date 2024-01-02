import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * A collection of buffers using the Least Recently Used (LRU) replacement
 * strategy
 *
 * @author dmjimeno0105
 */
public class BufferPool {
    private Buffer[] buffers;
    private int max;
    private int size;
    private RandomAccessFile raf;

    /**
     * Create a new BufferPool object with a maximum number of allowed buffers
     *
     * @param max
     *            capacity
     * @param file
     *            random access
     * @throws FileNotFoundException
     */
    public BufferPool(int max, File file) throws FileNotFoundException {
        this.buffers = new Buffer[max];
        this.max = max;
        this.size = 0;
        raf = new RandomAccessFile(file, "rw");
    }


    /**
     * Return buffer array
     *
     * @return buffers
     */
    public Buffer[] buffers() {
        return buffers;
    }


    /**
     * Closes the random access file
     *
     * @throws IOException
     */
    public void close() throws IOException {
        raf.close();
    }


    /**
     * Inserts buffer with an index and data into buffer pool
     *
     * @param index
     *            start location of buffer in disk file
     * @param data
     *            matching byte array from disk file
     * @throws IOException
     */
    public void insert(int index, byte[] data) throws IOException {
        // If pool is full, makes room
        if (size == max) {
            // Writes the to-be-overwritten buffer to disk, if necessary
            if (buffers[buffers.length - 1].isDirty()) {
                write(buffers[buffers.length - 1].index(),
                    buffers[buffers.length - 1].data());
            }
        }

        // Shifts buffers down one and inserts new buffer at the top of the pool
        System.arraycopy(buffers, 0, buffers, 1, buffers.length - 1);
        this.buffers[0] = new Buffer(index, data);
        if (size < max)
            size++;
    }


    /**
     * Obtain the associated buffer containing the specified index
     *
     * @param index
     *            to be found within a buffer
     * @return buffer if found, null otherwise
     */
    public Buffer find(int index) {
        // Traverses buffers
        for (int i = 0; i < size; i++) {
            // If index is found within buffer, return buffer
            if (buffers[i].index() <= index && index < buffers[i].index()
                + ByteFile.BYTES_PER_BLOCK) {
                return buffers[i];
            }
        }

        // If no buffer found, return null
        return null;
    }


    /**
     * Remove, and write if necessary, all buffers in pool
     *
     * @return number of writes
     * @throws IOException
     */
    public int flush() throws IOException {
        int writes = 0;

        // Traverses buffers
        for (int i = 0; i < size; ++i) {
            // If buffer has been updated, write to disk
            if (buffers[i].isDirty()) {
                write(buffers[i].index(), buffers[i].data());
                writes++;
            }
            buffers[i] = null;
        }

        // Resets size of buffer pool
        size = 0;
        return writes;
    }


    /**
     * Read size data starting at index from disk file
     *
     * @param index
     *            position to be read from
     * @param size
     *            of data to be read
     * @return data
     * @throws IOException
     */
    @SuppressWarnings("hiding")
    public byte[] read(int index, int size) throws IOException {
        byte[] data = new byte[size];
        raf.seek(index);
        raf.read(data);

        return data;
    }


    /**
     * Write data starting at index to disk file
     *
     * @param index
     *            position to be written to
     * @param data
     *            to be written
     * @throws IOException
     */
    public void write(int index, byte[] data) throws IOException {
        raf.seek(index);
        raf.write(data);
    }


    /**
     * Return current size of buffer pool
     *
     * @return size
     */
    public int size() {
        return size;
    }


    /**
     * Return maximum buffer capacity of pool
     *
     * @return max
     */
    public int capacity() {
        return max;
    }
}
