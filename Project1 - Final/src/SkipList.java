import java.util.LinkedList;
import java.util.Random;
import student.TestableRandom;

/**
 * SkipList class
 *
 * @author Dominic Jimeno (dmjimeno0105)
 *
 * @version 9/5/2022
 *
 * @param <K>
 *            generic
 * @param <V>
 *            generic
 */
public class SkipList<K extends Comparable<K>, V> {
    private SkipNode<K, V> head;
    private int level;
    private int size;
    private Random rnd; // Random number generator

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
     * Inserts the node into the SkipList
     *
     * @param key
     *            the key
     * @param value
     *            the value
     */
    public void insert(K key, V value) {
        int newLevel = randomLevel(); // New node's level
        if (newLevel > level) { // If new node is deeper
            adjustHead(newLevel); // adjust the header
        }

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

        // Node to be inserted
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
     *            the level to adjust the head node to
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
     *            the key
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
     * Removes the rectangle with the specified dimensions
     *
     * @param x
     *            top left x coordinate of rectangle
     * @param y
     *            top left y coordinate of rectangle
     * @param width
     *            width of rectangle
     * @param height
     *            height of rectangle
     *
     * @return the rectangle being removed
     *         if the rectangle was found, null otherwise
     */
    public Rectangle remove(int x, int y, int width, int height) {
        SkipNode<K, V> node = head.forward()[0];

        while (node != null) {
            Rectangle rectangle = (Rectangle)node.value();
            // if rectangle found, remove
            if (rectangle.x() == x && rectangle.y() == y && rectangle
                .width() == width && rectangle.height() == height) {
                remove(node.key());
                return rectangle;
            }
            node = node.forward()[0];
        }

        return null;
    }


    /**
     * Returns the first matching element if one exists, null otherwise
     *
     * @param key
     *            the key
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
     *            key used to find all respective nodes
     * @return list of all found SkipNode values
     */
    public LinkedList<V> findAll(K key) {
        LinkedList<V> result = new LinkedList<>();
        SkipNode<K, V> x = internalFind(key); // find first element

        if (x == null) {
            return result;
        }
        // then sequentially traverse list to check for duplicate keys
        while (x != null && x.key().equals(key)) {
            result.add(x.value());
            x = x.forward()[0];
        }

        return result;
    }


    /**
     * Private helper; returns the first matching SkipNode if one exists, null
     * otherwise
     *
     * @param key
     *            the key
     * @return SkipNode, null if node not found
     */
    private SkipNode<K, V> internalFind(K key) {
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
            return x; // Got it
        }

        return null; // Its not there
    }


    /**
     * Returns a list of all SkipNodes in the SkipList
     *
     * @return LinkedList of all SkipNodes in the SkipList
     */
    public LinkedList<SkipNode<K, V>> dump() {
        LinkedList<SkipNode<K, V>> nodes = new LinkedList<>();
        SkipNode<K, V> temp = head;

        // dump() will always return a list of size of at least one since the
        // head exists even in an empty SkipList
        do {
            nodes.add(temp);
            temp = temp.forward()[0]; // move forward through the SkipList
        }
        while (temp != null);

        return nodes;
    }


    /**
     * Reports all pairs of rectangles within the SkipList that intersect
     *
     * @return LinkedList is empty if no intersection pairs were found
     */
    public LinkedList<Rectangle> intersections() {
        LinkedList<Rectangle> intersectionPairs = new LinkedList<>();

        if (this.size < 2) {
            return intersectionPairs;
        }
        SkipNode<K, V> ptr1 = head.forward()[0];
        SkipNode<K, V> ptr2 = ptr1.forward()[0];

        while (ptr1 != null) {
            while (ptr2 != null) {
                if (((Rectangle)ptr1.value()).rectangleIntersects(
                    (Rectangle)ptr2.value())) {
                    // the first intersects the second
                    intersectionPairs.add((Rectangle)ptr1.value());
                    intersectionPairs.add((Rectangle)ptr2.value());
                    // the second intersects the first
                    intersectionPairs.add((Rectangle)ptr2.value());
                    intersectionPairs.add((Rectangle)ptr1.value());
                }
                ptr2 = ptr2.forward()[0];
            }
            ptr1 = ptr1.forward()[0];
            if (ptr1 != null)
                ptr2 = ptr1.forward()[0];
        }

        return intersectionPairs;
    }


    /**
     * Reports all rectangles within the SkipList that intersect the query
     * rectangle specified by the regionSearch parameters
     *
     * @param x
     *            top left x coordinate of query rectangle
     * @param y
     *            top left y coordinate of query rectangle
     * @param width
     *            width of query rectangle
     * @param height
     *            height of query rectangle
     *
     * @return LinkedList is empty if no rectangles were found to intersect the
     *         query rectangle or null if query rectangle was invalid
     */
    public LinkedList<Rectangle> regionSearch(
        int x,
        int y,
        int width,
        int height) {
        LinkedList<Rectangle> rectangles = new LinkedList<Rectangle>();

        Rectangle queryRectangle = new Rectangle("query rectangle", x, y, width,
            height);
        // Check if queryRectangle is valid
        if (queryRectangle.height() <= 0 || queryRectangle.width() <= 0)
            return null;

        // Check if SkipList is empty
        if (this.size == 0) {
            return rectangles;
        }

        // Find rectangles that intersect query rectangle, if any
        SkipNode<K, V> ptr = head.forward()[0];
        while (ptr != null && ptr.value() != null) {
            if (((Rectangle)ptr.value()).rectangleIntersects(queryRectangle))
                rectangles.add((Rectangle)ptr.value());
            ptr = ptr.forward()[0];
        }

        return rectangles;
    }


    // =============== Getters ===============
    /**
     * Returns size
     *
     * @return size
     */
    public int size() {
        return size;
    }
    // ============ End of Getters ============
}
