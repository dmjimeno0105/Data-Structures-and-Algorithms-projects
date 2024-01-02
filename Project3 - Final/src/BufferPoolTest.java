import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import student.TestCase;

/**
 * Tests BufferPool
 *
 * @author dmjimeno0105
 */
public class BufferPoolTest extends TestCase {
    private BufferPool pool;
    private String fileName;
    private File file;

    public void setUp() throws IOException {
        // leave original .bin file sample untouched
        String originalFileName = "sampleBlock3.bin";
        File originalFile = new File(originalFileName);
        // make copy of original .bin file sample
        fileName = originalFileName.replace(".bin", "Copy.bin");
        file = new File(fileName);
        copy(originalFile, file);
        pool = new BufferPool(3, file);
    }


    /**
     * Tests buffers()
     *
     * @throws IOException
     */
    public void testBuffers() throws IOException {
        assertEquals(pool.capacity(), pool.buffers().length);
        pool.close();
        file.delete();
    }


    /**
     * Tests size()
     *
     * @throws IOException
     */
    public void testSize() throws IOException {
        // test buffer size starts at zero
        assertEquals(0, pool.size());
        pool.close();
        file.delete();
    }


    /**
     * Tests capacity()
     *
     * @throws IOException
     */
    public void testCapacity() throws IOException {
        // test buffer size starts at zero
        assertEquals(3, pool.capacity());
        pool.close();
        file.delete();
    }


    /**
     * Tests insert()
     *
     * @throws IOException
     */
    public void testInsert() throws IOException {
        // insert buffer into pool
        byte[] data = pool.read(0, 4096);
        pool.insert(0, data);

        // ensure buffer pool size increased by one
        assertEquals(1, pool.size());

        // fill up pool with last buffer being different from the rest
        // second buffer inserted at top
        data = pool.read(4096, 4096);
        pool.insert(4096, data);
        // third buffer inserted now at top
        data = pool.read(4096 * 2, 4096);
        pool.insert(4096 * 2, data);

        // current last buffer: block 1 (contains bytes 0 to 4095)
        Buffer lastBuffer = pool.buffers()[pool.capacity() - 1];
        byte[] lastBufferBytes = lastBuffer.data();
        ByteBuffer bb = ByteBuffer.allocate(ByteFile.BYTES_PER_BLOCK);
        ByteBuffer.wrap(lastBufferBytes);
        Record record = new Record(bb.getShort(0), bb.getShort(2));

        // insert buffer to push out last buffer from pool
        data = pool.read(0, 4096);
        pool.insert(0, data);
        // current last buffer: block 2
        lastBuffer = pool.buffers()[pool.capacity() - 1];
        lastBufferBytes = lastBuffer.data();
        bb = ByteBuffer.allocate(ByteFile.BYTES_PER_BLOCK);
        ByteBuffer.wrap(lastBufferBytes);
        Record record2 = new Record(bb.getShort(0), bb.getShort(2));
        // ensure last buffer was removed
        assertFalse(record.getKey() != record2.getKey());

        // insert buffer and update bytes in the current last buffer to
        // later write when flushed
        data = pool.read(4096, 4096);
        pool.insert(4096, data);
        // current last buffer: 3
        lastBuffer = pool.buffers()[pool.capacity() - 1];
        for (int i = 0; i < 4; i++) {
            lastBuffer.setByte(i, (byte)i);
        }
        lastBufferBytes = lastBuffer.data();
        bb = ByteBuffer.allocate(ByteFile.BYTES_PER_BLOCK);
        ByteBuffer.wrap(lastBufferBytes);
        Record record3 = new Record(bb.getShort(0), bb.getShort(2));
        // ensure last buffer was removed
        assertFalse(record2.getKey() != record3.getKey());

        // insert another buffer to write last buffer to disk
        data = pool.read(4096 * 2, 4096);
        pool.insert(4096 * 2, data);
        // current last buffer: 1
        lastBuffer = pool.buffers()[pool.capacity() - 1];
        for (int i = 0; i < 4; i++) {
            lastBuffer.setByte(i, (byte)i);
        }
        lastBufferBytes = lastBuffer.data();
        bb = ByteBuffer.allocate(ByteFile.BYTES_PER_BLOCK);
        ByteBuffer.wrap(lastBufferBytes);
        Record record4 = new Record(bb.getShort(0), bb.getShort(2));
        // ensure last buffer was removed
        assertFalse(record3.getKey() != record4.getKey());

        pool.close();
        file.delete();
    }


    /**
     * Tests find()
     *
     * @throws IOException
     */
    public void testFind() throws IOException {
        // makes sure no buffer with index 10 is found when initially empty
        assertEquals(null, pool.find(10));

        // insert buffer into pool
        byte[] data = pool.read(0, 4096);
        pool.insert(0, data);

        // create reference buffer directly from file
        @SuppressWarnings("resource")
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        byte[] bytes = new byte[ByteFile.BYTES_PER_BLOCK];
        raf.seek(0);
        raf.read(bytes);
        raf.close();
        Buffer buffer = new Buffer(0, bytes);

        // tests if the byte of index 10 is found within buffer that was just
        // inserted, confirming the buffer was found
        assertEquals(buffer.data()[10], pool.find(10).data()[10]);
        pool.close();
        file.delete();
    }


    /**
     * Tests flush()
     *
     * @throws IOException
     */
    public void testFlush() throws IOException {
        // nothing should have been written to disk when the buffer pool is
        // initially empty
        assertEquals(0, pool.flush());

        // still, nothing should have been written to disk since inserted buffer
        // has not been changed
        byte[] data = pool.read(0, 4096);
        pool.insert(0, data);
        assertEquals(0, pool.flush());

        // reinsert buffer
        pool.insert(0, data);
        // after buffer has been changed, the number writes (what flush()
        // returns), should be one
        pool.find(10).setByte(0, (byte)42);
        assertEquals(1, pool.flush());
        pool.close();
        file.delete();
    }


    /**
     * Tests read()
     *
     * @throws IOException
     */
    public void testRead() throws IOException {
        // read in data from buffer pool raf copy
        byte[] data = pool.read(0, 4096);

        // create reference data to compare
        @SuppressWarnings("resource")
        RandomAccessFile raf = new RandomAccessFile(file, "r");
        byte[] data2 = new byte[4096];
        raf.seek(0);
        raf.read(data2);
        raf.close();

        // check index 0 from both byte arrays
        assertEquals(data2[0], data[0]);
        pool.close();
        file.delete();
    }


    /**
     * Tests write()
     *
     * @throws IOException
     */
    public void testWrite() throws IOException {
        // create reference data to compare
        @SuppressWarnings("resource")
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        byte[] data2 = new byte[4096];
        raf.seek(0);
        raf.read(data2);
        raf.close();

        // read in and write new data into buffer pool raf copy
        byte[] data = pool.read(0, 4096);
        data[13] = (byte)42;
        pool.write(0, data);

        // ensure new buffer data does not equal old buffer data
        assertFalse(data2[13] == data[13]);
        // write in new byte data into old buffer reference
        data2[13] = (byte)42;
        // they should now be equal
        assertTrue(data2[13] == data[13]);
        pool.close();
        file.delete();
    }


    private static void copy(File source, File dest) throws IOException {
        Files.copy(source.toPath(), dest.toPath());
    }
}
