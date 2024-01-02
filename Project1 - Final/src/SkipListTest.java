import java.util.LinkedList;

/**
 * SkipList test
 *
 * @author Dominic Jimeno (dmjimeno0105)
 *
 * @version 9/5/2022
 */
public class SkipListTest extends student.TestCase {
    private SkipList<String, Integer> integerList;
    private SkipList<String, Rectangle> rectangleList;

    /**
     * Setup method for testing
     */
    public void setUp() {
        // initially empty lists
        integerList = new SkipList<>();
        rectangleList = new SkipList<>();
    }


    /**
     * Tests size()
     */
    public void testSize() {
        // test empty skipList
        assertEquals(0, integerList.size());

        // test with one SkipNode
        SkipNode<String, Integer> node1 = new SkipNode<>("node1", Integer
            .valueOf(1), 0);
        integerList.insert(node1.key(), node1.value());
        assertEquals(1, integerList.size());

        // test with two SkipNodes (both of which have the same key)
        SkipNode<String, Integer> node2 = new SkipNode<>("node1", Integer
            .valueOf(2), 0);
        integerList.insert(node2.key(), node2.value());
        assertEquals(2, integerList.size());

        // test with three SkipNodes (two of which have the same value)
        SkipNode<String, Integer> node3 = new SkipNode<>("node3", Integer
            .valueOf(2), 0);
        integerList.insert(node3.key(), node3.value());
        assertEquals(3, integerList.size());

        // test after removing one SkipNode
        integerList.remove(node3.key());
        assertEquals(2, integerList.size());
    }


    /**
     * Tests insert(K key, V value)
     */
    public void testInsert() {
        SkipNode<String, Integer> head = new SkipNode<>(null, null, 1);
        SkipNode<String, Integer> node1 = new SkipNode<>("node1", Integer
            .valueOf(1), 0);
        SkipNode<String, Integer> node2 = new SkipNode<>("node2", Integer
            .valueOf(2), 0);
        SkipNode<String, Integer> node3 = node1;
        SkipNode<String, Integer> node4 = new SkipNode<>("node4", Integer
            .valueOf(4), 0);

        // check if nodes were added
        assertEquals(0, integerList.size());
        integerList.insert(node4.key(), node4.value());
        assertEquals(1, integerList.size());
        integerList.insert(node1.key(), node1.value());
        assertEquals(2, integerList.size());
        integerList.insert(node2.key(), node2.value());
        assertEquals(3, integerList.size());
        integerList.insert(node3.key(), node3.value());
        assertEquals(4, integerList.size());

        // check if nodes were added in the right order
        // head->node3->node1->node2->node4->null
        LinkedList<SkipNode<String, Integer>> list = new LinkedList<>();
        list.add(head);
        list.add(node3);
        list.add(node1);
        list.add(node2);
        list.add(node4);
        assertEquals(list, integerList.dump());
    }


    /**
     * Tests remove(K key)
     */
    public void testRemoveKey() {
        SkipNode<String, Integer> node1 = new SkipNode<>("node1", Integer
            .valueOf(1), 0);
        SkipNode<String, Integer> node2 = new SkipNode<>("node2", Integer
            .valueOf(2), 0);
        SkipNode<String, Integer> node3 = node1;
        SkipNode<String, Integer> node4 = new SkipNode<>("node4", Integer
            .valueOf(4), 0);

        // attempt to remove a node that's not in the list before inserting
        assertNull(integerList.remove("node5"));

        // insert nodes
        // head->node3->node1->node2->node4->null
        integerList.insert(node4.key(), node4.value());
        integerList.insert(node1.key(), node1.value());
        integerList.insert(node2.key(), node2.value());
        integerList.insert(node3.key(), node3.value());

        // attempt to remove a node that's not in the list
        assertNull(integerList.remove("node5"));

        // remove first node
        assertTrue(node1.value().equals(integerList.remove("node1")));

        // remove last node
        assertTrue(node4.value().equals(integerList.remove("node4")));

        // remove the rest
        assertTrue(node1.value().equals(integerList.remove("node1")));
        assertTrue(node2.value().equals(integerList.remove("node2")));
    }


