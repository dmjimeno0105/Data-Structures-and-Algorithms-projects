import java.util.Random;
import student.TestableRandom;

/**
 * SkipList class
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
public class SkipList<K extends Comparable<K>, V> {
    private SkipNode<K, V> head;
    private int level;
    private int size;
    private Random rnd = new Random();

    /**
     * Creates an empty SkipList
     */
    public SkipList() {
        head = new SkipNode<K, V>(null, null, 1);
        level = 1;
        size = 0;
        rnd = new TestableRandom();
    }


    /**
     * Insert the node into the SkipList
     *
     * @param key
     *            qwerty
     * @param value
     *            qwerty
     */
    public void insert(K key, V value) {
        int newLevel = randomLevel(); // New node's level
        if (newLevel > level) // If new node is deeper
            adjustHead(newLevel); // adjust the header

        // Track end of level; use update to point to node to be inserted
        @SuppressWarnings("unchecked")
        SkipNode<K, V>[] update = new SkipNode[level + 1];
        SkipNode<K, V> x = head; // Start at header node

        for (int i = level; i >= 0; i--) { // Find insert position
            while ((x.forward()[i] != null) && (x.forward()[i].key().compareTo(
                key) < 0))
                x = x.forward()[i];
            update[i] = x; // Track end at level i
        }

        x = new SkipNode<K, V>(key, value, newLevel);
        for (int i = 0; i <= newLevel; i++) { // Splice into list
            x.forward()[i] = update[i].forward()[i]; // Who x points to
            update[i].forward()[i] = x; // Who points to x
        }

        size++; // Increment dictionary size
    }


    /**
     * Picks a level using a geometric distribution
     *
     * @return random level number
     */
    private int randomLevel() {
        int lev;
        for (lev = 0; rnd.nextBoolean(); lev++) {
            // advance level
        }
        return lev;
    }


    /**
     * Updates the head node's level
     *
     * @param newLevel
     */
    private void adjustHead(int newLevel) {
        SkipNode<K, V> temp = head;
        head = new SkipNode<K, V>(null, null, newLevel);
        for (int i = 0; i <= level; i++)
            head.forward()[i] = temp.forward()[i];
        level = newLevel;
    }


    /**
     * Removes the node from the SkipList
     *
     * @param key
     *            qwerty
     *
     * @return the value of the node being removed
     *         if the node was found, null otherwise
     */
    public V remove(K key) {
        // Track end of level; use update to point to the node that follows the
        // node to be removed
        @SuppressWarnings("unchecked")
        SkipNode<K, V>[] update = new SkipNode[level + 1];
        SkipNode<K, V> x = head; // Start at header node

        for (int i = level; i >= 0; i--) { // Find remove position
            while ((x.forward()[i] != null) && (x.forward()[i].key().compareTo(
                key) < 0))
                x = x.forward()[i];
            update[i] = x; // Track end at level i
        }

        // Checks if node was found
        if (update[0].forward()[0] != null && update[0].forward()[0].key()
            .equals(key)) {
            // Node's value to be returned
            SkipNode<K, V> node = update[0].forward()[0];
            for (int i = 0; i <= level; i++) { // Splice into list
                if (update[i].forward()[i] != null)
                    update[i].forward()[i] = update[i].forward()[i]
                        .forward()[i];
            }
            size--; // Decrement dictionary size
            return node.value();
        }

        return null;
    }


    /**
     * Returns the first matching element if one exists, null otherwise
     *
     * @param key
     *            qwerty
     *
     * @return SkipNode value, null if node not found
     */
    public V find(K key) {
        if (size == 0) {
            return null;
        }
        SkipNode<K, V> x = head; // Dummy header node

        for (int i = level; i >= 0; i--) { // For each level...
            while ((x.forward()[i] != null) && (x.forward()[i].key().compareTo(
                key) < 0)) // go forward
                x = x.forward()[i]; // Go one last step
        }
        x = x.forward()[0]; // Move to actual record, if it exists

        // Check if x holds actual record
        if ((x != null) && (x.key().compareTo(key) == 0)) {
            return x.value(); // Got it
        }

        return null; // Its not there
    }


    /**
     * Finds all nodes matching the given key
     *
     * @param key
     *            key used to find all nodes matching it
     * @return list of all SkipNodes matching the given key
     */
    @SuppressWarnings("unchecked")
    public SkipNode<K, V>[] findAll(K key) {
        SkipNode<K, V>[] result = new SkipNode[1];
        SkipNode<K, V> x = head; // Dummy header node
        int startLevel = level;
        while (x != null) {
            for (int i = startLevel; i >= 0; i--) { // For each level...

                try { // try-catch to handle errors
                    while ((x.forward()[i] != null) && // Go getForward()
                        (key.compareTo(x.forward()[i].key()) > 0)) {
                        x = x.forward()[i]; // Go one last step
                    }
                }
                catch (Exception e) {
                    return result;
                }
            }
            x = x.forward()[0]; // Move to actual record, if it exists

            if ((x != null) && (key.compareTo(x.key()) == 0)) {

                // makes an array of size one larger than result
                SkipNode<K, V>[] tempArray = new SkipNode[result.length + 1];
                // copies everything from result into tempArray
                System.arraycopy(result, 0, tempArray, 0, result.length);
                // makes a skipnode copy of the one found in the skiplist
                tempArray[tempArray.length - 1] = new SkipNode<K, V>(key, x
                    .value(), 0);
                result = tempArray; // result is now tempArray
                startLevel = x.forward().length - 1;
            }

        }
        return result;
    }


    /**
     * Creates a dump of all the SkipNode in the SkipList
     *
     * @return array of all SkipNodes in the SkipList
     */
    @SuppressWarnings("unchecked")
    public SkipNode<K, V>[] dump() {
        SkipNode<K, V> temp = head;
        SkipNode<K, V>[] nodes = new SkipNode[1];
        int length = 0;
        while (temp != null) {
            if (length == 0) {
                nodes[0] = new SkipNode<K, V>(temp.key(), temp.value(), temp
                    .forward().length);
                length++;
            }
            else {
                SkipNode<K, V>[] tempArray = new SkipNode[nodes.length + 1];
                System.arraycopy(nodes, 0, tempArray, 0, nodes.length);
                tempArray[tempArray.length - 1] = new SkipNode<K, V>(temp.key(),
                    temp.value(), temp.forward().length);
                nodes = tempArray;
            }
            temp = temp.forward()[0];
        }
        return nodes;
    }


    /**
     * Getter method for size
     *
     * @return size
     */
    public int size() {
        return size;
    }
}
