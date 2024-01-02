import java.nio.ByteBuffer;

/**
 * Contains a large array of bytes. Uses a doubly linked list to keep track of
 * the locations of free blocks within the pool array. This list is referred to
 * as the freeblock list. Uses the best fit rule for selecting which free block
 * to use for a memory request (i.e. the smallest free block in the list that is
 * large enough to store the requested space). If not all space of this block is
 * needed, then the remaining space will make up a new free block and be
 * returned to the free list.
 *
 * @author Dominic Jimeno (dmjimeno0105)
 * @version 1.0
 */
public class MemoryPool {
    private int capacity;
    private int initialCapacity;
    // where k represents the number of bytes in the record
    private static final int K_BYTES_LENGTH = 2;
    private LinkedList<FreeBlock> list;
    private byte[] pool;
    private int size;

    /**
     * Create a new MemoryPool object.
     *
     * @param c
     *            the initial pool capacity
     */
    public MemoryPool(int c) {
        initialCapacity = c;
        capacity = initialCapacity;
        list = new LinkedList<>();
        list.add(new FreeBlock(0, capacity));
        pool = new byte[capacity];
        size = 0;
    }


    /**
     * Finds the smallest free block in the linked list that is large enough to
     * store the requested space.
     *
     * @param space
     *            the amount of space attempting to be stored in bytes
     * @return the starting index of the best fit free block, otherwise -1 if no
     *         block was found
     */
    private int findBestFit(int space) {
        ListIterator<FreeBlock> cursor = list.listIterator(0);
        FreeBlock bestFit = new FreeBlock(-1, Integer.MAX_VALUE);
        FreeBlock block;

        while (cursor.hasNext()) {
            block = cursor.next();
            if (space <= block.size() && block.size() < bestFit.size()) {
                bestFit = block;
            }
        }

        return bestFit.index();
    }


    /**
     * Increases the capacity of the memory pool.
     */
    private void increaseCapacity() {
        byte[] newPool = new byte[capacity + initialCapacity];
        System.arraycopy(pool, 0, newPool, 0, capacity);
        pool = newPool;
        capacity = capacity + initialCapacity;

        FreeBlock newBlock = new FreeBlock(capacity - initialCapacity,
            initialCapacity);
        list.add(newBlock);

        merge(newBlock.index());
    }


    /**
     * Merges any free blocks abutting the specified free block from either
     * side.
     *
     * @param index
     *            the list index of the specified free block
     */
    @SuppressWarnings("null")
    private void merge(int index) {
        // find specified free block
        ListIterator<FreeBlock> cursor = list.listIterator(0);
        FreeBlock block = null;
        while (cursor.hasNext()) {
            block = cursor.next();
            if (block.index() == index) {
                // set the cursor to be right before specified block
                cursor.previous();
                break;
            }
        }

        FreeBlock leftBlock;
        FreeBlock rightBlock;
        // check if there is a left free block
        if (cursor.hasPrevious()) {
            leftBlock = cursor.previous();
            // check if the left free block abuts the specified free block
            if (leftBlock.index() + leftBlock.size() == block.index()) {
                // update specified block to include left block
                block.setIndex(leftBlock.index());
                block.setSize(leftBlock.size() + block.size());
                // remove left block
                cursor.remove();
            }
            // doesn't abut, reset cursor to be right before specified block
            else {
                cursor.next();
            }
        }

        // set cursor to be right after specified block
        cursor.next();

        // check if there is a right free block
        if (cursor.hasNext()) {
            rightBlock = cursor.next();
            // check if the right free block abuts the specified free block
            if (block.index() + block.size() == rightBlock.index()) {
                // update specified block to include right block
                block.setSize(block.size() + rightBlock.size());
                // remove right block
                cursor.remove();
            }
        }
    }


