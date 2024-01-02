/**
 * Stores an index and size indicating where and how much free space there is
 * allocated.
 *
 * @author Dominic Jimeno (dmjimeno0105)
 * @version 1.0
 */
public class FreeBlock implements Comparable<FreeBlock> {
    private int index;
    private int size;

    /**
     * Create a new FreeBlock object.
     *
     * @param index
     *            represents start position
     * @param size
     *            represents amount of free space
     */
    public FreeBlock(int index, int size) {
        this.index = index;
        this.size = size;
    }


    /**
     * Compares this FreeBlock with the specified FreeBlock for order.
     *
     * @param o
     *            the FreeBlock to be compared
     * @return a negative integer, zero, or a positive integer as this FreeBlock
     *         is less than, equal to, or greater than the specified FreeBlock
     */
    @Override
    public int compareTo(FreeBlock o) {
        if (this.index > o.index) {
            return 1;
        }
        else if (this.index < o.index) {
            return -1;
        }
        else {
            return 0;
        }
    }


    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param o
     *            the reference object with which to compare.
     * @return true if this object is the same as the obj argument; false
     *         otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (o.getClass() == this.getClass()) {
            FreeBlock other = (FreeBlock)o;

            return this.compareTo(other) == 0;
        }
        return false;
    }


    /**
     * Returns the value of index.
     *
     * @return index
     */
    public int index() {
        return index;
    }


    /**
     * Sets the value of index to specified value.
     *
     * @param i
     *            the specified value
     */
    public void setIndex(int i) {
        index = i;
    }


    /**
     * Sets the value of size to specified value.
     *
     * @param s
     *            the specified value
     */
    public void setSize(int s) {
        size = s;
    }


    /**
     * Returns the value of size.
     *
     * @return size
     */
    public int size() {
        return size;
    }


    /**
     * Returns a String object representing this FreeBlock's value.
     */
    @Override
    public String toString() {
        return "(" + index + "," + size + ")";
    }
}
