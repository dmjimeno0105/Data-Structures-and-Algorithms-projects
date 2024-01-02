import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import student.TestCase;

/**
 * Tests ExternalHeapSort
 *
 * @author dmjimeno0105
 */
public class ExternalHeapSortTest extends TestCase {
    private ExternalHeapSort heap;
    private String fileName;
    private File file;
    private int maxBuffers;
    private int numRecords;

    public void setUp() throws IOException {
        // leave original .bin file sample untouched
        String originalFileName = "sampleBlock1.bin";
        File originalFile = new File(originalFileName);
        // make copy of original .bin file sample
        fileName = originalFileName.replace(".bin", "Copy.bin");
        file = new File(fileName);
        copy(originalFile, file);

        maxBuffers = 1;
        numRecords = ByteFile.RECORDS_PER_BLOCK;
        heap = new ExternalHeapSort(file, maxBuffers, numRecords);
    }


    /**
     * Tests sort()
     *
     * @throws IOException
     */
    public void testSort() throws IOException {
        heap.sort();
        ByteFile heapFile = new ByteFile(fileName, 1);
        assertTrue(heapFile.isSorted());
        heap.close();
        file.delete();
    }


    private static void copy(File source, File dest) throws IOException {
        Files.copy(source.toPath(), dest.toPath());
    }
}
