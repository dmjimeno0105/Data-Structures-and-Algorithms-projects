import student.TestCase;

/**
 * Tests LinkedList.
 *
 * @author Dominic Jimeno (dmjimeno0105)
 * @version 1.0
 */
public class LinkedListTest extends TestCase {
    private LinkedList<String> list;

    /**
     * Sets up.
     */
    public void setUp() {
        list = new LinkedList<>();
    }


    /**
     * Tests add(E element).
     */
    public void testAddElement() {
        list.add("hello world");
        assertEquals("hello world", list.get(0));

        list.add("hello universe");
        assertEquals("hello universe", list.get(1));
    }


    /**
     * Tests add(int index, E element).
     */
    public void testAddElementToIndex() {
        list.add(0, "hello world");
        assertEquals("hello world", list.get(0));

        list.add(0, "hello galaxy");
        assertEquals("hello galaxy", list.get(0));

        list.add(1, "hello universe");
        assertEquals("hello universe", list.get(1));
    }


    /**
     * Tests add(int index, E element) when trying to add null.
     */
    public void testAddIllegalArgumentException() {
        Exception exception = null;
        try {
            list.add(0, null);
        }
        catch (Exception e) {
            exception = e;
        }
        assertTrue(exception instanceof IllegalArgumentException);
    }


    /**
     * Tests add().
     */
    public void testAddIndexOutOfBoundsException() {
        list.add("hello world");
        Exception exception = null;
        try {
            list.add(-1, "hello galaxy");
        }
        catch (Exception e) {
            exception = e;
        }
        assertTrue(exception instanceof IndexOutOfBoundsException);

        exception = null;
        try {
            list.add(2, "hello universe");
        }
        catch (Exception e) {
            exception = e;
        }
        assertTrue(exception instanceof IndexOutOfBoundsException);
    }


    /**
     * Tests clear().
     */
    public void testClear() {
        list.add("hello world");
        list.add("hello galaxy");
        list.add("hello universe");
        list.clear();

        assertTrue(list.isEmpty());
    }


    /**
     * Tests contains(Object o).
     */
    public void testContains() {
        assertFalse(list.contains("hello world"));

        list.add("hello world");
        assertTrue(list.contains("hello world"));
    }


    /**
     * Tests get(int index).
     */
    public void testGet() {
        list.add("hello world");
        list.add("hello galaxy");
        list.add("hello universe");

        assertEquals("hello world", list.get(0));
        assertEquals("hello galaxy", list.get(1));
        assertEquals("hello universe", list.get(2));
    }


    /**
     * Tests get(int index) when the index is less than zero or greater than or
     * equal to size.
     */
    public void testGetIndexOutOfBoundsException() {
        list.add("hello world");
        Exception exception = null;
        try {
            list.get(-1);
        }
        catch (Exception e) {
            exception = e;
        }
        assertTrue(exception instanceof IndexOutOfBoundsException);

        exception = null;
        try {
            list.get(1);
        }
        catch (IndexOutOfBoundsException e) {
            exception = e;
        }
        assertTrue(exception instanceof IndexOutOfBoundsException);
    }


    /**
     * Tests isEmpty().
     */
    public void testIsEmpty() {
        assertTrue(list.isEmpty());

        list.add("hello world");
        assertFalse(list.isEmpty());
    }


    /**
     * Tests lastIndexOf(Object o).
     */
    public void testLastIndexOf() {
        list.add("hello world");
        list.add("hello world");
        list.add("hello world");

        assertEquals(2, list.lastIndexOf("hello world"));
        assertEquals(-1, list.lastIndexOf("hello galaxy"));
    }


    /**
     * Tests listIterator(int index).
     */
    public void testListIterator() {
        // checkout LIteratorTest for inner class method testing
    }


    /**
     * Tests listIterator(int index) when the index is less than zero or greater
     * than or equal to size.
     */
    public void testListIteratorIndexOutOfBoundsException() {
        list.add("hello world");
        Exception exception = null;
        try {
            list.listIterator(-1);
        }
        catch (Exception e) {
            exception = e;
        }
        assertTrue(exception instanceof IndexOutOfBoundsException);

        exception = null;
        try {
            list.listIterator(1);
        }
        catch (Exception e) {
            exception = e;
        }
        assertTrue(exception instanceof IndexOutOfBoundsException);
    }


    /**
     * Tests remove(int index).
     */
    public void testRemoveElementAtIndex() {
        list.add("hello world");
        list.remove(0);

        assertFalse(list.contains("hello world"));
    }


    /**
     * Tests remove(Object o).
     */
    public void testRemoveObject() {
        list.add("hello world");
        list.add("hello galaxy");
        list.add("hello world");

        assertTrue(list.remove("hello world"));
        assertEquals(2, list.size());

        assertEquals("hello galaxy", list.get(0));

        assertFalse(list.remove("hello universe"));
        assertEquals(2, list.size());
    }


    /**
     * Tests size().
     */
    public void testSize() {
        assertEquals(0, list.size());

        list.add("hello world");
        assertEquals(1, list.size());

        list.remove(0);
        assertEquals(0, list.size());
    }


    /**
     * Tests toArray().
     */
    public void testToArray() {
        assertTrue(list.toArray().length == 0);

        list.add("hello world");
        list.add("hello galaxy");
        list.add("hello universe");
        String[] expected = { "hello world", "hello galaxy", "hello universe" };
        for (int i = 0; i < list.size(); i++) {
            assertEquals(expected[i], list.get(i));
        }
    }


    /**
     * Tests toString().
     */
    public void testToString() {
        assertEquals("", list.toString());

        list.add("hello world");
        list.add("hello galaxy");
        list.add("hello universe");
        StringBuilder builder = new StringBuilder();
        builder.append("hello world");
        builder.append(" -> ");
        builder.append("hello galaxy");
        builder.append(" -> ");
        builder.append("hello universe");

        assertEquals(builder.toString(), list.toString());
    }
}
