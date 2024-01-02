/**
 * SkipNode class
 *
 * @author Dominic Jimeno (dmjimeno0105)
 *
 * @version 9/5/2022
 *
 * @param <K>
 *            key
 * @param <V>
 *            value
 */
public class SkipNode<K extends Comparable<K>, V> {
    private K key;
    private V value;
    private int level;
    private SkipNode<K, V>[] forward;

    /**
     * SkipNode constructor
     *
     * @param key
     *            the key
     * @param value
     *            the value
     * @param level
     *            highest SkipList level
     */
    @SuppressWarnings("unchecked")
    public SkipNode(K key, V value, int level) {
        this.key = key;
        this.value = value;
        this.level = level;
        this.forward = new SkipNode[level + 1];
        for (int i = 0; i <= level; i++)
            this.forward[i] = null;
    }


    /**
     * Checks if two SkipNodes are equal
     *
     * @param obj
     *            the other SkipNode
     * @return true if the keys and values of both nodes match, false otherwise
     */
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null)
            return false;
        if (this.getClass() == obj.getClass()) {
            @SuppressWarnings("unchecked")
            SkipNode<K, V> other = (SkipNode<K, V>)obj;
            // one of the keys is null and one is not
            if ((this.key() == null && other.key() != null) || (other
                .key() == null && this.key() != null))
                return false;
            // one of the values is null and one is not
            else if ((this.value() == null && other.value() != null) || (other
                .value() == null && this.value() != null))
                return false;
            // both keys and values are null
            if ((this.key() == null && other.key() == null) && (this
                .value() == null && other.value() == null))
                return true;
            return this.key().equals(other.key()) && this.value().equals(other
                .value());
        }

        return false;
    }


    /**
     * Displays SkipNode key and value
     *
     * @return SkipNode as (key, value)
     */
    public String toString() {
        return "(" + key + ", " + value + ")";
    }


    // =============== Getters ===============
    /**
     * Returns key
     *
     * @return key
     */
    public K key() {
        return key;
    }


    /**
     * Returns value
     *
     * @return value
     */
    public V value() {
        return value;
    }


    /**
     * Returns level
     *
     * @return level
     */
    public int level() {
        return level;
    }


    /**
     * Returns next node or null if there is no next node
     *
     * @return SkipNode
     */
    public SkipNode<K, V>[] forward() {
        return forward;
    }
    // ============ End of Getters ============
}
