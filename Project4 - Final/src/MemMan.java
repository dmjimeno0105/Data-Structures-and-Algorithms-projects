/*
 * On my honor:
 *
 * - I have not used source code obtained from another student,
 * or any other unauthorized source, either modified or
 * unmodified.
 *
 * - All source code and documentation used in my program is
 * either my original work, or was derived by me from the
 * source code published in the textbook for this course.
 *
 * - I have not discussed coding details about this project with
 * anyone other than the instructor, ACM/UPE tutors, programming
 * partner (if allowed in this class), or the TAs assigned to
 * this course. I understand that I may discuss the concepts
 * of this program with other students, and that another student
 * may help me debug my program so long as neither of us writes
 * anything during the discussion or modifies any computer file
 * during the discussion. I have violated neither the spirit nor
 * letter of this restriction.
 */

/**
 * Runs project.
 *
 * @author Dominic Jimeno (dmjimeno0105)
 * @version 3.0
 */
public class MemMan {

    /**
     * Main. Invoke from the terminal as:
     * java MemMan {initial-hash-size} {initial-block-size} {command-file}
     *
     * @param args
     *            the arguments passed into the function MemMan
     */
    public static void main(String[] args) {
        Parser project4 = new Parser(args[0], args[1], args[2]);
        project4.run();
    }
}
