import student.TestCase;

/**
 * Tests Hashtable.
 *
 * @author Dominic Jimeno (dmjimeno0105)
 * @version 1.0
 */
public class HashTableTest extends TestCase {
    private MemoryPool pool;
    private Hashtable table;

    /**
     * Sets up.
     */
    public void setUp() {
        pool = new MemoryPool(20);
        table = new Hashtable(10, pool);
    }


    /**
     * Tests capacity().
     */
    public void testCapacity() {
        assertEquals(10, table.capacity());
    }


    /**
     * Covers halfFull() mutations.
     */
    public void testHalfFull() {
        // Checkout assert size and capacity statements in:
        // testPut() and testRemove()
    }


    /**
     * Tests get(String s).
     */
    public void testGet() {
        Handle handle;
        // test get from empty and get string that does not exist
        assertNull(table.get("a"));
        // test get on somewhat filled and get string that does exist
        handle = pool.add("a");
        table.put("a", handle);
        assertNotNull(table.get("a"));
        // test get on half full
        handle = pool.add("b");
        table.put("b", handle);
        handle = pool.add("c");
        table.put("c", handle);
        handle = pool.add("d");
        table.put("d", handle);
        handle = pool.add("e");
        table.put("e", handle);
        assertNotNull(table.get("b"));
        // test get that goes through entire probe sequence but does not find
        assertNull(table.get("f"));
        assertNull(table.get("g"));
        assertNull(table.get("h"));
        assertNull(table.get("i"));
        assertNull(table.get("j"));
        assertNull(table.get("k"));
        assertNull(table.get("l"));
        assertNull(table.get("m"));
        assertNull(table.get("n"));
        assertNull(table.get("o"));
        assertNull(table.get("p"));
        assertNull(table.get("q"));
        assertNull(table.get("r"));
        assertNull(table.get("s"));
        assertNull(table.get("t"));
        assertNull(table.get("u"));
        assertNull(table.get("v"));
        assertNull(table.get("w"));
        assertNull(table.get("x"));
        assertNull(table.get("y"));
        assertNull(table.get("z"));
        // test find probing through at least 1 tombstone
        handle = table.remove("a");
        pool.remove(handle);
        handle = table.remove("b");
        pool.remove(handle);
        handle = table.remove("c");
        pool.remove(handle);
        handle = table.remove("d");
        pool.remove(handle);
        handle = table.remove("e");
        pool.remove(handle);
        assertNull(table.get("a"));
    }


    /**
     * Tests isEmpty().
     */
    public void testIsEmpty() {
        // test starts empty
        assertTrue(table.isEmpty());
        // test after put, its not empty
        Handle handle = pool.add("a");
        table.put("a", handle);
        assertEquals(1, table.size());
        assertFalse(table.isEmpty());
        // test after removing recent put, its empty again
        handle = table.remove("a");
        pool.remove(handle);
        assertTrue(table.isEmpty());
    }


    /**
     * Tests put(String s, Handle h).
     */
    public void testPut() {
        // assert boolean for every put statement
        Handle handle;
        // test put into empty
        Handle handleA = pool.add("a");
        table.put("a", handleA);
        assertEquals(1, table.size());
        assertEquals(10, table.capacity());
        // test put into somewhat filled
        // test put into tombstone
        // test put into half full
        handle = pool.add("b");
        table.put("b", handle);
        assertEquals(2, table.size());
        assertEquals(10, table.capacity());
        handle = pool.add("c");
        table.put("c", handle);
        assertEquals(3, table.size());
        assertEquals(10, table.capacity());
        handle = pool.add("d");
        table.put("d", handle);
        assertEquals(4, table.size());
        assertEquals(10, table.capacity());
        handle = pool.add("e");
        table.put("e", handle);
        assertEquals(5, table.size());
        assertEquals(10, table.capacity());
        handle = pool.add("f");
        table.put("f", handle);
        assertEquals(6, table.size());
        assertEquals(20, table.capacity());
    }


