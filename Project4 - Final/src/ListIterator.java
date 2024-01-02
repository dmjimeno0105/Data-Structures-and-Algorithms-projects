/**
 * An iterator for lists that allows the programmer to traverse the list in
 * either direction, modify the list during iteration, and obtain the iterator's
 * current position in the list. A ListIterator has no current element; its
 * cursor position always lies between the element that would be returned by a
 * call to previous() and the element that would be returned by a call to
 * next(). An iterator for a list of length n has n+1 possible cursor positions.
 *
 * Note that the remove() and set(Object) methods are not defined in terms of
 * the cursor position; they are defined to operate on the last element returned
 * by a call to next() or previous().
 *
 * @author Dominic Jimeno (dmjimeno0105)
 * @version 1.0
 * @param <E>
 *            the elements with which this iterator is able to add, remove, set,
 *            and traverse through
 */
public interface ListIterator<E> {

    /**
     * Inserts the specified element into the list (optional operation).
     *
     * @param e
     *            the element to insert
     * @throws UnsupportedOperationException
     *             if the set operation is not supported by this list iterator
     * @throws ClassCastException
     *             if the class of the specified element prevents it from being
     *             added to this list
     * @throws IllegalArgumentException
     *             if some aspect of the specified element prevents it from
     *             being added to this list
     */
    public abstract void add(E e)
        throws UnsupportedOperationException,
        ClassCastException,
        IllegalArgumentException;


    /**
     * Returns true if this list iterator has more elements when traversing the
     * list in the forward direction.
     *
     * @return true if the list iterator has more elements when traversing the
     *         list in the forward direction
     */
    public abstract boolean hasNext();


    /**
     * Returns true if this list iterator has more elements when traversing the
     * list in the reverse direction.
     *
     * @return true if the list iterator has more elements when traversing the
     *         list in the reverse direction
     */
    public abstract boolean hasPrevious();


    /**
     * Returns the next element in the list and advances the cursor position.
     *
     * @return the next element in the list
     * @throws NoSuchElementException
     *             if the iteration has no next element
     */
    public abstract E next() throws NoSuchElementException;


    /**
     * Returns the index of the element that would be returned by a subsequent
     * call to next().
     *
     * @return the index of the element that would be returned by a subsequent
     *         call to next, or list size if the list iterator is at the end of
     *         the list
     */
    public abstract int nextIndex();


    /**
     * Returns the previous element in the list and moves the cursor position
     * backwards.
     *
     * @return the previous element in the list
     * @throws NoSuchElementException
     *             if the iteration has no previous element
     */
    public abstract E previous() throws NoSuchElementException;


    /**
     * Returns the index of the element that would be returned by a subsequent
     * call to previous().
     *
     * @return the index of the element that would be returned by a subsequent
     *         call to previous, or -1 if the list iterator is at the beginning
     *         of the list
     */
    public abstract int previousIndex();


    /**
     * Removes from the list the last element that was returned by next() or
     * previous() (optional operation).
     *
     * @throws UnsupportedOperationException
     *             if the remove operation is not supported by this list
     *             iterator
     * @throws IllegalStateException
     *             if neither next nor previous have been called, or remove or
     *             add have been called after the last call to next or previous
     */
    public abstract void remove()
        throws UnsupportedOperationException,
        IllegalStateException;


    /**
     * Replaces the last element returned by next() or previous() with the
     * specified element (optional operation).
     *
     * @param e
     *            the element with which to replace the last element returned by
     *            next or previous
     * @throws UnsupportedOperationException
     *             if the set operation is not supported by this list iterator
     * @throws ClassCastException
     *             if the class of the specified element prevents it from being
     *             added to this list
     * @throws IllegalArgumentException
     *             if some aspect of the specified element prevents it from
     *             being added to this list
     * @throws IllegalStateException
     *             if neither next nor previous have been called, or remove or
     *             add have been called after the last call to next or previous
     */
    public abstract void set(E e)
        throws UnsupportedOperationException,
        ClassCastException,
        IllegalArgumentException,
        IllegalStateException;
}
