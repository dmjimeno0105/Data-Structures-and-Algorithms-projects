/**
 * Doubly-linked list implementation.
 *
 * @author Dominic Jimeno (dmjimeno0105)
 * @version 1.0
 * @param <E>
 *            element
 */
public class LinkedList<E> {
    private Node<E> head;
    private int size;
    private Node<E> tail;

    /**
     * Create a new LinkedList object.
     */
    public LinkedList() {
        initialize();
    }


    /**
     * Get the node at the specified index.
     *
     * @warning beware of returning a node to caller
     * @param index
     *            the specified index
     * @return the node at the specified index
     */
    private Node<E> getNode(int index) {
        if (index < 0 || size <= index) {
            throw new IndexOutOfBoundsException();
        }
        Node<E> current = head.next();
        for (int i = 0; i < index; i++) {
            current = current.next();
        }
        return current;
    }


    /**
     * Initialize an instance of this class.
     */
    private void initialize() {
        head = new Node<E>(null);
        tail = new Node<E>(null);
        head.setNext(tail);
        tail.setPrevious(head);
        size = 0;
    }


    /**
     * Appends the specified element to the end of this list.
     *
     * @param e
     *            element to be appended to this list
     */
    public void add(E e) {
        add(size, e);
    }


    /**
     * Inserts the specified element at the specified position in this list.
     *
     * @param index
     *            index at which the specified element is to be inserted
     * @param element
     *            element to be inserted
     */
    public void add(int index, E element) {
        if (element == null) {
            throw new IllegalArgumentException(
                "Cannot add null objects to list");
        }

        Node<E> nodeAfter;
        if (index == size) {
            nodeAfter = tail;
        }
        else {
            nodeAfter = getNode(index);
        }

        Node<E> newNode = new Node<E>(element);
        newNode.setPrevious(nodeAfter.previous());
        newNode.setNext(nodeAfter);
        nodeAfter.previous().setNext(newNode);
        nodeAfter.setPrevious(newNode);
        size++;
    }


    /**
     * Removes all of the elements from this list.
     */
    public void clear() {
        initialize();
    }


    /**
     * Returns true if this list contains the specified element.
     *
     * @param o
     *            element whose presence in this list is to be tested
     * @return true if this list contains the specified element
     */
    public boolean contains(Object o) {
        return lastIndexOf(o) != -1;
    }


    /**
     * Returns the element at the specified position in this list.
     *
     * @param index
     *            index of the element to return
     * @return the element at the specified position in this list
     */
    public E get(int index) {
        return getNode(index).data();
    }


    /**
     * Returns true if this list contains no elements.
     *
     * @return true if this list contains no elements
     */
    public boolean isEmpty() {
        return size == 0;
    }


    /**
     * Returns the index of the last occurrence of the specified element in this
     * list, or -1 if this list does not contain the element.
     *
     * @param o
     *            element to search for
     * @return the index of the last occurrence of the specified element in this
     *         list, or -1 if this list does not contain the element
     */
    public int lastIndexOf(Object o) {
        Node<E> current = tail.previous();
        for (int i = size - 1; i >= 0; i--) {
            if (current.data().equals(o)) {
                return i;
            }
            current = current.previous();
        }
        return -1;
    }


    /**
     * Returns a list-iterator of the elements in this list (in proper
     * sequence), starting at the specified position in the list.
     *
     * @param index
     *            index of the first element to be returned from the
     *            list-iterator (by a call to next)
     * @return a ListIterator of the elements in this list (in proper sequence),
     *         starting at the specified position in the list
     */
    public ListIterator<E> listIterator(int index) {
        return new Iterator<E>(index);
    }


    /**
     * Removes the element at the specified position in this list.
     *
     * @param index
     *            the index of the element to be removed
     * @return the element previously at the specified position
     */
    public E remove(int index) {
        Node<E> removedNode = getNode(index);
        removedNode.previous().setNext(removedNode.next());
        removedNode.next().setPrevious(removedNode.previous());
        size--;
        return removedNode.data();
    }


    /**
     * Removes the first occurrence of the specified element from this list, if
     * it is present.
     *
     * @param o
     *            element to be removed from this list, if present
     * @return true if this list contained the specified element
     */
    public boolean remove(Object o) {
        Node<E> current = head.next();
        while (!current.equals(tail)) {
            if (current.data().equals(o)) {
                current.previous().setNext(current.next());
                current.next().setPrevious(current.previous());
                size--;
                return true;
            }
            current = current.next();
        }
        return false;
    }


    /**
     * Replaces the element at the specified position in this list with the
     * specified element.
     *
     * @param index
     *            index of the element to replace
     * @param element
     *            element to be stored at the specified position
     * @return the element previously at the specified position
     */
    public E set(int index, E element) {
        Node<E> node = getNode(index);
        E oldData = node.data();
        node.setData(element);
        return oldData;
    }


    /**
     * Returns the number of elements in this list.
     *
     * @return the number of elements in this list
     */
    public int size() {
        return size;
    }


    /**
     * Returns an array containing all of the elements in this list in proper
     * sequence (from first to last element).
     *
     * @return an array containing all of the elements in this list in proper
     *         sequence
     */
    public Object[] toArray() {
        Object[] elements = new Object[size];
        Node<E> current = head.next();

        int i = 0;
        while (current != tail) {
            elements[i] = current.data();
            i++;
            current = current.next();
        }

        return elements;
    }


