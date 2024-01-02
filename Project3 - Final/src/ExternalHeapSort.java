import java.io.File;
import java.io.IOException;

/**
 * External HeapSort algorithm
 *
 * @author dmjimeno0105
 */
public class ExternalHeapSort {
    private MaxHeap heap;
    private int numRecords;

    /**
     * Constructor
     *
     * @param file
     *
     * @param maxBuffers
     *            max size of buffer pool
     * @param numRecords
     * @throws IOException
     */
    public ExternalHeapSort(File file, int maxBuffers, int numRecords)
        throws IOException {
        // The heap constructor invokes the buildHeap method
        heap = new MaxHeap(file, maxBuffers, numRecords);
        this.numRecords = numRecords;
    }


    /**
     * Closes any live streams being used by the MaxHeap BufferPool
     *
     * @throws IOException
     */
    public void close() throws IOException {
        heap.close();
    }


    /**
     * Method to start heap sort
     *
     * @throws IOException
     */
    public void sort() throws IOException {
        for (int i = 0; i < numRecords; i++) { // Now sort
            heap.removeMax(); // removeMax places max at end of heap
        }

        heap.flush();
    }
}
