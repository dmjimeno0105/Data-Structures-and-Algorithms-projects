import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import student.TestCase;
import student.TestableRandom;

/**
 * Tests ByteFile
 *
 * @author dmjimeno0105
 */
public class ByteFileTest extends TestCase {
    private String dataFileName;
    private File dataFile;
    private int numBlocks;
    private ByteFile byteFile;

    public void setUp() throws IOException {
        // leave original unsorted .bin file samples untouched
        String originalDataFileName = "sampleBlock1.bin";
        File originalDataFile = new File(originalDataFileName);
        // make copy of original .bin file sample
        dataFileName = originalDataFileName.replace(".bin", "Copy.bin");
        dataFile = new File(dataFileName);
        numBlocks = 1;
        copy(originalDataFile, dataFile);
        // use copy for byte file
        byteFile = new ByteFile(dataFileName, numBlocks);
    }


    /**
     * Test isSorted()
     *
     * @throws IOException
     */
    public void testIsSorted() throws IOException {
        assertFalse(byteFile.isSorted());

        // then sort
        ExternalHeapSort heap = new ExternalHeapSort(dataFile, 1, numBlocks
            * ByteFile.RECORDS_PER_BLOCK);
        heap.sort();
        heap.close();

        assertTrue(byteFile.isSorted());
        dataFile.delete();
    }


    /**
     * Test writeRandomRecords()
     *
     * @throws IOException
     */
    public void testWriteRandomRecords() throws IOException {
        // set next random (short) integers
        TestableRandom.setNextInts(13, 42);
        byteFile.writeRandomRecords();

        // convert very first set bytes into record
        @SuppressWarnings("resource")
        RandomAccessFile data = new RandomAccessFile(dataFile, "rw");
        byte[] recordBytes = new byte[Record.SIZE_IN_BYTES];
        data.seek(0);
        data.read(recordBytes);
        Record record = new Record(recordBytes);
        data.close();

        assertEquals((short)13, record.getKey());
        assertEquals((short)42, record.getValue());
        dataFile.delete();
    }


    private static void copy(File source, File dest) throws IOException {
        Files.copy(source.toPath(), dest.toPath());
    }
}
