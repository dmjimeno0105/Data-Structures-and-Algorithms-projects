import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;

/*
 * On my honor:
 *
 * - I have not used source code obtained from another student,
 * or any other unauthorized source, either modified or
 * unmodified.
 *
 * - All source code and documentation used in my program is
 * either my original work, or was derived by me from the
 * source code published in the textbook for this course.
 *
 * - I have not discussed coding details about this project with
 * anyone other than the instructor, ACM/UPE tutors, programming
 * partner (if allowed in this class), or the TAs assigned to
 * this course. I understand that I may discuss the concepts
 * of this program with other students, and that another student
 * may help me debug my program so long as neither of us writes
 * anything during the discussion or modifies any computer file
 * during the discussion. I have violated neither the spirit nor
 * letter of this restriction.
 */

/**
 * External HeapsSort starter kit.
 *
 * @author dmjimeno0105
 */
public class HeapSort {

    /**
     * This is the entry point of the application
     *
     * @param args
     *            Command line arguments
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        // arguments
        String dataFileName = args[0]; // file to be sorted
        int numBuffers = Integer.parseInt(args[1]); // max buffers
        String statFileName = args[2]; // file to store runtime statistics

        // find num of blocks
        String stringNumBlocks;
        int numBlocks;
        if (!dataFileName.contains("Copy") && !dataFileName.contains(
            "generated")) {
            stringNumBlocks = dataFileName.replace("sampleBlock", "");
            stringNumBlocks = stringNumBlocks.replace(".bin", "");
            numBlocks = Integer.parseInt(stringNumBlocks);
        }
        else if (dataFileName.contains("Copy")) {
            stringNumBlocks = dataFileName.replace("sampleBlock", "");
            stringNumBlocks = stringNumBlocks.replace("Copy.bin", "");
            numBlocks = Integer.parseInt(stringNumBlocks);
        }
        else /*(dataFileName.contains("generated"))*/ {
            stringNumBlocks = dataFileName.replace("generatedBlock", "");
            stringNumBlocks = stringNumBlocks.replace(".bin", "");
            numBlocks = Integer.parseInt(stringNumBlocks);
        }

        // make a copy of the supplied sampleBlock##.bin file
        File originalFile = new File(dataFileName);
        String fileName = dataFileName.replace(".bin", "Copy.bin");
        File file = new File(fileName);
        copy(originalFile, file);

        // instantiates MaxHeap
        ExternalHeapSort heap = new ExternalHeapSort(file, numBuffers, numBlocks
            * ByteFile.RECORDS_PER_BLOCK);
        long totalSortTime = System.currentTimeMillis();
        heap.sort();
        totalSortTime = System.currentTimeMillis() - totalSortTime;
        Statistics.executionTime = totalSortTime;
        Statistics.analyze(statFileName, dataFileName);
        heap.close();

        // Print the first record from each block, in order, from the sorted
        // data file. The records are to be printed 8 records to a line (showing
        // both the key value and the data value for each record) separated by a
        // whitespace.
        @SuppressWarnings("resource")
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        byte[] recordBytes = new byte[Record.SIZE_IN_BYTES];
        for (int i = 1; i <= numBlocks; i++) {
            raf.seek(i * ByteFile.BYTES_PER_BLOCK);
            raf.read(recordBytes);
            Record record = new Record(recordBytes);
            System.out.printf("%5d %5d\t", record.getKey(), record.getValue());
            if (i % 8 == 0) {
                System.out.println();
            }
        }
        raf.close();
        file.delete();
    }


    private static void copy(File source, File dest) throws IOException {
        Files.copy(source.toPath(), dest.toPath());
    }
}