    /**
     * Returns a string representation of this list.
     *
     * @return a string representation of this list
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        Node<E> current = head.next();
        while (current != tail) {
            builder.append(current.data().toString());
            if (current.next != tail) {
                builder.append(" -> ");
            }
            current = current.next();
        }
        return builder.toString();
    }

    private class Iterator<T> implements ListIterator<E> {
        private Node<E> currentBack;
        private Node<E> currentFront;
        private int index;
        private boolean movedBackward;
        private boolean movedForward;

        /**
         * Create a new ListIterator object, with the cursor starting right
         * before the specified node in the list.
         *
         * @param i
         *            the specified position in the list
         */
        public Iterator(int i) {
            if (isEmpty()) {
                currentFront = tail;
            }
            else {
                currentFront = getNode(i);
            }
            currentBack = currentFront.previous;
            index = i;
            movedBackward = false;
            movedForward = false;
        }


        /**
         * Inserts the specified element into the list. The new element is
         * inserted before the implicit cursor
         *
         * @param e
         *            the specified element
         */
        @Override
        public void add(E e) {
            Node<E> newNode = new Node<E>(e);
            newNode.setPrevious(currentBack);
            newNode.setNext(currentFront);
            currentBack.setNext(newNode);
            currentFront.setPrevious(newNode);
            currentBack = currentFront.previous();
            index++;
            size++;
        }


        /**
         * Checks if there are more elements in front.
         *
         * @return true if there are more elements in front
         */
        @Override
        public boolean hasNext() {
            return currentFront != tail;
        }


        /**
         * Checks if there are more elements behind.
         *
         * @return true if there are more elements behind
         */
        @Override
        public boolean hasPrevious() {
            return currentBack != head;
        }


        /**
         * Gets the next value in the list.
         *
         * @return the next value
         * @throws NoSuchElementException
         *             if there are no nodes left in front
         */
        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Illegal call to next");
            }
            E data = currentFront.data;
            currentFront = currentFront.next;
            currentBack = currentBack.next;
            movedForward = true;
            movedBackward = false;
            index++;
            return data;
        }


        /**
         * Returns the index of the element that would be returned by a
         * subsequent call to next(). (Returns list size if the list iterator is
         * at the end of the list.)
         *
         * @return the index of the element that would be returned by a
         *         subsequent call to next, or list size if the list iterator is
         *         at the end of the list
         */
        @Override
        public int nextIndex() {
            return index;
        }


        /**
         * Gets the previous value in the list.
         *
         * @return the previous value
         * @throws NoSuchElementException
         *             if there are no nodes left behind
         */
        @Override
        public E previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException("Illegal call to previous");
            }
            E data = currentBack.data;
            currentBack = currentBack.previous;
            currentFront = currentFront.previous;
            movedBackward = true;
            movedForward = false;
            index--;
            return data;
        }


        /**
         * Returns the index of the element that would be returned by a
         * subsequent call to previous(). (Returns -1 if the list iterator is at
         * the beginning of the list.)
         *
         * @return the index of the element that would be returned by a
         *         subsequent call to previous, or -1 if the list iterator is at
         *         the beginning of the list
         */
        @Override
        public int previousIndex() {
            return index - 1;
        }


        /**
         * Removes from the list the last element that was returned by next() or
         * previous().
         *
         * @throws IllegalStateException
         *             if the iterator has not been moved yet or if the element
         *             has already been removed
         */
        @Override
        public void remove() {
            if (movedBackward) {
                if (hasNext()) {
                    currentBack.setNext(currentBack.next.next);
                    currentBack.next.setPrevious(currentBack);
                    currentFront = currentBack.next;
                    size--;
                }
                else {
                    throw new NoSuchElementException("No element to remove");
                }
            }
            else if (movedForward) {
                if (hasPrevious()) {
                    currentFront.setPrevious(currentFront.previous.previous);
                    currentFront.previous.setNext(currentFront);
                    currentBack = currentFront.previous;
                    index--;
                    size--;
                }
                else {
                    throw new NoSuchElementException("No element to remove");
                }
            }
            else {
                throw new IllegalStateException(
                    "Next or previous has not been called yet");
            }
        }


        /**
         * Replaces the last element returned by next() or previous() with the
         * specified element.
         *
         * @param e
         *            the specified element
         */
        @Override
        public void set(E e) {
            if (movedBackward) {
                if (hasNext()) {
                    currentFront.setData(e);
                }
                else {
                    throw new NoSuchElementException("No element to set");
                }
            }
            else if (movedForward) {
                if (hasPrevious()) {
                    currentBack.setData(e);
                }
                else {
                    throw new NoSuchElementException("No element to set");
                }
            }
            else {
                throw new IllegalStateException(
                    "Next or previous has not been called yet");
            }
        }
    }


    /**
     * This represents a node in a doubly linked list. This node stores data, a
     * pointer to the node before it in the list, and a pointer to the node
     * after it in the list.
     *
     * @param <T>
     *            This is the type of object that this class will store
     *
     * @author dmjimeno0105
     * @version 1.0
     */
    private static class Node<T> {
        private T data;
        private Node<T> next;
        private Node<T> previous;

        /**
         * Create a new Node object with the given data.
         *
         * @param d
         *            the data to put inside the node
         */
        public Node(T d) {
            data = d;
        }


        /**
         * Gets the data in the node.
         *
         * @return data
         */
        public T data() {
            return data;
        }


        /**
         * Gets the next node.
         *
         * @return next
         */
        public Node<T> next() {
            return next;
        }


        /**
         * Gets the previous node.
         *
         * @return previous
         */
        public Node<T> previous() {
            return previous;
        }


        /**
         * Sets the data within this node.
         *
         * @param d
         *            the data to be set
         */
        public void setData(T d) {
            data = d;
        }


        /**
         * Sets the node after this node.
         *
         * @param n
         *            the node after this one
         */
        public void setNext(Node<T> n) {
            next = n;
        }


        /**
         * Sets the node before this one.
         *
         * @param p
         *            the node before this one
         */
        public void setPrevious(Node<T> p) {
            previous = p;
        }
    }
}