    /**
     * Covers increaseCapacity() mutations.
     */
    public void testIncreaseCapacity() {
        // add
        Handle handle;
        handle = pool.add("a");
        table.put("a", handle);
        handle = pool.add("c");
        table.put("c", handle);
        handle = pool.add("e");
        table.put("e", handle);
        handle = pool.add("g");
        table.put("g", handle);
        handle = pool.add("i");
        table.put("i", handle);
        // remove to create tombstones
        handle = table.remove("a");
        pool.remove(handle);
        handle = table.remove("c");
        pool.remove(handle);
        handle = table.remove("e");
        pool.remove(handle);
        handle = table.remove("g");
        pool.remove(handle);
        handle = table.remove("i");
        pool.remove(handle);
        // add in between tombstones in order to call increaseCapacity()
        handle = pool.add("b");
        table.put("b", handle);
        handle = pool.add("d");
        table.put("d", handle);
        handle = pool.add("f");
        table.put("f", handle);
        handle = pool.add("h");
        table.put("h", handle);
        handle = pool.add("j");
        table.put("j", handle);
        // covers scenario where tombstone is passed
        // to get to next handle to transfer; note it actually didn't
        handle = pool.add("a");
        table.put("a", handle);
    }


    /**
     * Tests remove(String s).
     */
    public void testRemove() {
        Handle handle;
        // test remove on empty
        // test remove handle that does not exist
        assertNull(table.remove("a")); // since null, unable to remove from pool
        // test remove on somewhat filled
        // test remove handle that does exist
        handle = pool.add("a");
        table.put("a", handle);
        assertEquals(1, table.size());
        handle = table.remove("a");
        assertNotNull(handle);
        assertEquals(0, table.size());
        pool.remove(handle);
        // test remove on half full
        handle = pool.add("a");
        table.put("a", handle);
        handle = pool.add("b");
        table.put("b", handle);
        handle = pool.add("c");
        table.put("c", handle);
        handle = pool.add("d");
        table.put("d", handle);
        handle = pool.add("e");
        table.put("e", handle);
        assertEquals(5, table.size());
        handle = table.remove("a");
        assertNotNull(handle);
        assertEquals(4, table.size());
        pool.remove(handle);
        handle = pool.add("a");
        table.put("a", handle);
        assertNull(table.remove("f"));
        assertNull(table.remove("g"));
        assertNull(table.remove("h"));
        assertNull(table.remove("i"));
        assertNull(table.remove("j"));
        assertNull(table.remove("k"));
        assertNull(table.remove("l"));
        assertNull(table.remove("m"));
        assertNull(table.remove("n"));
        assertNull(table.remove("o"));
        assertNull(table.remove("p"));
        assertNull(table.remove("q"));
        assertNull(table.remove("r"));
        assertNull(table.remove("s"));
        assertNull(table.remove("t"));
        assertNull(table.remove("u"));
        assertNull(table.remove("v"));
        assertNull(table.remove("w"));
        assertNull(table.remove("x"));
        assertNull(table.remove("y"));
        assertNull(table.remove("z"));
        assertEquals(5, table.size());
    }


    /**
     * Tests size().
     */
    public void testSize() {
        // test size starts at zero
        assertEquals(0, table.size());
        // test size increases when put is called
        Handle handle = pool.add("a");
        table.put("a", handle);
        assertEquals(1, table.size());
        // test size decreases when remove is called
        handle = table.remove("a");
        pool.remove(handle);
        assertEquals(0, table.size());
    }


    /**
     * Tests toString(String tableCategory).
     */
    public void testToString() {
        // assert correct string representation when hashtable empty
        assertEquals("total artists: 0", table.toString("artists"));
        // assert correct string representation when hashtable not empty
        Handle handle = pool.add("Kid Cudi");
        table.put("Kid Cudi", handle);
        assertEquals("|Kid Cudi| 6\ntotal artists: 1", table.toString(
            "artists"));
    }
}
