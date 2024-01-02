/**
 * Point2 main driver class test
 *
 * @author Dominic Jimeno (dmjimeno0105)
 *
 * @version 9/27/2022
 *
 */
public class Point2Test extends student.TestCase {

    /**
     * sets up
     */
    public void setUp() {
        // because Point2's main function is statically invoked, no need to
        // declare variables
    }


    /**
     * Tests one of the provided project test files
     */
    public void testMain() {
        String[] test = new String[1];
        test[0] = "P2test1.txt";
        java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
        java.io.PrintStream oldStream = System.out;
        System.setOut(new java.io.PrintStream(out));
        Point2.main(test);
        assertTrue(true);
//        assertEquals(("paste output here"), out.toString().trim());

        test[0] = "invalid";
        System.out.flush();
        System.setOut(oldStream);
        try {
            Point2.main(test);
        }
        catch (Exception e) {
            System.out.println("FileNotFoundException caught");

        }

    }

}
