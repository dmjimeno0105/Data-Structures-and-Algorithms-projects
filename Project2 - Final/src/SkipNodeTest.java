/**
 * SkipNode tester
 *
 * @author Dominic Jimeno (dmjimeno0105)
 *
 * @version 9/27/2022
 */
public class SkipNodeTest extends student.TestCase {
    private SkipNode<String, Integer> node1;
    private SkipNode<String, Integer> node1Same;

    /**
     * Setup method for testing
     */
    public void setUp() {
        node1 = new SkipNode<String, Integer>("a", Integer.valueOf(50), 2);
        node1Same = new SkipNode<String, Integer>("a", Integer.valueOf(50), 2);
    }


    /**
     * Tests getValue method
     */
    public void testGetValue() {
        assertTrue(node1.value().equals(node1Same.value()));
    }


    /**
     * Tests getKey method
     */
    public void testGetKey() {
        assertEquals(node1.key(), node1Same.key());
    }


    /**
     * Tests getForward method
     */
    public void testGetForward() {
        @SuppressWarnings("unchecked")
        SkipNode<String, Integer>[] forwardCompare = new SkipNode[3];
        for (int i = 0; i < 2; i++) {
            forwardCompare[i] = null;
        }
        for (int i = 0; i < 2; i++) {
            assertEquals(node1.forward()[i], forwardCompare[i]);
        }

    }


    /**
     * Tests getForward method
     */
    public void testGetLevel() {
        assertEquals(node1.level(), 2);
    }


    /**
     * Tests the equals method
     */
    public void testEquals() {
        SkipNode<String, Integer> nodeNull = null;
        Object obj = new Object();
        SkipNode<String, Integer> node2 = new SkipNode<String, Integer>("b",
            Integer.valueOf(60), 2);
        SkipNode<String, Integer> node3 = new SkipNode<String, Integer>("a",
            Integer.valueOf(60), 2);

        assertTrue(node1.equals(node1));
        assertFalse(node1.equals(nodeNull));
        assertTrue(node1.equals(node1Same));
        assertFalse(node1.equals(node2));
        assertFalse(node1.equals(obj));
        assertFalse(node1.equals(node3));

    }

}
