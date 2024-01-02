import java.util.LinkedList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

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
 * Rectangle1 main driver class
 *
 * @author Dominic Jimeno (dmjimeno0105)
 *
 * @version 9/5/2022
 *
 */
public class Rectangle1 {

    /**
     * Parses command file information and performs operations to the
     * database accordingly
     *
     * @param args
     *            the commands passed in from the command line
     */
    public static void main(String[] args) {
        String fileName = args[0];
        LinkedList<Rectangle> list;
        Rectangle rectangle;
        SimpleDatabase database = new SimpleDatabase();
        Scanner scan;

        try {
            scan = new Scanner(new File(fileName));
            while (scan.hasNext()) {
                String command = scan.next();
                String name = "";
                int x;
                int y;
                int width;
                int height;

                switch (command) {
                    case "insert":
                        name = scan.next();
                        x = scan.nextInt();
                        y = scan.nextInt();
                        width = scan.nextInt();
                        height = scan.nextInt();
                        rectangle = new Rectangle(name, x, y, width, height);

                        if (rectangle.valid()) {
                            database.insert(rectangle);
                            System.out.println("Rectangle inserted: "
                                + rectangle.toString());
                        }
                        else
                            System.out.println("Rectangle rejected: "
                                + rectangle.toString());
                        break;

                    case "dump":
                        LinkedList<SkipNode<String, Rectangle>> nodes = database
                            .dump();

                        System.out.println("Skiplist dump:");
                        for (int i = 0; i < nodes.size(); i++) {
                            // for printing out header node
                            if (nodes.get(i).value() == null) {
                                System.out.println("Node has depth " + nodes
                                    .get(i).level() + ", Value " + "(" + nodes
                                        .get(i).value() + ")");
                            }
                            // for printing out every node other than the header
                            else {
                                System.out.println("Node has depth " + nodes
                                    .get(i).level() + ", Value " + nodes.get(i)
                                        .value());
                            }
                        }
                        System.out.println("Skiplist size is: " + database
                            .size());
                        break;

                    case "remove":
                        name = scan.next();
                        // remove by name
                        if (!isNumber(name)) {
                            rectangle = database.remove(name);
                            if (rectangle == null)
                                System.out.println("Rectangle not removed: ("
                                    + name + ")");
                            else {
                                System.out.println("Rectangle removed: "
                                    + rectangle.toString());
                            }

                        }
                        // remove by dimensions
                        else {
                            x = Integer.parseInt(name);
                            y = scan.nextInt();
                            width = scan.nextInt();
                            height = scan.nextInt();
                            rectangle = new Rectangle("rectangle", x, y, width,
                                height);

                            if (!rectangle.valid()) {
                                System.out.println("Rectangle rejected: (" + x
                                    + ", " + y + ", " + width + ", " + height
                                    + ")");
                            }
                            else {
                                rectangle = database.remove(x, y, width,
                                    height);
                                if (rectangle == null)
                                    System.out.println(
                                        "Rectangle not removed: (" + x + ", "
                                            + y + ", " + width + ", " + height
                                            + ")");
                                else {
                                    System.out.println("Rectangle removed: "
                                        + rectangle.toString());
                                }
                            }
                        }
                        break;

                    case "regionsearch":
                        x = scan.nextInt();
                        y = scan.nextInt();
                        width = scan.nextInt();
                        height = scan.nextInt();

                        list = database.regionSearch(x, y, width, height);
                        if (list == null) {
                            System.out.println("Rectangle rejected: (" + x
                                + ", " + y + ", " + width + ", " + height
                                + ")");
                        }
                        else {
                            System.out.println(
                                "Rectangles intersecting region (" + x + ", "
                                    + y + ", " + width + ", " + height + "):");
                            while (!list.isEmpty()) {
                                rectangle = list.remove();
                                System.out.println(rectangle.toString());
                            }
                        }
                        break;

                    case "intersections":
                        list = database.intersections();
                        System.out.println("Intersections pairs:");
                        while (!list.isEmpty()) {
                            Rectangle rectangle1 = list.remove();
                            Rectangle rectangle2 = list.remove();
                            System.out.println("(" + rectangle1.toString()
                                .replaceAll("[()]", "") + " | " + rectangle2
                                    .toString().replaceAll("[()]", "") + ")");
                        }
                        break;

                    case "search":
                        name = scan.next();
                        list = database.search(name);

                        if (list.isEmpty()) {
                            System.out.println("Rectangle not found: (" + name
                                + ")");
                        }
                        else {
                            System.out.println("Rectangles found:");
                            for (int i = 0; i < list.size(); i++) {
                                System.out.println(list.get(i).toString());
                            }
                        }
                        break;

                    default:
                        // do nothing
                        break;
                } // end of switch case
            } // end of while loop
        } // end of try statement
        catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }


    /**
     * Checks if a given string is a valid integer
     *
     * @param temp
     *            the String being tested as a number
     *
     * @return true if given string is a number, false otherwise
     */
    public static boolean isNumber(String temp) {
        try {
            Integer.parseInt(temp);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
}
