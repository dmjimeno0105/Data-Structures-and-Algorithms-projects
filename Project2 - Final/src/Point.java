/**
 * Point class
 *
 * @author Dominic Jimeno (dmjimeno0105)
 *
 * @version 9/27/2022
 */
public class Point implements Comparable<Point> {
    private String name;
    private int x;
    private int y;

    /**
     * Point constructor
     *
     * @param name
     *            name of point
     * @param x
     *            x coordinate of point
     * @param y
     *            y coordinate of point
     */
    public Point(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }


    /**
     * Checks if name of point is less than, equal to, or greater than name of
     * other point
     */
    @Override
    public int compareTo(Point point) {
        return name.compareTo(point.name());
    }


    /**
     * Checks if name and coordinates are equal
     *
     * @param point
     *            the point being compared
     *
     * @return true if name and coordinates are equal, false otherwise
     */
    public boolean equalsPoint(Point point) {
        return equalsName(point.name()) && equalsCoordinates(point);
    }


    /**
     * Checks if name of point is equal to name of other point
     *
     * @param otherName
     *            name of other point
     *
     * @return true if names are equal, false otherwise
     */
    public boolean equalsName(String otherName) {
        return name.equals(otherName);
    }


    /**
     * Checks if coordinates of point are equal to coordinates of other point
     *
     * @param point
     *            point being compared
     *
     * @return true if coordinates are equal, false otherwise
     */
    public boolean equalsCoordinates(Point point) {
        return x == point.x() && y == point.y();
    }


    /**
     * Parses point information into string format
     *
     * @return point information
     */
    public String toString() {
        return name + ", " + x + ", " + y;
    }


    /**
     * Getter method for name of point
     *
     * @return name
     */
    public String name() {
        return name;
    }


    /**
     * Getter method for x coordinate
     *
     * @return x
     */
    public int x() {
        return x;
    }


    /**
     * Getter method for y coordinate
     *
     * @return y
     */
    public int y() {
        return y;
    }
}
