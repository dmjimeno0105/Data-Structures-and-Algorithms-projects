import student.TestCase;

/**
 * Tests MemoryPool.
 *
 * @author Dominic Jimeno (dmjimeno0105)
 * @version 1.0
 */
public class MemoryPoolTest extends TestCase {
    private MemoryPool pool;

    /**
     * Sets up.
     */
    public void setUp() {
        pool = new MemoryPool(20);
    }


    /**
     * Test add() more.
     */
    public void testAddMore() {
        // adding something where capacity has to be increased more than once
        pool.add("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        assertEquals(20 + 2 * 20, pool.capacity());

        // adding to increase capacity when there are no freeblocks
        pool = new MemoryPool(18);
        pool.add("aa"); // pool size = 4
        Handle b = pool.add("b"); // "" size = 7
        pool.add("cc"); // "" size = 11
        Handle d = pool.add("d"); // "" size = 14
        pool.add("ee"); // "" size = 18
        assertEquals(18, pool.size());
        assertEquals(18, pool.capacity());

        assertEquals(0, pool.list().size());
        assertEquals("(18,0)", pool.toString());

        Handle ff = pool.add("f");
        assertEquals(18, ff.index());
        assertEquals(21, pool.size());
        assertEquals(36, pool.capacity());
        assertEquals("(21,15)", pool.toString());

        // adding to increase capacity when there are still freeblocks
        pool = new MemoryPool(18);
        pool.add("aa"); // pool size = 4
        b = pool.add("b"); // "" size = 7
        pool.add("cc"); // "" size = 11
        d = pool.add("d"); // "" size = 14
        pool.add("ee"); // "" size = 18
        assertEquals(18, pool.size());
        assertEquals(18, pool.capacity());

        pool.remove(b);
        pool.remove(d);
        assertEquals(12, pool.size());
        assertEquals("(4,3) -> (11,3)", pool.toString());

        ff = pool.add("ff");
        assertEquals(18, ff.index());
        assertEquals(16, pool.size());
        assertEquals(36, pool.capacity());
        assertEquals("(4,3) -> (11,3) -> (22,14)", pool.toString());
    }


    /**
     * Tests add(String record).
     */
    public void testAdd() {
        // for following tests, assert correct handle is returned
        // add into empty
        // add where best fit is first block of freeblock list
        Handle expected = new Handle(0);
        assertTrue(expected.equals(pool.add("a")));
        // add into non empty
        // add to fill a block
        expected = new Handle(3);
        assertTrue(expected.equals(pool.add("bbbbbbbbbbbbbbb")));
        assertEquals(20, pool.size());
        assertEquals("(20,0)", pool.toString());
        // add into full
        expected = new Handle(20);
        assertTrue(expected.equals(pool.add("c")));
        assertEquals(23, pool.size());
        assertEquals("(23,17)", pool.toString());

        // setup new pool
        pool = new MemoryPool(16);
        Handle aa = new Handle(0);
        assertTrue(aa.equals(pool.add("aa")));
        pool.add("b");
        Handle c = new Handle(7);
        assertTrue(c.equals(pool.add("c")));
        pool.add("d");
        pool.remove(aa);
        pool.remove(c);

        // add where best fit is in middle of freeblock list
        expected = new Handle(7);
        assertTrue(expected.equals(pool.add("c")));
        // add where best fit is last block of freeblock list
        expected = new Handle(13);
        assertTrue(expected.equals(pool.add("e")));

        // setup new pool
        pool = new MemoryPool(5);

        // try adding an empty string
        pool.add("");
        assertEquals(2, pool.size());
    }


    /**
     * Tests capacity().
     */
    public void testCapacity() {
        // make the pool a little smaller
        pool = new MemoryPool(9);

        // assert capacity updates on increaseCapacity
        assertEquals(9, pool.capacity()); // initial capacity
        pool.add("a");
        assertEquals(3, pool.size());
        assertEquals(9, pool.capacity());
        pool.add("");
        assertEquals(5, pool.size());
        assertEquals(9, pool.capacity());
        pool.add("bb");
        assertEquals(9, pool.size());
        assertEquals(9, pool.capacity());
        // test insert on full
        pool.add("c");
        assertEquals(12, pool.size());
        assertEquals(18, pool.capacity());

        // assert capacity increases by adding initial capacity to capacity
        pool.add("ddddd");
        assertEquals(19, pool.size());
        assertEquals(27, pool.capacity());
    }


    /**
     * Tests get(Handle handle).
     */
    public void testGet() {
        // assert correct string is returned
        // NOTE do not test get on empty pool
        // setup new pool
        pool = new MemoryPool(15);
        Handle a = new Handle(0);
        assertTrue(a.equals(pool.add("a")));
        Handle b = new Handle(3);
        assertTrue(b.equals(pool.add("b")));
        Handle c = new Handle(6);
        assertTrue(c.equals(pool.add("c")));
        Handle d = new Handle(9);
        assertTrue(d.equals(pool.add("d")));
        Handle e = new Handle(12);
        assertTrue(e.equals(pool.add("e")));

        // test get on full
        // test get on left
        assertEquals("a", pool.get(a));
        // test get on full
        // test get on middle
        assertEquals("c", pool.get(c));
        // test get on full
        // test get on right
        assertEquals("e", pool.get(e));

        // update pool
        pool.remove(a);
        pool.remove(e);

        // test get on not full
        // test get on left
        assertEquals("b", pool.get(b));
        // test get on not full
        // test get on middle
        assertEquals("c", pool.get(c));
        // test get on not full
        // test get on right
        assertEquals("d", pool.get(d));
    }


    /**
     * Covers mutations in increaseCapacity().
     */
    public void testIncreaseCapacity() {
        // setup new pool
        // tests when left block abuts and merges
        pool = new MemoryPool(6);
        pool.add("a");
        assertEquals(3, pool.size());
        assertEquals(6, pool.capacity());
        Handle bb = new Handle(3);
        pool.add("bb");
        assertEquals(7, pool.size());
        assertEquals(12, pool.capacity());
        assertEquals(1, pool.list().size());
        pool.remove(bb);
        assertEquals(3, pool.size());
        assertEquals(12, pool.capacity());
        assertEquals(1, pool.list().size()); // should merge free space

        // reset pool
        // test when left block does not abut so does not merge
        pool = new MemoryPool(6);
        Handle a = new Handle(0);
        pool.add("a");
        assertEquals(3, pool.size());
        assertEquals(6, pool.capacity());
        // Handle b = new Handle(3);
        pool.add("b");
        assertEquals(6, pool.size());
        assertEquals(6, pool.capacity());

        assertEquals("(" + pool.capacity() + ",0)", pool.toString());

        pool.remove(a);
        assertEquals(3, pool.size());
        assertEquals(6, pool.capacity());
        assertEquals(1, pool.list().size());
        pool.add("cc");
        assertEquals(7, pool.size());
        assertEquals(12, pool.capacity());
        assertEquals(2, pool.list().size());

        // uses list to confirm no blocks were merged
    }


    /**
     * Tests isEmpty().
     */
    public void testIsEmpty() {
        // test isEmpty on empty
        assertTrue(pool.isEmpty());
        // test isEmpty on non empty
        pool.add("a");
        assertFalse(pool.isEmpty());
    }


    /**
     * Tests remove(Handle handle).
     */
    public void testRemove() {
        // assert size is correct after every remove
        // NOTE do not test remove on empty pool
        // setup new pool
        pool = new MemoryPool(15);
        Handle a = new Handle(0);
        assertTrue(a.equals(pool.add("a")));
        Handle b = new Handle(3);
        assertTrue(b.equals(pool.add("b")));
        Handle c = new Handle(6);
        assertTrue(c.equals(pool.add("c")));
        Handle d = new Handle(9);
        assertTrue(d.equals(pool.add("d")));
        Handle e = new Handle(12);
        assertTrue(e.equals(pool.add("e")));

        // test remove beginning
        // test remove on full
        // test add freeblock in front
        pool.remove(a);
        assertEquals(12, pool.size());
        // test remove end
        // test remove on not full
        // test add freeblock at end
        pool.remove(e);
        assertEquals(9, pool.size());
        // test remove in middle
        // test add freeblock in middle
        // test no merge
        pool.remove(c);
        assertEquals(6, pool.size());

        // setup new pool
        pool = new MemoryPool(9);
        a = new Handle(0);
        assertTrue(a.equals(pool.add("a")));
        b = new Handle(3);
        assertTrue(b.equals(pool.add("b")));
        c = new Handle(6);
        assertTrue(c.equals(pool.add("c")));

        // test merge block to the left
        pool.remove(a);
        assertEquals(6, pool.size());
        pool.remove(b);
        assertEquals(3, pool.size());
        // reset
        pool.add("a");
        pool.add("b");
        // test merge block to the right
        pool.remove(b);
        assertEquals(6, pool.size());
        pool.remove(a);
        assertEquals(3, pool.size());
        // reset
        pool.add("a");
        pool.add("b");
        // test merge blocks to the left and right
        pool.remove(a);
        assertEquals(6, pool.size());
        pool.remove(c);
        assertEquals(3, pool.size());
        pool.remove(b);
        assertEquals(0, pool.size());
    }


    /**
     * Tests size().
     */
    public void testSize() {
        assertEquals(0, pool.size());

        // assert size updates on add
        Handle a = new Handle(0);
        pool.add("a");
        assertEquals(3, pool.size());
        Handle b = new Handle(3);
        pool.add("b");
        assertEquals(6, pool.size());
        // assert size updates on remove
        pool.remove(a);
        assertEquals(3, pool.size());
        pool.remove(b);
        assertEquals(0, pool.size());
    }


    /**
     * Tests toString().
     */
    public void testToString() {
        // assert a correct free block list string representation is returned
        // when pool is empty
        assertEquals("(0,20)", pool.toString());

        // setup new pool
        pool = new MemoryPool(9);
        Handle a = new Handle(0);
        assertTrue(a.equals(pool.add("a")));
        Handle b = new Handle(3);
        assertTrue(b.equals(pool.add("b")));
        Handle c = new Handle(6);
        assertTrue(c.equals(pool.add("c")));

        // assert a correct free block list string representation is returned
        // when pool is full
        assertEquals("(" + pool.capacity() + ",0)", pool.toString());

        // update pool
        pool.remove(a);
        pool.remove(c);
        // assert a correct free block list string representation is returned
        // when pool is somewhat filled
        assertEquals("(0,3) -> (6,3)", pool.toString());
    }
}
