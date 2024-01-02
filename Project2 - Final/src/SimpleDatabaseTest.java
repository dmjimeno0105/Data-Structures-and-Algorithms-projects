import java.util.LinkedList;

/**
 * Simple database utilizing PR QuadTree and SkipList to store data test
 *
 * @author Dominic Jimeno (dmjimeno0105)
 *
 * @version 9/27/2022
 *
 */
public class SimpleDatabaseTest extends student.TestCase {
    private SimpleDatabase db;
    private Point pt;

    /**
     * Sets up objects.
     */
    public void setUp() {
        db = new SimpleDatabase();
        pt = new Point("pt", 0, 0);
        db.insert(pt);
    }


    /**
     * a description
     */
    public void testSize() {
        assertEquals(1, db.size());
    }


    /**
     * Inserts the point data into both the PRQuadtree and SkipList
     */
    public void testInsert() {
        Point pt1 = new Point("pt1", 1, 1);
        db.insert(pt1);
        assertEquals(2, db.size());
    }


    /**
     * Removes a point given its name
     */
    public void testRemoveByName() {
        db.removeByName("pt");
        assertEquals(0, db.size());
        assertNull(db.removeByName(null));
    }


    /**
     * Removes a point given its x and y coordinates
     */
    public void testRemoveByCoords() {
        db.removeByCoords(0, 0);
        assertEquals(0, db.size());
        assertNull(db.removeByCoords(1, 1));
    }


    /**
     * Report all points in the database that are contained within the region
     * specified by the regionSearch parameters
     */
    public void testRegionSearch() {
        LinkedList<String> list = new LinkedList<>();
        list.add("Points Intersecting Region: (0, 0, 1, 1)");
        list.add("Point Found: (pt, 0, 0)");
        list.add("1 QuadTree Nodes Visited");
        assertEquals(list, db.regionSearch(0, 0, 1, 1));
    }


    /**
     * Report all points that have duplicate coordinates
     */
    public void testDuplicates() {
        Point pt2 = new Point("pt2", 0, 0);
        db.insert(pt2);
        LinkedList<String> list = new LinkedList<>();
        list.add("0, 0");
        assertEquals(list, db.duplicates());
    }


    /**
     * Return the information about the point(s) matching parameter name
     */
    public void testSearch() {
        LinkedList<String> list = new LinkedList<>();
        list.add("Point Found (pt, 0, 0)");
        assertEquals(list, db.search("pt"));
        LinkedList<String> list1 = new LinkedList<>();
        list1.add("Point Not Found: noPt");
        assertEquals(list1, db.search("noPt"));
    }


    /**
     * Returns a "dump" of the database
     */
    public void testDump() {
        LinkedList<String> myList = new LinkedList<>();
        LinkedList<String> compareList;
        myList.add("SkipList Dump:");
        // myList.add("level: 3 Value: null");
        // myList.add("level: 2 Value: (pt, 0, 0)");
        myList.add("The SkipList's Size is: 1");
        myList.add("QuadTree Dump:");
        myList.add("Node at 0, 0, 1024:");
        myList.add("(pt, 0, 0)");
        myList.add("QuadTree Size: 1 QuadTree Nodes Printed.");
        compareList = db.dump();
        compareList.remove(1); // bc level is always different,
        compareList.remove(1); // remove indices that contain level
        assertEquals(myList, compareList);
        LinkedList<String> list1 = new LinkedList<>();
        SimpleDatabase listDb = new SimpleDatabase();
        list1.add("SkipList Dump:");
        list1.add("level: 1 Value: null");
        list1.add("The SkipList's Size is: 0");
        list1.add("QuadTree Dump:");
        list1.add("Node at 0, 0, 1024: Empty");
        list1.add("QuadTree Size: 1 QuadTree Nodes Printed.");
        assertEquals(listDb.dump(), list1);
    }


    /**
     * Checks if the given x and y coordinates are within the canvas's region
     */
    public void testIfValidPoint() {
        assertTrue(db.ifValidPoint(0, 0));
        assertTrue(db.ifValidPoint(512, 42));
        assertTrue(db.ifValidPoint(42, 42));
        assertTrue(db.ifValidPoint(42, 512));
        assertFalse(db.ifValidPoint(1024, 0));
        assertFalse(db.ifValidPoint(0, 1024));
        assertFalse(db.ifValidPoint(-1, 0));
        assertFalse(db.ifValidPoint(0, -1));
    }

}