    /**
     * Tests remove(int x, int y, int width, int height)
     */
    public void testRemoveDimensions() {
        Rectangle rectangle1 = new Rectangle("rectangle1", 0, 0, 70, 50);
        Rectangle rectangle2 = new Rectangle("rectangle2", 0, 0, 90, 60);
        Rectangle rectangle4 = new Rectangle("rectangle4", 0, 0, 70, 50);

        SkipNode<String, Rectangle> node1 = new SkipNode<>("node1", rectangle1,
            0);
        SkipNode<String, Rectangle> node2 = new SkipNode<>("node2", rectangle2,
            0);
        SkipNode<String, Rectangle> node3 = node1;
        SkipNode<String, Rectangle> node4 = new SkipNode<>("node4", rectangle4,
            0);

        // attempt to remove a node that's not in the list before inserting
        assertNull(rectangleList.remove(0, 0, 50, 70));

        // insert nodes
        // head->node3->node1->node2->node4->null
        rectangleList.insert(node4.key(), node4.value());
        rectangleList.insert(node1.key(), node1.value());
        rectangleList.insert(node2.key(), node2.value());
        rectangleList.insert(node3.key(), node3.value());

        // attempt to remove a node that's not in the list
        assertNull(rectangleList.remove(0, 0, 50, 70));

        // remove first occurrence of dimensions (0, 0, 70, 50)
        assertEquals(rectangle1, rectangleList.remove(0, 0, 70, 50));

        // remove first occurrence of dimensions (0, 0, 90, 60)
        assertEquals(rectangle2, rectangleList.remove(0, 0, 90, 60));

        // remove the rest
        assertEquals(rectangle1, rectangleList.remove(0, 0, 70, 50));
        assertEquals(rectangle4, rectangleList.remove(0, 0, 70, 50));
    }


    /**
     * Tests find(K key)
     */
    public void testFind() {
        SkipNode<String, Integer> node1 = new SkipNode<>("node1", Integer
            .valueOf(1), 0);
        SkipNode<String, Integer> node2 = new SkipNode<>("node2", Integer
            .valueOf(2), 0);
        SkipNode<String, Integer> node3 = node1;
        SkipNode<String, Integer> node4 = new SkipNode<>("node4", Integer
            .valueOf(4), 0);

        // try to find a node that doesn't exist in an empty list
        assertNull(integerList.find("node5"));

        // insert nodes
        // head->node3->node1->node2->node4->null
        integerList.insert(node4.key(), node4.value());
        integerList.insert(node1.key(), node1.value());
        integerList.insert(node2.key(), node2.value());
        integerList.insert(node3.key(), node3.value());

        // try to find a node that doesn't exist
        assertNull(integerList.find("node5"));

        // find the first occurrence of node1
        assertTrue(node3.value().equals(integerList.find("node1")));

        // find the first occurrence of node4
        assertTrue(node4.value().equals(integerList.find("node4")));

        // find the next occurrence of node1
        assertTrue(node1.value().equals(integerList.find("node1")));

        // find the first occurrence of node2
        assertTrue(node2.value().equals(integerList.find("node2")));
    }


    /**
     * Tests findAll(K key)
     */
    public void testFindAll() {
        SkipNode<String, Integer> node1 = new SkipNode<>("node1", Integer
            .valueOf(1), 0);
        SkipNode<String, Integer> node2 = new SkipNode<>("node2", Integer
            .valueOf(2), 0);
        SkipNode<String, Integer> node3 = node1;
        SkipNode<String, Integer> node4 = new SkipNode<>("node4", Integer
            .valueOf(4), 0);
        SkipNode<String, Integer> node1Copy = new SkipNode<>("node1", Integer
            .valueOf(0), 0);
        SkipNode<String, Integer> node2Copy = new SkipNode<>("node2", Integer
            .valueOf(0), 0);
        SkipNode<String, Integer> node3Copy = node1;
        SkipNode<String, Integer> node4Copy = new SkipNode<>("node4", Integer
            .valueOf(0), 0);

        // ensure findAll(K key) returns an empty list when no match was found
        assertEquals(new LinkedList<Integer>(), integerList.findAll("node1"));

        // insert nodes
        integerList.insert(node4.key(), node4.value());
        integerList.insert(node1.key(), node1.value());
        integerList.insert(node2.key(), node2.value());
        integerList.insert(node3.key(), node3.value());
        integerList.insert(node4Copy.key(), node4Copy.value());
        integerList.insert(node1Copy.key(), node1Copy.value());
        integerList.insert(node2Copy.key(), node2Copy.value());
        integerList.insert(node3Copy.key(), node3Copy.value());
        integerList.insert(node1.key(), node1.value());

        // return LinkedList size of 5 node1 SkipNodes
        LinkedList<Integer> nodes1 = new LinkedList<>();
        nodes1.add(node1.value());
        nodes1.add(node3Copy.value());
        nodes1.add(node1Copy.value());
        nodes1.add(node3.value());
        nodes1.add(node1.value());
        assertEquals(nodes1, integerList.findAll("node1"));

        // return LinkedList size of 2 node2 SkipNodes
        LinkedList<Integer> nodes2 = new LinkedList<>();
        nodes2.add(node2Copy.value());
        nodes2.add(node2.value());
        assertEquals(nodes2, integerList.findAll("node2"));

        // return LinkedList size of 2 node4 SkipNodes
        LinkedList<Integer> nodes4 = new LinkedList<>();
        nodes4.add(node4Copy.value());
        nodes4.add(node4.value());
        assertEquals(nodes4, integerList.findAll("node4"));

        // return empty LinkedList
        assertTrue(integerList.findAll("node3").isEmpty());
    }


