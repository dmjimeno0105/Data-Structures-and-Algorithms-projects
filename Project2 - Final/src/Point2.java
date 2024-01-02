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
 * Point2 class
 *
 * @author Dominic Jimeno (dmjimeno0105)
 *
 * @version 9/27/2022
 *
 */
public class Point2 {

    /**
     * Parses command file information and performs operations to the
     * SimpleDatabase, database, accordingly
     *
     * @param args
     *            The commands passed in from the command line
     */
    public static void main(String[] args) {
        String filename = args[0];
        LinkedList<String> result;
        Point tempPt;
        SimpleDatabase database = new SimpleDatabase();
        Scanner scan = null;
        try {
            scan = new Scanner(new File(filename));
            while (scan.hasNext()) {

                String str = scan.next();
                String ptName = "";
                int xCoord;
                int yCoord;
                int width;
                int height;
                switch (str) {

                    case "insert":
                        ptName = scan.next();
                        xCoord = scan.nextInt();
                        yCoord = scan.nextInt();
                        Point temp = new Point(ptName, xCoord, yCoord);

                        if (database.ifValidPoint(xCoord, yCoord)) {
                            System.out.println("Point Inserted: (" + temp
                                .toString() + ")");
                            database.insert(temp);
                        }
                        else
                            System.out.println("Point REJECTED: (" + temp
                                .toString() + ")");

                        break;

                    case "remove":
                        ptName = scan.next();
                        if (!isNumber(ptName)) {
                            tempPt = database.removeByName(ptName);
                            if (tempPt == null)
                                System.out.println("Point Not Removed: "
                                    + ptName);
                            else {
                                System.out.println("Point (" + tempPt.toString()
                                    + ") Removed");
                            }

                        }
                        else {
                            xCoord = Integer.parseInt(ptName);
                            yCoord = scan.nextInt();
                            if (xCoord < 0 || yCoord < 0) {
                                System.out.println("Point rejected: (" + xCoord
                                    + ", " + yCoord + ")");
                            }
                            else {
                                tempPt = database.removeByCoords(xCoord,
                                    yCoord);
                                if (tempPt == null)
                                    System.out.println("Point Not Found: ("
                                        + xCoord + ", " + yCoord + ")");
                                else {
                                    System.out.println("Point (" + tempPt
                                        .toString() + ") Removed");
                                }
                            }

                        }
                        break;

                    case "regionsearch":
                        xCoord = scan.nextInt();
                        yCoord = scan.nextInt();
                        width = scan.nextInt();
                        height = scan.nextInt();
                        if (width > 0 && height > 0) {
                            result = database.regionSearch(xCoord, yCoord,
                                width, height);
                            for (int i = 0; i < result.size(); i++)
                                System.out.println(result.get(i));
                        }
                        else {
                            System.out.println("Rectangle Rejected: (" + xCoord
                                + ", " + yCoord + ", " + width + ", " + height
                                + ")");
                        }
                        break;
                    case "duplicates":
                        System.out.println("Duplicate Points:");
                        result = database.duplicates();
                        for (int i = 0; i < result.size(); i++) {
                            System.out.println("(" + result.get(i) + ")");
                        }
                        break;

                    case "search":
                        ptName = scan.next();
                        result = database.search(ptName);
                        for (int i = 0; i < result.size(); i++) {
                            System.out.println(result.get(i));
                        }
                        break;

                    case "dump":
                        result = database.dump();
                        for (int i = 0; i < result.size(); i++)
                            System.out.println(result.get(i));
                        break;

                    default:
                        break;
                }
            }
        }
        catch (FileNotFoundException e) {
            System.out.println(e);
        }

    }


    /**
     * Checks if a given string is a valid integer
     *
     * @param temp
     *            a description
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