    /**
     * Adds the specified record into the memory pool and returns a handle to
     * that record for reference after insertion.
     *
     * @param record
     *            the specified record to be added
     * @return a handle to the specified record for fast retrieval
     */
    public Handle add(String record) {
        int k = record.getBytes().length;
        int index = findBestFit(K_BYTES_LENGTH + k);

        // if not a large enough freeblock was found
        while (index == -1) {
            increaseCapacity();
            index = findBestFit(K_BYTES_LENGTH + k);
        }

        // insert k and record as bytes
        ByteBuffer buffer = ByteBuffer.allocate(K_BYTES_LENGTH);
        buffer.putShort((short)k);
        byte[] kBytes = buffer.array();
        System.arraycopy(kBytes, 0, pool, index, K_BYTES_LENGTH);
        System.arraycopy(record.getBytes(), 0, pool, index + K_BYTES_LENGTH, k);

        // update/remove freeblock
        ListIterator<FreeBlock> cursor = list.listIterator(0);
        FreeBlock block = null;
        while (cursor.hasNext()) {
            block = cursor.next();
            if (block.index() == index) {
                break;
            }
        }
        assert block != null;
        int blockIndex = index + (K_BYTES_LENGTH + k);
        @SuppressWarnings("null")
        int blockSize = block.size() - (K_BYTES_LENGTH + k);
        // if the size of the block is zero, remove
        if (blockSize == 0) {
            cursor.remove();
        }
        // if there is still space left in the block, update
        else {
            cursor.set(new FreeBlock(blockIndex, blockSize));
        }

        size += (K_BYTES_LENGTH + k);
        return new Handle(index);
    }


    /**
     * Returns the value of current capacity.
     *
     * @return current capacity
     */
    public int capacity() {
        return capacity;
    }


    /**
     * Returns the record referenced by the specified handle from the memory
     * pool.
     *
     * @param handle
     *            the specified handle
     * @return the record referenced by the specified handle
     */
    public String get(Handle handle) {
        // get k to get record string
        byte[] kBytes = new byte[K_BYTES_LENGTH];
        System.arraycopy(pool, handle.index(), kBytes, 0, K_BYTES_LENGTH);
        short k = ByteBuffer.wrap(kBytes).getShort();
        // get record string
        String recordString = new String(pool, handle.index() + K_BYTES_LENGTH,
            k);

        return recordString;
    }


    /**
     * Returns the value of initialCapacity.
     *
     * @return the value of initialCapacity
     */
    public int initialCapacity() {
        return initialCapacity;
    }


    /**
     * Returns true if this memory pool contains no bytes.
     *
     * @return true if this memory pool contains no bytes
     */
    public boolean isEmpty() {
        return size == 0;
    }


    /**
     * Returns the value of the freeblock list of this memory pool.
     *
     * @return the value of the freeblock list of this memory pool
     */
    public LinkedList<FreeBlock> list() {
        return list;
    }


    /**
     * Removes the record referenced by the specified handle from the memory
     * pool.
     *
     * @param handle
     *            the specified handle
     */
    public void remove(Handle handle) {
        // retrieve the number of bytes in the record at handle index
        ByteBuffer wrapped = ByteBuffer.wrap(pool);
        short k = wrapped.getShort(handle.index());

        FreeBlock newBlock = new FreeBlock(handle.index(), K_BYTES_LENGTH + k);

        // update the freeblock list
        ListIterator<FreeBlock> cursor = list.listIterator(0);
        FreeBlock block = null;
        // insert free block, sorting by index in ascending order
        while (cursor.hasNext()) {
            block = cursor.next();
            if (newBlock.index() < block.index()) {
                cursor.previous();
                cursor.add(newBlock);
                break;
            }
        }
        // if block belongs at the end since index is greater than all the rest
        if (!cursor.hasNext()) {
            cursor.add(newBlock);
        }

        merge(newBlock.index());

        size -= (K_BYTES_LENGTH + k);
    }


    /**
     * Returns the number of bytes in this memory pool.
     *
     * @return the number of bytes in this memory pool
     */
    public int size() {
        return size;
    }


    /**
     * Calls free block list toString().
     *
     * @return string representation of free block list
     */
    public String toString() {
        if (list.size() == 0) {
            return "(" + capacity + ",0)";
        }
        return list.toString();
    }
}
