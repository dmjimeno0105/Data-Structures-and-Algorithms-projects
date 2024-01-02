/**
 * Rectangle class
 *
 * @author Dominic Jimeno (dmjimeno0105)
 *
 * @version 9/5/2022
 *
 */
public class Rectangle implements Comparable<Rectangle> {
    private String name;
    private int x;
    private int y;
    private int width;
    private int height;

    /**
     * Rectangle constructor
     *
     * @param name
     *            name of rectangle
     * @param x
     *            x coordinate
     * @param y
     *            y coordinate
     * @param width
     *            the width
     * @param height
     *            the height
     */
    public Rectangle(String name, int x, int y, int width, int height) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }


    /**
     * Checks to see if this rectangle intersects with other rectangle
     *
     * @param rectangle
     *            other rectangle
     * @return true if this rectangle intersects with other rectangle, false
     *         otherwise
     */
    public boolean rectangleIntersects(Rectangle rectangle) {
        // this rectangle
        int top1 = this.y + this.height;
        int bottom1 = this.y;
        int left1 = this.x;
        int right1 = this.x + this.width;
        // other rectangle
        int top2 = rectangle.y() + rectangle.height();
        int bottom2 = rectangle.y();
        int left2 = rectangle.x();
        int right2 = rectangle.x() + rectangle.width();

        if (top1 <= bottom2 || bottom1 >= top2) {
            return false;
        }
        return !(left1 >= right2 || right1 <= left2);
    }


    /**
     * Checks if the given rectangle has a height and width greater than 0 and
     * fits within the canvas region
     *
     * @return true rectangle has a height and width greater than 0 and fits
     *         within the world box, false otherwise
     */
    public boolean valid() {
        int top = this.y + this.height;
        int bottom = this.y;
        int left = this.x;
        int right = this.x + this.width;

        if (this.height <= 0 || this.width <= 0)
            return false;
        return !(top > 1024 || bottom < 0 || left < 0 || right > 1024);
    }


    /**
     * Compares rectangle. If -1 is returned, rectangle has a smaller or equal
     * to area.
     * If 1 is returned, rectangle has a larger area. If zero is
     * returned, rectangles are exactly the same.
     *
     * @param rectangle
     *            rectangle being compared against
     * @return 0
     *         if rectangles are exactly the same, -1 if this rectangle's area
     *         is smaller or equal to, or 1 if this rectangle's area is larger
     */
    public int compareTo(Rectangle rectangle) {
        // if this rectangle is exactly the same
        if (this.name.equals(rectangle.name()) && this.x == rectangle.x()
            && this.y == rectangle.y() && this.width == rectangle.width()
            && this.height == rectangle.height()) {
            return 0;
        }
        // if this rectangle's area is smaller or equal to
        else if (this.width * this.height <= rectangle.width() * rectangle
            .height()) {
            return -1;
        }
        // if this rectangle's area is larger
        else {
            return 1;
        }
    }


    /**
     * Checks if rectangles are exactly the same
     *
     * @param rectangle
     *            rectangle being compared against
     * @return true if rectangles are exactly the same, false otherwise
     */
    public boolean equals(Object rectangle) {
        return (this.compareTo((Rectangle)rectangle) == 0) ? true : false;
    }


    /**
     * Parses rectangle information into string format
     *
     * @return rectangle's attributes in string format
     */
    public String toString() {
        return "(" + name + ", " + x + ", " + y + ", " + width + ", " + height
            + ")";
    }


    // =============== Getters ===============
    /**
     * Returns name of rectangle
     *
     * @return name
     */
    public String name() {
        return name;
    }


    /**
     * Returns top left x coordinate
     *
     * @return x
     */
    public int x() {
        return x;
    }


    /**
     * Returns top left y coordinate
     *
     * @return y
     */
    public int y() {
        return y;
    }


    /**
     * Returns width
     *
     * @return width
     */
    public int width() {
        return width;
    }


    /**
     * Returns height
     *
     * @return height
     */
    public int height() {
        return height;
    }
    // ============ End of Getters ============
}
