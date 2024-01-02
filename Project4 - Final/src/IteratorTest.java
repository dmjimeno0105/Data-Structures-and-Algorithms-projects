import student.TestCase;

/**
 * Tests LinkedList.Iterator.
 *
 * @author Dominic Jimeno (dmjimeno0105)
 * @version 1.0
 */
public class IteratorTest extends TestCase {
    private LinkedList<String> empty;
    private LinkedList<String> list1;
    private LinkedList<String> list2;
    private ListIterator<String> emptyIterator;
    private ListIterator<String> iterator1;
    private ListIterator<String> iterator2;

    /**
     * Sets up.
     */
    public void setUp() {
        list1 = new LinkedList<>();
        list1.add("b");
        iterator1 = list1.listIterator(0);

        empty = new LinkedList<>();
        emptyIterator = empty.listIterator(0);

        list2 = new LinkedList<>();
        list2.add("b");
        iterator2 = list2.listIterator(0);
        iterator2.next();
    }


    /**
     * Tests add(E e).
     */
    public void testAdd() {
        // empty iterator
        assertFalse(emptyIterator.hasPrevious());
        assertFalse(emptyIterator.hasNext());

        // iterator1
        iterator1.add("a");
        assertEquals(2, list1.size());

        // iterator2
        iterator2.add("c");
        assertEquals(2, list2.size());
    }


    /**
     * Tests hasNext().
     */
    public void testHasNext() {
        // iterator1
        assertTrue(iterator1.hasNext());

        // iterator2
        assertFalse(iterator2.hasNext());
    }


    /**
     * Tests hasPrevious().
     */
    public void testHasPrevious() {
        // iterator1
        assertFalse(iterator1.hasPrevious());

        // iterator2
        assertTrue(iterator2.hasPrevious());
    }


    /**
     * Tests private int index to ensure it is being update correctly.
     */
    public void testIndex() {
        LinkedList<String> strings = new LinkedList<>();
        strings.add("a");
        strings.add("b");
        ListIterator<String> cursor = strings.listIterator(0);
        // validating index computation in next()
        cursor.next();
        cursor.next();
        assertEquals(1, cursor.previousIndex());

        LinkedList<String> strings1 = new LinkedList<>();
        strings1.add("a");
        ListIterator<String> cursor1 = strings1.listIterator(0);
        // validating index computation in add(E e)
        cursor1.next();
        cursor1.add("b");
        assertEquals(1, cursor1.previousIndex());

        LinkedList<String> strings2 = new LinkedList<>();
        strings2.add("a");
        strings2.add("b");
        ListIterator<String> cursor2 = strings2.listIterator(1);
        // validating index computation in previous();
        cursor2.previous();
        assertEquals(0, cursor2.nextIndex());

        LinkedList<String> strings3 = new LinkedList<>();
        strings3.add("a");
        ListIterator<String> cursor3 = strings3.listIterator(0);
        // validating index computation in remove();
        cursor3.next();
        cursor3.remove();
        assertEquals(0, cursor3.nextIndex());
    }


    /**
     * Tests next().
     */
    public void testNext() {
        // iterator1
        assertEquals("b", iterator1.next());

        // iterator2
        Exception exception = null;
        try {
            iterator2.next();
        }
        catch (Exception e) {
            exception = e;
        }
        assertTrue(exception instanceof NoSuchElementException);
    }


    /**
     * Tests nextIndex().
     */
    public void testNextIndex() {
        // iterator1
        assertEquals(0, iterator1.nextIndex());
        iterator1.add("a");
        assertEquals(1, iterator1.nextIndex());

        // iterator2
        assertEquals(list2.size(), iterator2.nextIndex());
    }


    /**
     * Tests previous().
     */
    public void testPrevious() {
        // iterator1
        Exception exception = null;
        try {
            iterator1.previous();
        }
        catch (Exception e) {
            exception = e;
        }
        assertTrue(exception instanceof NoSuchElementException);

        // iterator2
        assertEquals("b", iterator2.previous());
    }


    /**
     * Tests previousIndex().
     */
    public void testPreviousIndex() {
        // iterator1
        assertEquals(-1, iterator1.previousIndex());

        // iterator2
        assertEquals(0, iterator2.previousIndex());
    }


    /**
     * Tests remove().
     */
    public void testRemove() {
        // iterator1
        Exception exception = null;
        try {
            iterator1.remove();
        }
        catch (Exception e) {
            exception = e;
        }
        assertTrue(exception instanceof IllegalStateException);

        iterator1.next();
        iterator1.remove();
        assertEquals(0, list1.size());
        exception = null;
        try {
            iterator1.remove();
        }
        catch (Exception e) {
            exception = e;
        }
        assertTrue(exception instanceof NoSuchElementException);

        // iterator2
        iterator2 = list2.listIterator(0);
        exception = null;
        try {
            iterator2.remove();
        }
        catch (Exception e) {
            exception = e;
        }
        assertTrue(exception instanceof IllegalStateException);
        iterator2.next();

        iterator2.previous();
        iterator2.remove();
        assertEquals(0, list2.size());
        exception = null;
        try {
            iterator2.remove();
        }
        catch (Exception e) {
            exception = e;
        }
        assertTrue(exception instanceof NoSuchElementException);
    }


    /**
     * Tests set(E e).
     */
    public void testSet() {
        // iterator1
        Exception exception = null;
        try {
            iterator1.set("boat");
        }
        catch (Exception e) {
            exception = e;
        }
        assertTrue(exception instanceof IllegalStateException);

        iterator1.next();
        iterator1.set("boat");
        assertEquals("boat", list1.get(0));
        iterator1.remove();
        exception = null;
        try {
            iterator1.set("boat");
        }
        catch (Exception e) {
            exception = e;
        }
        assertTrue(exception instanceof NoSuchElementException);

        // iterator2
        iterator2 = list2.listIterator(0);
        exception = null;
        try {
            iterator2.set("boat");
        }
        catch (Exception e) {
            exception = e;
        }
        assertTrue(exception instanceof IllegalStateException);
        iterator2.next();

        iterator2.previous();
        iterator2.set("baboon");
        assertEquals("baboon", list2.get(0));
        iterator2.remove();
        exception = null;
        try {
            iterator2.set("baboon");
        }
        catch (Exception e) {
            exception = e;
        }
        assertTrue(exception instanceof NoSuchElementException);
    }
}
