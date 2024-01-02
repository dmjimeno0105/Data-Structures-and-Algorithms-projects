/**
 * Uses a memory pool to store string records and a hashtable to store the
 * string records' handles (index locations within the pool)
 *
 * Note: handle objects are used instead of integers to check for empty spots in
 * the hashtable (integers would store "empty" slots as zeros but 0 could be a
 * valid slot in the table. handle arrays label empty slots as null)
 *
 * @author Dominic Jimeno (dmjimeno0105)
 * @version 1.0
 */
public class MemoryManager {
    private Hashtable artists;
    private MemoryPool pool;
    private Hashtable songs;

    /**
     * Create a new MemoryManager object.
     *
     * @param initialHashSize
     *            is an integer that specifies the initial size of the hash
     *            table (in terms of slots).
     * @param initialBlockSize
     *            is an integer that is the initial size of the memory pool (in
     *            bytes).
     */
    public MemoryManager(int initialHashSize, int initialBlockSize) {
        pool = new MemoryPool(initialBlockSize);
        artists = new Hashtable(initialHashSize, pool);
        songs = new Hashtable(initialHashSize, pool);
    }


    /**
     * Returns a string representation of the artists contained in the database.
     *
     * @return a string representation of the artists contained in the database
     */
    public String artistsToString() {
        return artists.toString("artists");
    }


    /**
     * Returns a string representation of the current freeblock list in the
     * memory pool.
     *
     * @return a string representation of the current freeblock list in the
     *         memory pool
     */
    public String blocksToString() {
        return pool.toString();
    }


    /**
     * Inserts an artist and song into the memory pool and there associated
     * handles into the there respective hashtables.
     *
     * @param artistName
     *            the artist string being stored into the pool
     * @param songName
     *            the song string being stored into the pool
     * @return an output message indicating whether the command was successful
     */
    public String insert(String artistName, String songName) {
        StringBuilder builder = new StringBuilder();

        builder.append(insertArtist(artistName) + "\n");
        builder.append(insertSong(songName));

        return builder.toString();
    }


    /**
     * Inserts an artist into the memory pool and its associated handle into the
     * artist hashtable.
     *
     * @param artistName
     *            the record string being stored into the pool
     * @return an output message indicating whether the command was successful
     */
    public String insertArtist(String artistName) {
        StringBuilder builder = new StringBuilder();
        int previousPoolCapacity = pool.capacity();
        int previousTableCapacity = artists.capacity();

        // if the handle is retrieved, artist already exists in the pool
        if (artists.get(artistName) != null) {
            builder.append("|" + artistName
                + "| duplicates a record already in the artist database");
        }
        // but if no handle was retrieved, artist does not yet exist in the pool
        else {
            Handle artistHandle = pool.add(artistName);
            artists.put(artistName, artistHandle);

            // check if either pool or hashtable capacity increased
            if (artists.capacity() > previousTableCapacity) {
                builder.append("Artist hash table size doubled\n");
            }
            // check for multiple pool expansions
            int growthAmount = pool.capacity() - previousPoolCapacity;
            int expansionCount = growthAmount / pool.initialCapacity();
            for (int i = 1; i <= expansionCount; i++) {
                builder.append("Memory pool expanded to be "
                    + (previousPoolCapacity + i * pool.initialCapacity())
                    + " bytes\n");
            }

            builder.append("|" + artistName
                + "| is added to the artist database");
        }

        return builder.toString();
    }


    /**
     * Inserts a song into the memory pool and its associated handle into the
     * song hashtable.
     *
     * @param songName
     *            the record string being stored into the pool
     * @return an output message indicating whether the command was successful
     */
    public String insertSong(String songName) {
        StringBuilder builder = new StringBuilder();
        int previousPoolCapacity = pool.capacity();
        int previousTableCapacity = songs.capacity();

        // if the handle is retrieved, song already exists in the pool
        if (songs.get(songName) != null) {
            builder.append("|" + songName
                + "| duplicates a record already in the song database");
        }
        // but if no handle was retrieved, song does not yet exist in the pool
        else {
            Handle songHandle = pool.add(songName);
            songs.put(songName, songHandle);

            // check if either pool or hashtable capacity increased
            if (songs.capacity() > previousTableCapacity) {
                builder.append("Song hash table size doubled\n");
            }
            // check for multiple pool expansions
            int growthAmount = pool.capacity() - previousPoolCapacity;
            int expansionCount = growthAmount / pool.initialCapacity();
            for (int i = 1; i <= expansionCount; i++) {
                builder.append("Memory pool expanded to be "
                    + (previousPoolCapacity + i * pool.initialCapacity())
                    + " bytes\n");
            }

            builder.append("|" + songName + "| is added to the song database");
        }

        return builder.toString();
    }


    /**
     * Removes an artist from the memory pool and its associated handle from the
     * artist hashtable.
     *
     * @param name
     *            the record string being removed from the pool
     * @return an output message indicating whether the command was successful
     */
    public String removeArtist(String name) {
        StringBuilder builder = new StringBuilder();

        Handle artistHandle = artists.remove(name);
        // if handle does not exist, then record does not exist
        // pool and tables remain synced always
        if (artistHandle == null) {
            builder.append("|" + name
                + "| does not exist in the artist database");
        }
        // but if handle does exist, then record does exist
        else {
            pool.remove(artistHandle);
            builder.append("|" + name
                + "| is removed from the artist database");
        }

        return builder.toString();
    }


    /**
     * Removes a song from the memory pool and its associated handle from the
     * song hashtable.
     *
     * @param name
     *            the record string being removed from the pool
     * @return an output message indicating whether the command was successful
     */
    public String removeSong(String name) {
        StringBuilder builder = new StringBuilder();

        Handle songHandle = songs.remove(name);
        // if handle does not exist, then record does not exist
        // pool and tables remain synced always
        if (songHandle == null) {
            builder.append("|" + name
                + "| does not exist in the song database");
        }
        // but if handle does exist, then record does exist
        else {
            pool.remove(songHandle);
            builder.append("|" + name + "| is removed from the song database");
        }

        return builder.toString();
    }


    /**
     * Returns a string representation of the songs contained in the database.
     *
     * @return a string representation of the songs contained in the database
     */
    public String songsToString() {
        return songs.toString("songs");
    }
}
