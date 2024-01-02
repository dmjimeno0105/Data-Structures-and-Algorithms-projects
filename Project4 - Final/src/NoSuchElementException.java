/**
 * Thrown by the next method of a ListIterator to indicate that there are no
 * more elements in the list.
 *
 * @author Dominic Jimeno (dmjimeno0105)
 * @version 1.0
 */
public class NoSuchElementException extends RuntimeException {

    /**
     * Constructs a NoSuchElementException with null as its error message
     * string.
     */
    public NoSuchElementException() {
        super();
    }


    /**
     * Constructs a NoSuchElementException, saving a reference to the error
     * message string s for later retrieval by the getMessage method.
     *
     * @param s
     *            the detail message.
     */
    public NoSuchElementException(String s) {
        super(s);
    }
}
