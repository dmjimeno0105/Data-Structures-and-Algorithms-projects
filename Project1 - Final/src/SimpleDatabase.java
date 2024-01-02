import java.util.LinkedList;

/**
 * SimpleDatabase utilizes SkipList to store data
 *
 * @author Dominic Jimeno (dmjimeno0105)
 *
 * @version 9/5/2022
 */
public class SimpleDatabase {
    private SkipList<String, Rectangle> skipList;

    /**
     * SimpleDatabase default constructor, initializes the SkipList and
     * PRQuadtree to contain the data
     */
    public SimpleDatabase() {
        skipList = new SkipList<String, Rectangle>();
    }


    /**
     * Getter method for size of database.
     *
     * @return size
     *         size
     */
    public int size() {
        return skipList.size();
    }


    /**
     * Inserts the rectangle into SkipList
     *
     * @param rectangle
     *            the rectangle containing data
     * @return boolean true if rectangle is valid, false otherwise
     */
    public boolean insert(Rectangle rectangle) {
        if (!rectangle.valid())
            return false;
        skipList.insert(rectangle.name(), rectangle);
        return true;
    }


    /**
     * Removes a rectangle given its name
     *
     * @param name
     *            the name of a rectangle
     * @return null if a rectangle by the specified name doesn't exist,
     *         otherwise return the rectangle that was removed
     */
    public Rectangle remove(String name) {
        return skipList.remove(name);
    }


    /**
     * Removes a rectangle given its x and y coordinates
     *
     * @param x
     *            x coordinate of the rectangle
     * @param y
     *            y coordinate of the rectangle
     * @param width
     *            the width of the rectangle
     * @param height
     *            the height of the rectangle
     * @return null if a rectangle by the specified coordinates doesn't exist,
     *         otherwise return the rectangle that was removed
     */
    public Rectangle remove(int x, int y, int width, int height) {
        return skipList.remove(x, y, width, height);
    }


    /**
     * Report all points in the database that are contained within the region
     * specified by the regionSearch parameters
     *
     * @param x
     *            x coordinate of the region
     * @param y
     *            y coordinate of the region
     * @param width
     *            width of the region
     * @param height
     *            height of the region
     * @return all rectangles within the database that are contained within the
     *         specified region
     */
    public LinkedList<Rectangle> regionSearch(
        int x,
        int y,
        int width,
        int height) {
        return skipList.regionSearch(x, y, width, height);
    }


    /**
     * Report all intersections
     *
     * @return LinkedList of rectangles
     */
    public LinkedList<Rectangle> intersections() {
        return skipList.intersections();
    }


    /**
     * Return the information about the rectangle(s) matching parameter name
     *
     * @param name
     *            name to search for
     * @return list of rectangle(s) matching name
     */
    public LinkedList<Rectangle> search(String name) {
        return skipList.findAll(name);
    }


    /**
     * Returns a "dump" of the database
     *
     * @return dump of the database
     */
    public LinkedList<SkipNode<String, Rectangle>> dump() {
        return skipList.dump();
    }
}
