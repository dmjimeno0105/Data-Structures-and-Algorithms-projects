import student.TestableRandom;

/**
 * Rectangle1 test
 *
 * @author Dominic Jimeno (dmjimeno0105)
 *
 * @version 9/5/2022
 *
 */
public class Rectangle1Test extends student.TestCase {

    /**
     * Setup method for testing
     */
    public void setUp() {
        // Rectangle1's main function is statically invoked;
        // no need to declare variables
    }


    /**
     * Tests main
     */
    public void testMain() {
        TestableRandom.setNextBooleans(true, false, false, true, false);

        String[] args = { "SkipListSampleInput.txt" };
        Rectangle1.main(args);

        String output = systemOut().getHistory();
        assertFuzzyEquals("Rectangle rejected: (r_r, -1, -20, 3, 4)\r\n"
            + "Rectangle rejected: (rec, 7, -8, 1, 3)\r\n"
            + "Rectangle rejected: (virtual_rec0, 1, 1, 0, 0)\r\n"
            + "Rectangle rejected: (virtual_REC0, 0, 0, 11, 0)\r\n"
            + "Rectangle rejected: (inExistRec_0, 1, 1, -1, -2)\r\n"
            + "Rectangle rejected: (11, 11, 0, 0)\r\n"
            + "Intersections pairs:\r\n" + "Skiplist dump:\r\n"
            + "Node has depth 1, Value (null)\r\n" + "Skiplist size is: 0\r\n"
            + "Rectangle not found: (r_r)\r\n"
            + "Rectangle not removed: (r_r)\r\n"
            + "Rectangle rejected: (1, 1, 0, 0)\r\n"
            + "Rectangles intersecting region (-5, -5, 20, 20):\r\n"
            + "Rectangle rejected: (5, 5, 0, 0)\r\n"
            + "Rectangle inserted: (goodRect, 5, 3, 56, 56)\r\n"
            + "Rectangle inserted: (goodRect2, 111, 400, 20, 20)\r\n"
            + "Rectangle inserted: (goodRect3, 25, 3, 6, 6)\r\n"
            + "Skiplist dump:\r\n" + "Node has depth 1, Value (null)\r\n"
            + "Node has depth 1, Value (goodRect, 5, 3, 56, 56)\r\n"
            + "Node has depth 0, Value (goodRect2, 111, 400, 20, 20)\r\n"
            + "Node has depth 1, Value (goodRect3, 25, 3, 6, 6)\r\n"
            + "Skiplist size is: 3\r\n"
            + "Rectangle removed: (goodRect2, 111, 400, 20, 20)\r\n"
            + "Intersections pairs:\r\n"
            + "(goodRect, 5, 3, 56, 56 | goodRect3, 25, 3, 6, 6)\r\n"
            + "(goodRect3, 25, 3, 6, 6 | goodRect, 5, 3, 56, 56)\r\n"
            + "Rectangles found:\r\n" + "(goodRect3, 25, 3, 6, 6)\r\n"
            + "Rectangle rejected: (-900, 5, 0, 5000)\r\n" + "", output);
    }
}
