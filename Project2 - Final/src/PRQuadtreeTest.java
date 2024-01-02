import java.util.LinkedList;
import junit.framework.TestCase;

// On my honor:
//
// - I have not used source code obtained from another student,
// or any other unauthorized source, either modified or
// unmodified.
//
// - All source code and documentation used in my program is
// either my original work, or was derived by me from the
// source code published in the textbook for this course.
//
// - I have not discussed coding details about this project with
// anyone other than the instructor, ACM/UPE tutors, programming
// partner (if allowed in this class), or the TAs assigned to
// this course. I understand that I may discuss the concepts
// of this program with other students, and that another student
// may help me debug my program so long as neither of us writes
// anything during the discussion or modifies any computer file
// during the discussion. I have violated neither the spirit nor
// letter of this restriction.

/**
 * PRQuadtree data structure class test
 *
 * @author Dominic Jimeno (dmjimeno0105), Tony Phonemany (phonemanytony)
 *
 * @version 10/20/21
 *
 *
 */
public class PRQuadtreeTest extends TestCase {
    private PRQuadtree tree;
    private Point pt;

    /**
     * sets up
     */
    public void setUp() {
        tree = new PRQuadtree();
        pt = new Point("pt", 0, 0);
        tree.insert(pt);
    }


    /**
     * size(): Getter method for size
     */
    public void testSize() {
        assertEquals(1, tree.size());
    }


    /**
     * insert(): Inserts a point into the quadtree
     */
    public void testInsert() {
        Point pt1 = new Point("pt1", 0, 0);
        tree.insert(pt1);
        assertEquals(2, tree.size());
    }


    /**
     * remove(): Removes a point from the quadtree given coordinates
     */
    public void testRemove1() {
        tree.remove(pt);
        assertEquals(0, tree.size());
    }


    /**
     * second remove(): Removes a point from the quadtree given a point
     */
    public void testRemove2() {
        tree.remove(0, 0);
        assertEquals(0, tree.size());
    }


    /**
     * duplicates(): Obtains a linkedlist of duplicates points within the tree
     */
    public void testDuplicates() {
        LinkedList<String> list = new LinkedList<>();
        list.add("0, 0");
        tree.insert(pt);
        assertEquals(list, tree.duplicates());
    }


    /**
     * regionSearch(): Searches a given region for all the points contained in
     * it
     */
    public void testRegionSearch() {
        LinkedList<Point> list = new LinkedList<>();
        list.add(pt);
        int[] numEncountered = new int[1];
        assertEquals(list, tree.regionSearch(0, 0, 1, 1, numEncountered));
    }


    /**
     * dump(): Creates a dump of the contents of the tree
     */
    public void testDump() {
        LinkedList<String> list = new LinkedList<>();
        list.add("Node at 0, 0, 1024:");
        list.add("(pt, 0, 0)");
        list.add("QuadTree Size: 1 QuadTree Nodes Printed.");
        assertEquals(list, tree.dump());
    }
}
