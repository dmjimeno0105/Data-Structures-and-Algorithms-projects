import java.util.LinkedList;

/**
 * SimpleDatabase class
 *
 * @author Dominic Jimeno (dmjimeno0105)
 *
 * @version 9/27/2022
 */
public class SimpleDatabase {
    private PRQuadtree prQuadTree;
    private SkipList<String, Point> skipList;
    private int size = 0;

    /**
     * SimpleDatabase default constructor, initializes the SkipList and
     * PRQuadtree to contain the data
     */
    public SimpleDatabase() {
        prQuadTree = new PRQuadtree();
        skipList = new SkipList<String, Point>();
        size = 0;
    }


    /**
     * Getter method for size of database.
     *
     * @return size
     *         size
     */
    public int size() {
        return size;
    }


    /**
     * Inserts the point data into both the PRQuadtree and SkipList
     *
     * @param pt
     *            the point containing data
     */
    public void insert(Point pt) {
        skipList.insert(pt.name(), pt);
        prQuadTree.insert(pt);
        size++;
    }


    /**
     * Removes a point given its name
     *
     * @param name
     *            the name of a point
     * @return null if a point by the specified name doesn't exist, otherwise
     *         return the point that was removed
     */
    public Point removeByName(String name) {
        Point removed = skipList.find(name); // check if a
                                             // point labeled
                                             // "name" exists
                                             // within the
                                             // skipList
        if (removed == null) { // check, return null if it doesn't exist
            return null;
        }
        prQuadTree.remove(removed); // remove from PRQuadtree
        skipList.remove(name); // remove from skipList
        size--;
        return removed; // return the point that was removed
    }


    /**
     * Removes a point given its x and y coordinates
     *
     * @param x
     *            x coordinate of the point
     * @param y
     *            y coordinate of the point
     * @return null if a point by the specified coordinates doesn't exist,
     *         otherwise return the point that was removed
     */
    public Point removeByCoords(int x, int y) {
        Point[] removed = prQuadTree.remove(x, y); // check if a point with the
                                                 // specified x and y
        // coordinates exists within the PR Quad Tree,
        if (removed == null) { // check, return null if it doesn't exist
            return null;
        }
        skipList.remove(removed[0].name()); // remove from the skipList
        size--;
        return removed[0];
    }


    /**
     * Report all points in the database that are contained within the region
     * specified by the regionSearch parameters
     *
     * @param x
     *            x coordinate of the region
     * @param y
     *            y coordinate of the region
     * @param w
     *            width of the region
     * @param h
     *            height of the region
     * @return all points within the database that are contained within the
     *         specified region
     */
    public LinkedList<String> regionSearch(int x, int y, int w, int h) {
        int[] numEncountered = { 0 };
        LinkedList<Point> treeSearch = prQuadTree.regionSearch(x, y, w, h,
            numEncountered);
        LinkedList<String> result = new LinkedList<String>();
        result.add("Points Intersecting Region: (" + x + ", " + y + ", " + w
            + ", " + h + ")");
        for (int i = 0; i < treeSearch.size(); i++) {
            result.add("Point Found: (" + treeSearch.get(i).toString() + ")");
        }
        result.add(numEncountered[0] + " QuadTree Nodes Visited");
        return result;

    }


    /**
     * Report all points that have duplicate coordinates
     *
     * @return all points within the database that have duplicate coordinates
     */
    public LinkedList<String> duplicates() {
        return prQuadTree.duplicates();
    }


    /**
     * Return the information about the point(s) matching parameter name
     *
     * @param name
     *            name to search for
     * @return list of point(s) matching name
     */
    public LinkedList<String> search(String name) {
        SkipNode<String, Point>[] found = skipList.findAll(name);
        LinkedList<String> result = new LinkedList<String>();
        if (found.length == 1) {
            result.add("Point Not Found: " + name);
        }
        for (int i = 0; i < found.length; i++) {
            if (found[i] != null) {
                result.add("Point Found (" + found[i].key() + ", " + found[i]
                    .value().x() + ", " + found[i].value().y() + ")");
            }
        }
        return result;

    }


    /**
     * Returns a "dump" of the database
     *
     * @return dump of the database
     */
    public LinkedList<String> dump() {
        LinkedList<String> result = new LinkedList<String>();
        result.add("SkipList Dump:");
        SkipNode<String, Point>[] listDump = skipList.dump();
        if (listDump.length == 1) {
            result.add("level: 1 Value: null");
        }
        else {
            for (int i = 0; i < listDump.length; i++) {

                if (listDump[i].value() != null) {

                    result.add("level: " + (listDump[i].forward().length)
                        + " Value: (" + listDump[i].value().toString() + ")");
                }
                else {
                    result.add("level: " + (listDump[i].forward().length)
                        + " Value: " + listDump[i].value());
                }

            }
        }
        result.add("The SkipList's Size is: " + (listDump.length - 1));
        LinkedList<String> treeDump = prQuadTree.dump();
        result.add("QuadTree Dump:");
        for (int i = 0; i < treeDump.size(); i++) {
            result.add(treeDump.get(i));
        }
        return result;

    }


    /**
     * Checks if the given x and y coordinates are within the canvas's region
     *
     * @param x
     *            x coordinate of a point
     * @param y
     *            y coordinate of a point
     * @return true if the coordinates are within the canvas region, false
     *         otherwise
     */
    public boolean ifValidPoint(int x, int y) {
        return x >= 0 && x < 1024 && y >= 0 && y < 1024;
    }
}
