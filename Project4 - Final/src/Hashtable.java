/**
 * This class implements a hash table, which stores handles mapped to by strings
 * stored in a memory pool.
 *
 * @author Dominic Jimeno (dmjimeno0105)
 * @version 1.0
 */
public class Hashtable {
    private int capacity;
    private MemoryPool memoryPool;
    private int size;
    private Handle[] table;
    private Handle tombstone;

    /**
     * Constructs a new, empty hashtable with the specified initial capacity.
     *
     * @param initialCapacity
     *            the initial capacity of the hashtable
     * @param pool
     *            which store the strings that map to the handles in the
     *            hashtable
     */
    public Hashtable(int initialCapacity, MemoryPool pool) {
        capacity = initialCapacity;
        table = new Handle[capacity];
        size = 0;
        tombstone = new Handle(-1);
        memoryPool = pool;
    }


    /**
     * Returns the slot of the handle for the specified string.
     *
     * @param s
     *            the specified string to hash to find its handle
     * @return the slot of the string handle if found, otherwise -1 if not found
     */
    private int find(String s) {
        int initialSlot = hash(s);
        int slot = initialSlot;

        for (int i = 1; i < capacity; i++) {
            // if handle not found
            if (table[slot] == null) {
                return -1;
            }
            // if handle found
            else if (!table[slot].equals(tombstone) && memoryPool.get(
                table[slot]).equals(s)) {
                return slot;
            }
            // else continue probing
            slot = (initialSlot + i * i) % capacity;
        }

        // handle not found after searching full probe sequence
        return -1;
    }


    /**
     * Returns true if hashtable is half full.
     *
     * @return true if hashtable is half full
     */
    private boolean halfFull() {
        return size == capacity / 2;
    }


    /**
     * Maps the specified string to its handle and returns the slot of the
     * handle. Uses string folding.
     *
     * @param s
     *            the specified string
     * @return the slot of where the handle is to be stored in the hashtable
     */
    private int hash(String s) {
        int intLength = s.length() / 4;
        long sum = 0;

        for (int j = 0; j < intLength; j++) {
            char[] c = s.substring(j * 4, (j * 4) + 4).toCharArray();
            long mult = 1;
            for (int k = 0; k < c.length; k++) {
                sum += c[k] * mult;
                mult *= 256;
            }
        }
        char[] c = s.substring(intLength * 4).toCharArray();
        long mult = 1;

        for (int k = 0; k < c.length; k++) {
            sum += c[k] * mult;
            mult *= 256;
        }

        return (int)(Math.abs(sum) % capacity);
    }


    /**
     * Increases the capacity of the hash table and rehashes all handles.
     */
    private void increaseCapacity() {
        Handle[] oldTable = table;
        table = new Handle[oldTable.length * 2];
        capacity = table.length;
        size = 0; // new hashtable initially contains nothing

        // rehash handles into new hashtable
        for (int i = 0; i < oldTable.length; i++) {
            // if index has a handle; don't transfer nulls and tombstones
            if (oldTable[i] != null && !oldTable[i].equals(tombstone)) {
                String record = memoryPool.get(oldTable[i]);
                put(record, oldTable[i]);
            }
        }
    }


    /**
     * Returns the value of capacity.
     *
     * @return the value of capacity
     */
    public int capacity() {
        return capacity;
    }


    /**
     * Returns the handle for the specified string.
     *
     * @param s
     *            the specified string; attempts to find its handle
     * @return the handle if found, otherwise null if not found
     */
    public Handle get(String s) {
        int slot = find(s);

        // if handle found
        if (slot != -1) {
            return table[slot];
        }

        return null;
    }


    /**
     * Tests if this hashtable is empty.
     *
     * @return true if this hashtable is empty
     */
    public boolean isEmpty() {
        return size == 0;
    }


    /**
     * Maps the specified handle to the specified string in the memory pool.
     * Caller should check for duplicates using get().
     *
     * @param s
     *            the specified string used to hash a location for handle
     * @param h
     *            the specified handle to be inserted
     */
    public void put(String s, Handle h) {
        if (halfFull()) {
            increaseCapacity();
        }
        int initialSlot = hash(s);
        int slot = initialSlot;

        for (int i = 0; i < capacity; i++) {
            // if empty slot or tombstone found, insert
            if (table[slot] == null) {
                table[slot] = h;
                size++;
                break;
            }
            else if (table[slot].equals(tombstone)) { // CHECK might not need
                table[slot] = h;
                size++;
                break;
            }
            // else continue probing
            slot = (initialSlot + i * i) % capacity;
        }
    }


    /**
     * Removes the handle for the specified string from this hashtable.
     *
     * @param s
     *            the string used to map to the handle in the table
     * @return the removed handle, otherwise null if handle to remove not found
     */
    public Handle remove(String s) {
        int slot = find(s);

        // if handle found
        if (slot != -1) {
            Handle removed = table[slot];
            table[slot] = tombstone;
            size--;
            return removed;
        }

        return null;
    }


    /**
     * Returns the size of the hashtable.
     *
     * @return the size of the hashtable
     */
    public int size() {
        return size;
    }


    /**
     * Returns the string representation of all strings stored in the memory
     * pool.
     *
     * @param tableCategory
     *            the category of data this hashtable is storing handles for
     *            (i.e. artists or songs)
     * @return the string representation of all strings stored in the memory
     *         pool
     */
    public String toString(String tableCategory) {
        // iterate through table and if there is a handle, retrieve string
        // record from memory pool to build string
        StringBuilder builder = new StringBuilder();
        int total = 0;

        for (int i = 0; i < capacity; i++) {
            if (table[i] != null && !table[i].equals(tombstone)) {
                // retrieves string and its handle slot
                builder.append("|" + memoryPool.get(table[i]) + "| " + i
                    + "\n");
                total++;
            }
        }
        builder.append("total " + tableCategory + ": " + total);

        return builder.toString();
    }
}
