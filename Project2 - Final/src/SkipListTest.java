/**
 * SkipList tester
 *
 * @author Dominic Jimeno (dmjimeno0105)
 *
 * @version 9/27/2022
 */
public class SkipListTest extends student.TestCase {

    /**
     * Setup method for testing
     */
    public void setUp() {
        // left empty due to objects be instantiated in testMethods method
    }


    /**
     * Tests various methods relating to the SkipList and SkipNode classes
     */
    public void testMethods() {
        // Tests SkipList's default constructor
        SkipList<String, Integer> emptyList = new SkipList<String, Integer>();
//        assertNull(emptyList.getHead().getKey());
//        assertNull(emptyList.getHead().getValue());
//        assertEquals(emptyList.getHead().getLevel(), 1);

        // Instantiate nodes
        SkipNode<String, Integer> node2 = new SkipNode<String, Integer>("c",
            Integer.valueOf(60), 0);
        SkipNode<String, Integer> nodeLess = new SkipNode<String, Integer>("a",
            Integer.valueOf(40), 0);
        SkipNode<String, Integer> node1 = new SkipNode<String, Integer>("b",
            Integer.valueOf(50), 0);
        SkipNode<String, Integer> least = new SkipNode<String, Integer>("z",
            Integer.valueOf(100), 0);

        // Test SkipList constructor(s)
//        SkipList<String, Integer> listNode = new SkipList<String, Integer>(
//            node2);
//        assertTrue(listNode.getHead().equals(node2));
//        SkipList<String, Integer> list1 = new SkipList<String, Integer>(node2
//            .key(), node2.value(), node2.level());
//        assertTrue(list1.getHead().equals(node2));

        // Tests SkipList's "find" method and "insert" methods
//        list1.insert(node1.key(), node1.value());
//        list1.insert(nodeLess.key(), nodeLess.value());
//        list1.insert(node2.key(), node2.value());
//
//        assertEquals(list1.find("a"), (nodeLess));
//        assertEquals(list1.find("b"), (node1));
//        assertEquals(list1.find("c"), (node2));
//        assertNull(list1.find("d"));
//        assertNull(emptyList.find("e"));
//
//        // Tests the SkipList's "remove method"
//        assertNull(list1.remove("f"));
//        assertTrue(list1.remove("c").equals("c"));
//        assertNull(list1.find("c"));
//        assertTrue(list1.remove("b").equals("b"));
//        assertNull(list1.find("b"));
//
//        // Tests the SkipList's getSize method
//        assertEquals(2, list1.size());

        emptyList.insert(node1.key(), node1.value());
        emptyList.insert(node1.key(), node1.value());
        emptyList.insert(least.key(), least.value());

    }

}
