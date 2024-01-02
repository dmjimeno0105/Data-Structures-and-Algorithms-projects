import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Statistics
 *
 * @author dmjimeno0105
 */
public class Statistics {
    /**
     * cache hits
     */
    public static int cacheHits;
    /**
     * cache misses
     */
    public static int cacheMisses;
    /**
     * disk reads
     */
    public static int diskReads;
    /**
     * disk writes
     */
    public static int diskWrites;
    /**
     * total execution time
     */
    public static long executionTime;

    /**
     * Constructor
     */
    public Statistics() {
        cacheHits = 0;
        cacheMisses = 0;
        diskReads = 0;
        diskWrites = 0;
        executionTime = 0;
    }


    /**
     * Writes statistics to statisticsFileName
     *
     * @param statisticsFileName
     *            name of the statistics file
     * @param dataFileName
     *            name of the data file
     * @throws IOException
     */
    @SuppressWarnings("resource")
    public static void analyze(String statisticsFileName, String dataFileName)
        throws IOException {
        File analysis = new File(statisticsFileName);
        analysis.createNewFile();
        FileWriter fileWriter = new FileWriter(analysis.getAbsolutePath(),
            true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        bufferedWriter.write("------  STATS ------\n");
        bufferedWriter.write("File name: " + dataFileName + "\n");
        bufferedWriter.write("Cache Hits: " + cacheHits + "\n");
        bufferedWriter.write("Cache Misses: " + cacheMisses + "\n");
        bufferedWriter.write("Disk Reads: " + diskReads + "\n");
        bufferedWriter.write("Disk Writes: " + diskWrites + "\n");
        bufferedWriter.write("Time to sort: " + executionTime + "\n");
        bufferedWriter.close();
    }
}
