import java.io.File;
import java.io.RandomAccessFile;
import student.TestCase;
import student.TestableRandom;

/**
 * HeapSortTest
 *
 * @author dmjimeno0105
 */
public class HeapSortTest extends TestCase {

    /**
     * Tests main()
     *
     * @throws Exception
     */
    public void testMain() throws Exception {
        // set predictable random record bytes (first key, first value,
        // second key, second value...)
        TestableRandom.setNextInts(0, 42);

        // generate file to sort
        String fileName = "generatedBlock1.bin";
        int numBlocks = 1;
        ByteFile byteFile = new ByteFile(fileName, numBlocks);
        byteFile.writeRandomRecords();

        String[] args = { fileName, "1", "heapSortTestStats.txt" };
        HeapSort.main(args);

        // grab first record after sort has completed
        File file = new File(fileName);
        @SuppressWarnings("resource")
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        byte[] bytes = new byte[Record.SIZE_IN_BYTES];
        raf.seek(0);
        raf.read(bytes);
        Record record = new Record(bytes);
        raf.close();

        String output = systemOut().getHistory();
        assertTrue(output.contains(String.valueOf(record.getKey())));

        byteFile.delete();
    }
}
