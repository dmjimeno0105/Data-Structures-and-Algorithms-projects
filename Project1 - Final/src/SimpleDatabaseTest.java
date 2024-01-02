import java.util.LinkedList;

/**
 * SimpleDatabase test
 *
 * @author Dominic Jimeno (dmjimeno0105)
 *
 * @version 9/5/2022
 */
public class SimpleDatabaseTest extends student.TestCase {
    private SimpleDatabase database;
    private Rectangle rectangle;

    /**
     * Setup method for testing
     */
    public void setUp() {
        database = new SimpleDatabase();
        rectangle = new Rectangle("rectangle", 10, 10, 5, 5);
        database.insert(rectangle);
    }


    /**
     * Tests size()
     */
    public void testSize() {
        assertEquals(1, database.size());
    }


    /**
     * Tests insert()
     */
    public void testInsert() {
        Rectangle invalid = new Rectangle("invalid", 0, 0, 0, 0);
        database.insert(rectangle);
        database.insert(invalid);
        assertEquals(2, database.size());
    }


    /**
     * Tests remove(String name)
     */
    public void testRemoveName() {
        database.remove("rectangle");
        assertEquals(0, database.size());
        assertNull(database.remove(null));
    }


    /**
     * Tests remove(int x, int y, int width, int height)
     */
    public void testRemoveDimensions() {
        assertNull(database.remove(10, 10, 5, 6));
        database.remove(10, 10, 5, 5);
        assertEquals(0, database.size());
        assertNull(database.remove(10, 10, 5, 5));
    }


    /**
     * Tests regionSearch(int x, int y, int width, int height)
     */
    public void testRegionSearch() {
        LinkedList<Rectangle> list = new LinkedList<>();
        Rectangle rectangle2 = new Rectangle("rectangle2", 45, 0, 10, 10);
        Rectangle rectangle3 = new Rectangle("rectangle3", 400, 0, 100, 310);
        database.insert(rectangle2);
        database.insert(rectangle3);

        list.add(rectangle);
        list.add(rectangle2);
        list.add(rectangle3);

        assertEquals(3, database.size());

        assertEquals(list, database.regionSearch(-900, 5, 5000, 20));
    }


    /**
     * Tests search(String name)
     */
    @SuppressWarnings("unlikely-arg-type")
    public void testSearch() {
        LinkedList<Rectangle> list = new LinkedList<>();
        list.add(rectangle);
        assertEquals(list, database.search("rectangle"));
        list.remove("rectangle");
        assertEquals(list, database.search("rectangle"));
    }


    /**
     * Tests dump()
     */
    public void testDump() {
        database.remove(rectangle.name());
        Rectangle rectangle1 = new Rectangle("rectangle1", 10, 10, 5, 5);
        Rectangle rectangle2 = new Rectangle("rectangle2", 15, 10, 5, 5);
        Rectangle rectangle3 = new Rectangle("rectangle3", 14, 10, 5, 5);
        Rectangle rectangle4 = new Rectangle("rectangle4", 11, 11, 1, 1);

        SkipNode<String, Rectangle> head = new SkipNode<>(null, null, 1);
        SkipNode<String, Rectangle> node1 = new SkipNode<>("rectangle1",
            rectangle1, 0);
        SkipNode<String, Rectangle> node2 = new SkipNode<>("rectangle2",
            rectangle2, 0);
        SkipNode<String, Rectangle> node3 = new SkipNode<>("rectangle3",
            rectangle3, 0);
        SkipNode<String, Rectangle> node4 = new SkipNode<>("rectangle4",
            rectangle4, 0);

        // return a LinkedList of size one containing the head node
        LinkedList<SkipNode<String, Rectangle>> allNodes = new LinkedList<>();
        allNodes.add(head);
        assertEquals(allNodes, database.dump());

        // first insert nodes
        // head->node1->node2->node3->node4->null
        database.insert(rectangle1);
        database.insert(rectangle2);
        database.insert(rectangle3);
        database.insert(rectangle4);

        // return all nodes
        allNodes.add(node1);
        allNodes.add(node2);
        allNodes.add(node3);
        allNodes.add(node4);
        assertEquals(allNodes, database.dump());
    }
}
