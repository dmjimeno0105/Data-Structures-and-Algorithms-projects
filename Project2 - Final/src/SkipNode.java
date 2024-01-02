/**
 * SkipNode class
 *
 * @author Dominic Jimeno (dmjimeno0105)
 *
 * @version 9/27/2022
 *
 * @param <K>
 *            Key
 * @param <V>
 *            Value
 */
public class SkipNode<K extends Comparable<K>, V> {
    private SkipNode<K, V>[] forward;
    private K key;
    private V value;
    private int level;

    /**
     * SkipNode constructor
     *
     * @param key
     *            Key
     * @param value
     *            Value
     * @param level
     *            Highest SkipList level
     */
    @SuppressWarnings("unchecked")
    public SkipNode(K key, V value, int level) {
        this.key = key;
        this.value = value;
        this.level = level;
        forward = new SkipNode[level + 1];
        for (int i = 0; i <= level; i++)
            forward[i] = null;
    }


    /**
     * Getter method for key
     *
     * @return key
     */
    public K key() {
        return key;
    }


    /**
     * Getter method for value
     *
     * @return value
     */
    public V value() {
        return value;
    }


    /**
     * Getter method for level
     *
     * @return level
     */
    public int level() {
        return level;
    }


    /**
     * Getter method for next node from a specified level
     *
     * @return SkipNode
     */
    public SkipNode<K, V>[] forward() {
        return forward;
    }


    /**
     * Equals method for SkipNode
     *
     * @param obj
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null)
            return false;
        if (this.getClass() == obj.getClass()) {
            SkipNode<K, V> other = (SkipNode<K, V>)obj;
            return key.equals(other.key()) && value.equals(other
                .value());
        }
        return false;
    }
}
