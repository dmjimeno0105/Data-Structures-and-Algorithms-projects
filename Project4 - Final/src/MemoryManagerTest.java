import student.TestCase;

/**
 * Tests MemoryManager.
 *
 * @author Dominic Jimeno (dmjimeno0105)
 * @version 1.0
 */
public class MemoryManagerTest extends TestCase {
    private MemoryManager manager;

    /**
     * Sets up.
     */
    public void setUp() {
        manager = new MemoryManager(10, 32);
    }


    /**
     * Covers mutations for insertArtist() and insertSong().
     */
    public void testInsertMore() {
        manager = new MemoryManager(10, 5);
        // manager.insertArtist("hellohellohello");
        assertEquals("Memory pool expanded to be 10 bytes\n"
            + "Memory pool expanded to be 15 bytes\n"
            + "Memory pool expanded to be 20 bytes\n"
            + "|hellohellohello| is added to the artist database", manager
                .insertArtist("hellohellohello"));

        manager = new MemoryManager(10, 5);
        // manager.insertSong("hellohellohello");
        assertEquals("Memory pool expanded to be 10 bytes\n"
            + "Memory pool expanded to be 15 bytes\n"
            + "Memory pool expanded to be 20 bytes\n"
            + "|hellohellohello| is added to the song database", manager
                .insertSong("hellohellohello"));
    }


    /**
     * Tests insert(String artistName, String songName).
     */
    public void testInsert() {
        // Checkout testInsertArtist() and testInsertSong()
    }


    /**
     * Tests insertArtist(String artistName).
     */
    public void testInsertArtist() {
        // set up
        manager = new MemoryManager(2, 16);

        // test insert no duplicate
        assertEquals("|Kid Cudi| is added to the artist database", manager
            .insertArtist("Kid Cudi"));
        // test insert duplicate
        assertEquals(
            "|Kid Cudi| duplicates a record already in the artist database",
            manager.insertArtist("Kid Cudi"));
        // NOTE if both memory pool and hashtable sizes/capacities increase,
        // print hashtable increase notification first
        // test insert increase capacity of memory pool
        assertTrue(manager.insertArtist("Ozuna").contains(
            "Memory pool expanded to be 32 bytes"));
        // test insert increase capacity of hashtable
        assertTrue(manager.insertArtist("Alec Benjamin").contains(
            "Artist hash table size doubled"));
    }


    /**
     * Tests insertSong(String songName).
     */
    public void testInsertSong() {
        // set up
        manager = new MemoryManager(2, 27);

        // test insert no duplicate
        assertEquals("|Ignite The Love| is added to the song database", manager
            .insertSong("Ignite The Love"));
        // test insert duplicate
        assertEquals("|Ignite The Love| "
            + "duplicates a record already in the song database", manager
                .insertSong("Ignite The Love"));
        // NOTE if both memory pool and hashtable sizes/capacities increase,
        // print hashtable increase notification first
        // test insert increase capacity of memory pool
        assertTrue(manager.insertSong("Monotonia").contains(
            "Memory pool expanded to be 54 bytes"));
        // test insert increase capacity of hashtable
        assertTrue(manager.insertSong("The Way You Felt").contains(
            "Song hash table size doubled"));
    }


    /**
     * Tests removeArtist(String name).
     */
    public void testRemoveArtist() {
        // test remove does not exist
        assertEquals("|Bad Bunny| does not exist in the artist database",
            manager.removeArtist("Bad Bunny"));
        // test remove does exist
        manager.insertArtist("Good Bunny");
        assertEquals("|Good Bunny| is removed from the artist database", manager
            .removeArtist("Good Bunny"));
    }


    /**
     * Tests removeSong(String name).
     */
    public void testRemoveSong() {
        // test remove does not exist
        assertEquals("|Hall of Fame| does not exist in the song database",
            manager.removeSong("Hall of Fame"));
        // test remove does exist
        manager.insertSong("Hall of Fame");
        assertEquals("|Hall of Fame| is removed from the song database", manager
            .removeSong("Hall of Fame"));
    }
}
