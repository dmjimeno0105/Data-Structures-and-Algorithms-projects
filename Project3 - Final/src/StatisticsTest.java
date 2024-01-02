import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import student.TestCase;

/**
 * Test Statistics
 *
 * @author dmjimeno0105
 */
public class StatisticsTest extends TestCase {
    /**
     * Tests analyze()
     *
     * @throws IOException
     */
    public void testAnalyze() throws IOException {
        String statisticsFileName = "statisticsFile.txt";
        File statisticsFile = new File(statisticsFileName);
        String originalDataFileName = "sampleBlock1.bin";
        File originalDataFile = new File(originalDataFileName);
        int numBlocks = 1;
        String dataFileName = originalDataFileName.replace(".bin", "Copy.bin");
        File dataFile = new File(dataFileName);
        copy(originalDataFile, dataFile);

        // ====== run analyze before sorting ======
        Statistics.analyze(statisticsFileName, originalDataFileName);

        // convert statisticsFile into string to compare
        InputStream is = new FileInputStream(statisticsFileName);
        @SuppressWarnings("resource")
        BufferedReader buf = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = buf.readLine();
        while (line != null) {
            sb.append(line).append("\n");
            line = buf.readLine();
        }
        String statisticsFileContents = sb.toString();
        is.close();
        buf.close();
        statisticsFile.delete();

        // create reference string
        StringBuilder reference = new StringBuilder();
        reference.append("------  STATS ------\n");
        reference.append("File name: " + originalDataFileName + "\n");
        reference.append("Cache Hits: " + Statistics.cacheHits + "\n");
        reference.append("Cache Misses: " + Statistics.cacheMisses + "\n");
        reference.append("Disk Reads: " + Statistics.diskReads + "\n");
        reference.append("Disk Writes: " + Statistics.diskWrites + "\n");
        reference.append("Time to sort: " + Statistics.executionTime + "\n");

        // compare output of statistics file with reference
        assertTrue(statisticsFileContents.equals(reference.toString()));

        // ====== run analyze after sorting ======
        ExternalHeapSort heap = new ExternalHeapSort(dataFile, 1, numBlocks
            * ByteFile.RECORDS_PER_BLOCK);
        long totalSortTime = System.currentTimeMillis();
        heap.sort();
        totalSortTime = System.currentTimeMillis() - totalSortTime;
        Statistics.executionTime = totalSortTime;
        Statistics.analyze(statisticsFileName, originalDataFileName);
        heap.close();
        dataFile.delete();

        // convert statisticsFile into string to compare
        is = new FileInputStream(statisticsFileName);
        buf = new BufferedReader(new InputStreamReader(is));
        sb = new StringBuilder();
        line = buf.readLine();
        while (line != null) {
            sb.append(line).append("\n");
            line = buf.readLine();
        }
        statisticsFileContents = sb.toString();
        is.close();
        buf.close();
        statisticsFile.delete();

        // create reference string
        reference = new StringBuilder();
        reference.append("------  STATS ------\n");
        reference.append("File name: " + originalDataFileName + "\n");
        reference.append("Cache Hits: " + Statistics.cacheHits + "\n");
        reference.append("Cache Misses: " + Statistics.cacheMisses + "\n");
        reference.append("Disk Reads: " + Statistics.diskReads + "\n");
        reference.append("Disk Writes: " + Statistics.diskWrites + "\n");
        reference.append("Time to sort: " + Statistics.executionTime + "\n");

        // compare output of statistics file with reference
        assertTrue(statisticsFileContents.equals(reference.toString()));
    }


    private static void copy(File source, File dest) throws IOException {
        Files.copy(source.toPath(), dest.toPath());
    }
}
