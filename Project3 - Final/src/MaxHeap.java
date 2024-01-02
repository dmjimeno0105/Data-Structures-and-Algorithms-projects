import java.io.File;
import java.io.IOException;

/**
 * Max-heap implementation by Patrick Sullivan, based on OpenDSA Heap code
 * Can use `java -ea` (Java's VM arguments) to Enable Assertions
 * These assertions will check for valid heap positions
 *
 * Update: removed unecessary methods
 *
 * Many of these methods are not going to be useful for ExternalSorting...
 * Prune those methods out if you don't want to test them.
 *
 * @author dmjimeno0105
 */
public class MaxHeap {
    private BufferPool pool;
    private int size; // number of records

    /**
     * Constructor supporting preloading of heap contents
     *
     * @param file
     * @param maxBuffers
     * @param numRecords
     *
     * @throws IOException
     */
    MaxHeap(File file, int maxBuffers, int numRecords) throws IOException {
        pool = new BufferPool(maxBuffers, file);
        size = numRecords;
        buildHeap();
    }


    /**
     * Closes any live streams from the pool
     *
     * @throws IOException
     */
    public void close() throws IOException {
        pool.close();
    }


    /**
     * Return current size of the heap
     *
     * @return int
     */
    public int size() {
        return size;
    }


    /**
     * Return position for left child of pos
     *
     * @param pos
     * @return int
     */
    public int leftChild(int pos) {
        return 2 * pos + 1;
    }


    /**
     * Return position for right child of pos
     *
     * @param pos
     * @return int
     */
    public int rightChild(int pos) {
        return 2 * pos + 2;
    }


    /**
     * Return position for the parent of pos
     *
     * @param pos
     * @return int
     */
    public int parent(int pos) {
        return (pos - 1) / 2;
    }


    /**
     * Return true if pos a leaf position, false otherwise
     *
     * @param pos
     * @return boolean
     */
    public boolean isLeaf(int pos) {
        return (size / 2 <= pos) && (pos < size);
    }


    /**
     * Organize contents of array to satisfy the heap structure
     *
     * @throws IOException
     */
    private void buildHeap() throws IOException {
        for (int i = parent(size - 1); i >= 0; i--) {
            siftDown(i);
        }
    }


    /**
     * Moves an element down to its correct place
     *
     * @param pos
     * @throws IOException
     */
    private void siftDown(int pos) throws IOException {
        assert (0 <= pos && pos < size) : "Invalid heap position";
        while (!isLeaf(pos)) {
            int child = leftChild(pos);
            if ((child + 1 < size) && isGreaterThan(child + 1, child)) {
                child = child + 1; // child is now index with the greater value
            }
            if (!isGreaterThan(child, pos)) {
                return; // stop early
            }
            swap(pos, child);
            pos = child; // keep sifting down
        }
    }


    /**
     * Remove and return maximum value
     *
     * @return T
     * @throws IOException
     */
    public Record removeMax() throws IOException {
        assert size > 0 : "Heap is empty; cannot remove";
        size--;
        if (size > 0) {
            swap(0, size); // Swap maximum with last value
            siftDown(0); // Put new heap root val in correct place
        }
        return record(size);
    }


    // swaps the elements at two positions
    private void swap(int pos1, int pos2) throws IOException {
        Record temp = record(pos1);
        setRecord(pos1, record(pos2));
        setRecord(pos2, temp);
    }


    // does fundamental comparison used for checking heap validity
    private boolean isGreaterThan(int pos1, int pos2) throws IOException {
        return record(pos1).compareTo(record(pos2)) > 0;
    }


    /**
     * Remove, and write if necessary, all buffers in pool
     *
     * @return flushWrites
     * @throws IOException
     */
    public int flush() throws IOException {
        int flushWrites = pool.flush();
        Statistics.diskWrites += flushWrites;
        return flushWrites;
    }


    // Get record from buffer
    private Record record(int index) throws IOException {
        // gets buffer
        Buffer buffer = buffer(index);

        // going from record index to byte index
        // find start index of record within buffer
        int startIndexWithinBuffer = (index * Record.SIZE_IN_BYTES)
            % ByteFile.BYTES_PER_BLOCK;
        byte[] bufferData = buffer.data();
        byte[] recordBytes = new byte[Record.SIZE_IN_BYTES];
        // store bytes of record into recordBytes array
        for (int i = 0; i < Record.SIZE_IN_BYTES; i++) {
            recordBytes[i] = bufferData[startIndexWithinBuffer + i];
        }
        // convert into record object
        Record record = new Record(recordBytes);

        return record;
    }


    // Set record in buffer
    private void setRecord(int index, Record record) throws IOException {
        // gets buffer
        Buffer buffer = buffer(index);

        // going from record index to byte index
        // find start index of record within buffer
        int startIndexWithinBuffer = (index * Record.SIZE_IN_BYTES)
            % ByteFile.BYTES_PER_BLOCK;
        // set bytes of record into buffer; places record into buffer
        for (int i = 0; i < Record.SIZE_IN_BYTES; i++) {
            buffer.setByte(startIndexWithinBuffer + i, record.getByte(i));
        }
    }


    // Get buffer holding specified index
    private Buffer buffer(int index) throws IOException {
        // sees if buffer exists in pool
        Buffer buffer = pool.find(index * Record.SIZE_IN_BYTES);

        // if buffer was not found then create one from file
        if (buffer == null) {
            // obtains data from file (don't forget about integer division)
            int bufferIndex = ((index * Record.SIZE_IN_BYTES)
                / ByteFile.BYTES_PER_BLOCK) * ByteFile.BYTES_PER_BLOCK;
            // this is costly, but that's why we're using (Least Recently Used)
            // LRU replacement strategy
            byte[] data = pool.read(bufferIndex, ByteFile.BYTES_PER_BLOCK);
            Statistics.diskReads++;

            // if pool is full before insertion of another buffer, then increase
            // writes
            if (pool.size() == pool.capacity()) {
                Statistics.diskWrites++; // TODO: account for mutations here
            }

            // insert buffer into pool
            pool.insert(bufferIndex, data);
            Statistics.cacheMisses++;

            // now grab the buffer
            buffer = pool.find(index * Record.SIZE_IN_BYTES);
        }
        else {
            Statistics.cacheHits++;
        }

        return buffer;
    }
}
