import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import student.TestCase;

/**
 * MaxHeapTest
 *
 * @author dmjimeno0105
 */
public class MaxHeapTest extends TestCase {
    private MaxHeap heap;
    private String fileName;
    private File file;
    private int maxBuffers;
    private int numRecords;

    public void setUp() throws IOException {
        // leave original .bin file sample untouched
        String originalFileName = "sampleBlock10.bin";
        File originalFile = new File(originalFileName);
        // make copy of original .bin file sample
        fileName = originalFileName.replace(".bin", "Copy.bin");
        file = new File(fileName);
        copy(originalFile, file);

        maxBuffers = 4;
        numRecords = 10 * ByteFile.RECORDS_PER_BLOCK;
        heap = new MaxHeap(file, maxBuffers, numRecords);
    }


    /**
     * Tests size()
     *
     * @throws IOException
     */
    public void testSize() throws IOException {
        assertEquals(numRecords, heap.size());
        heap.close();
        file.delete();
    }


    /**
     * Tests leftChild()
     *
     * @throws IOException
     */
    public void testLeftChild() throws IOException {
        int pos = 42;
        assertEquals(2 * pos + 1, heap.leftChild(pos));
        heap.close();
        file.delete();
    }


    /**
     * Tests rightChild()
     *
     * @throws IOException
     */
    public void testRightChild() throws IOException {
        int pos = 42;
        assertEquals(2 * pos + 2, heap.rightChild(pos));
        heap.close();
        file.delete();
    }


    /**
     * Tests parent()
     *
     * @throws IOException
     */
    public void testParent() throws IOException {
        int pos = 42;
        assertEquals((pos - 1) / 2, heap.parent(pos));
        heap.close();
        file.delete();
    }


    /**
     * Tests isLeaf()
     *
     * @throws IOException
     */
    public void testIsLeaf() throws IOException {
        assertFalse(heap.isLeaf(42));
        assertTrue(heap.isLeaf(1024 * 10 - 1));
        heap.close();
        file.delete();
    }


    /**
     * Tests flush()
     *
     * @throws IOException
     */
    public void testFlush() throws IOException {
        int diskWrites = Statistics.diskWrites;
        int flushWrites = heap.flush();

        // if the buffer(s) was removed, the pool did not write to disk
        if (diskWrites == Statistics.diskWrites) {
            assertEquals(0, flushWrites);
        }
        // otherwise the buffer(s) did have to write to disk
        else {
            assertEquals(4, flushWrites);
        }

        heap.close();
        file.delete();
    }


    /**
     * Tests removeMax()
     *
     * @throws IOException
     */
    public void testRemoveMax() throws IOException {
        // ensure file is up to date with what was manipulated in buffer pool
        heap.flush();

        // obtain max directly from file
        @SuppressWarnings("resource")
        RandomAccessFile raf = new RandomAccessFile(fileName, "rw");
        byte[] bytes = new byte[Record.SIZE_IN_BYTES];
        raf.seek(0);
        raf.read(bytes);
        Record expectedMax = new Record(bytes);
        raf.close();

        Record actualMax = heap.removeMax();
        assertEquals(expectedMax.getKey(), actualMax.getKey());
        assertEquals(expectedMax.getValue(), actualMax.getValue());

        heap.close();
        file.delete();
    }


    private static void copy(File source, File dest) throws IOException {
        Files.copy(source.toPath(), dest.toPath());
    }
}
