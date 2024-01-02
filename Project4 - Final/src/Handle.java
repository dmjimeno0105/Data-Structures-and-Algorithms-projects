/**
 * Represents the starting index location of an element in a memory pool.
 *
 * @author Dominic Jimeno (dmjimeno0105)
 * @version 1.0
 */
public class Handle {
    private int index;

    /**
     * Create a new Handle object.
     *
     * @param i
     *            represents the starting index location of an element
     */
    public Handle(int i) {
        index = i;
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
            Handle other = (Handle)o;

            return this.index == other.index();
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
}