    /**
     * Tests dump()
     */
    public void testDump() {
        SkipNode<String, Integer> head = new SkipNode<>(null, null, 13);
        SkipNode<String, Integer> node1 = new SkipNode<>("node1", Integer
            .valueOf(1), 0);
        SkipNode<String, Integer> node2 = new SkipNode<>("node2", Integer
            .valueOf(2), 0);
        SkipNode<String, Integer> node3 = node1;
        SkipNode<String, Integer> node4 = new SkipNode<>("node4", Integer
            .valueOf(4), 0);

        // return a LinkedList of size one containing the head node
        LinkedList<SkipNode<String, Integer>> allNodes = new LinkedList<>();
        allNodes.add(head);
        assertEquals(allNodes, integerList.dump());

        // first insert nodes
        // head->node3->node1->node2->node4->null
        integerList.insert(node4.key(), node4.value());
        integerList.insert(node1.key(), node1.value());
        integerList.insert(node2.key(), node2.value());
        integerList.insert(node3.key(), node3.value());

        // return all nodes
        allNodes.add(node3);
        allNodes.add(node1);
        allNodes.add(node2);
        allNodes.add(node4);
        assertEquals(allNodes, integerList.dump());
    }


    /**
     * Tests intersections()
     */
    public void testIntersections() {
        Rectangle rectangle1 = new Rectangle("rectangle1", 10, 10, 5, 5);
        Rectangle rectangle2 = new Rectangle("rectangle2", 15, 10, 5, 5);
        Rectangle rectangle3 = new Rectangle("rectangle3", 14, 10, 5, 5);
        Rectangle rectangle4 = new Rectangle("rectangle4", 11, 11, 1, 1);

        SkipNode<String, Rectangle> node1 = new SkipNode<>("node1", rectangle1,
            0);
        SkipNode<String, Rectangle> node2 = new SkipNode<>("node2", rectangle2,
            0);
        SkipNode<String, Rectangle> node3 = new SkipNode<>("node3", rectangle3,
            0);
        SkipNode<String, Rectangle> node4 = new SkipNode<>("node4", rectangle4,
            0);

        assertTrue(rectangleList.intersections().isEmpty());

        // insert nodes; the order after insertion:
        // head->node1->node2->node3->node4->null
        rectangleList.insert(node1.key(), node1.value());
        rectangleList.insert(node2.key(), node2.value());
        rectangleList.insert(node3.key(), node3.value());
        rectangleList.insert(node4.key(), node4.value());

        LinkedList<Rectangle> intersectionPairs = new LinkedList<>();
        // comparing first node's rectangle dimensions
        intersectionPairs.add(rectangle1);
        intersectionPairs.add(rectangle3);
        intersectionPairs.add(rectangle3);
        intersectionPairs.add(rectangle1);
        intersectionPairs.add(rectangle1);
        intersectionPairs.add(rectangle4);
        intersectionPairs.add(rectangle4);
        intersectionPairs.add(rectangle1);

        // comparing second node's rectangle dimensions
        intersectionPairs.add(rectangle2);
        intersectionPairs.add(rectangle3);
        intersectionPairs.add(rectangle3);
        intersectionPairs.add(rectangle2);

        // comparing third node's rectangle dimensions

        // comparing fourth node's rectangle dimensions

        assertEquals(intersectionPairs, rectangleList.intersections());
    }


    /**
     * Tests regionSearch(int x, int y, int width, int height)
     */
    public void testRegionSearch() {
        Rectangle rectangle1 = new Rectangle("rectangle1", 45, 0, 10, 10);
        Rectangle rectangle2 = new Rectangle("rectangle2", 400, 0, 100, 310);

        SkipNode<String, Rectangle> node1 = new SkipNode<>("node1", rectangle1,
            0);
        SkipNode<String, Rectangle> node2 = new SkipNode<>("node2", rectangle2,
            0);

        assertTrue(rectangleList.regionSearch(-900, 5, 5000, 20).isEmpty());

        // insert nodes
        // head->node1->node2->null
        rectangleList.insert(node1.key(), node1.value());
        rectangleList.insert(node2.key(), node2.value());

        LinkedList<Rectangle> regionSearchRectangles = new LinkedList<>();
        regionSearchRectangles.add(rectangle1);
        regionSearchRectangles.add(rectangle2);

        assertEquals(regionSearchRectangles, rectangleList.regionSearch(-900, 5,
            5000, 20));
    }
}
