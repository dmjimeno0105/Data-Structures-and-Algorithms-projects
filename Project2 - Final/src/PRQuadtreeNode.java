import java.util.LinkedList;

/**
 * PRQuadtreeNode class
 *
 * @author Dominic Jimeno (dmjimeno0105)
 *
 * @version 9/27/2022
 */
public interface PRQuadtreeNode {

    /**
     * Inserts a point into the PRQuadTree
     *
     * @param point
     *            the point being inserted
     * @param x
     *            the region x coordinate
     * @param y
     *            the region y coordinate
     *
     * @param border
     *            the region border size
     * @return PRQuadtreeNode
     */
    public abstract PRQuadtreeNode insert(
        Point point,
        int x,
        int y,
        int border);


    /**
     * Removes a point given that point's x and y coordinates
     *
     * @param removeX
     *            x coordinate of point to be removed
     * @param removeY
     *            y coordinate of point to be removed
     * @param x
     *            the region x coordinate
     * @param y
     *            the region y coordinate
     * @param border
     *            the region border size
     * @param removed
     *            point removed, null otherwise
     * @return PRQuadtreeNode
     */
    public abstract PRQuadtreeNode remove(
        int removeX,
        int removeY,
        int x,
        int y,
        int border,
        Point[] removed);


    /**
     * Removes a specified point
     *
     * @param removePoint
     *            point to be removed
     * @param x
     *            the region x coordinate
     * @param y
     *            the region y coordinate
     * @param border
     *            the region border size
     * @param removed
     *            point removed, null otherwise
     * @return PRQuadtreeNode
     */
    public abstract PRQuadtreeNode remove(
        Point removePoint,
        int x,
        int y,
        int border,
        Point[] removed);


    /**
     * Parses nodes into strings and stores them
     *
     * @param x
     *            the region x coordinate
     * @param y
     *            the region y coordinate
     * @param border
     *            the region border size
     * @param lines
     *            lines containing information on nodes
     * @param spacing
     *            used to depict different tree levels
     * @param numEncountered
     *            number of times a node is encountered during search
     * @return nodes
     */
    public abstract LinkedList<String> getContents(
        int x,
        int y,
        int border,
        LinkedList<String> lines,
        int spacing,
        int[] numEncountered);


    /**
     * Finds duplicate nodes within the PRQuadtree
     *
     * @param list
     *            list of duplicate points
     *
     * @return list of duplicate points in the PRQuadtree, in string format
     */
    public abstract LinkedList<String> getDuplicates(LinkedList<String> list);


    /**
     * Search a region for points
     *
     * @param regionX
     *            x coordinate of region
     * @param regionY
     *            y coordinate of region
     * @param w
     *            width of region
     * @param h
     *            height of region
     * @param result
     *            points found
     * @param x
     *            x coordinate of point(s)
     * @param y
     *            y coordinate of point(s)
     * @param border
     *            the point's quadrant associated border size
     * @param numEncountered
     *            all points found within the region
     * @return points found in the region
     */
    public abstract LinkedList<Point> regionSearch(
        int regionX,
        int regionY,
        int w,
        int h,
        LinkedList<Point> result,
        int x,
        int y,
        int border,
        int[] numEncountered);


    /**
     * Checks if node is equal to other node
     *
     * @param otherNode
     *            node being compared
     *
     * @return true if equal, false otherwise
     */
    public abstract boolean equals(Object otherNode);


    /**
     * Getter method for number of points in node
     *
     * @return size
     */
    public abstract int size();
}
