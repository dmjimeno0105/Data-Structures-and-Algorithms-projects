/**
 * SkipNode test
 *
 * @author Dominic Jimeno (dmjimeno0105)
 *
 * @version 9/5/2022
 */
public class SkipNodeTest extends student.TestCase {
    private SkipNode<String, Integer> node;

    /**
     * Setup method for testing
     */
    public void setUp() {
        node = new SkipNode<String, Integer>("node", Integer.valueOf(42), 1);
    }


    /**
     * Tests key()
     */
    public void testKey() {
        assertEquals(node.key(), "node");
    }


    /**
     * Tests value()
     */
    public void testValue() {
        assertTrue(node.value().equals(Integer.valueOf(42)));
    }


    /**
     * Tests level()
     */
    public void testLevel() {
        assertEquals(node.level(), 1);
    }


    /**
     * Tests forward()
     */
    public void testForward() {
        @SuppressWarnings("unchecked")
        SkipNode<String, Integer>[] forwardCompare = new SkipNode[2];
        for (int i = 0; i < 2; i++)
            forwardCompare[i] = null;
        for (int i = 0; i < 2; i++)
            assertEquals(node.forward()[i], forwardCompare[i]);
    }


    /**
     * Tests toString()
     */
    public void testToString() {
        assertEquals("(node, 42)", node.toString());
    }


    /**
     * Tests equals()
     */
    public void testEquals() {
        SkipNode<String, Integer> nodeNull = null;
        SkipNode<String, Integer> nodeEqual = new SkipNode<String, Integer>(
            "node", Integer.valueOf(42), 1);
        SkipNode<String, Integer> nodeNotEqual = new SkipNode<String, Integer>(
            "nodeNotEqual", Integer.valueOf(24), 2);
        Object obj = new Object();

        assertTrue(node.equals(node));
        assertFalse(node.equals(nodeNull));
        assertTrue(node.equals(nodeEqual));
        assertFalse(node.equals(nodeNotEqual));
        assertFalse(node.equals(obj));
    }
}
