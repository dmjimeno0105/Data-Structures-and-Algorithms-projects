import java.io.File;
import java.util.Scanner;

/**
 * Parses input, runs commands, and prints output. Uses Java's provided Scanner
 * and PrintStream classes in order to parse and print. Scanner reads from a
 * file and PrintStream writes to the console.
 *
 * @author Dominic Jimeno (dmjimeno0105)
 * @version 1.0
 */
public class Parser {
    private String commandFile;
    private MemoryManager manager;

    /**
     * Create a new Parser object.
     *
     * @param arg1
     *            the initial capacity of hashtables
     * @param arg2
     *            the initial capacity of memory pool
     * @param arg3
     *            the name of the command file to be read
     */
    public Parser(String arg1, String arg2, String arg3) {
        manager = new MemoryManager(Integer.parseInt(arg1), Integer.parseInt(
            arg2));
        commandFile = arg3;
    }


    /**
     * Runs project 4.
     */
    public void run() {
        try {
            Scanner file = new Scanner(new File(commandFile));
            Scanner line;

            while (file.hasNextLine()) {
                line = new Scanner(file.nextLine());
                if (line.hasNextLine()) {
                    String command = line.next();
                    String type;
                    String parameter;

                    switch (command) {
                        case "insert":
                            type = line.nextLine().trim();

                            String[] parameters = type.split("<SEP>");
                            System.out.println(manager.insert(parameters[0],
                                parameters[1]));

                            break;
                        case "remove":
                            type = line.next();

                            switch (type) {
                                case "artist":
                                    parameter = line.nextLine().trim();
                                    System.out.println(manager.removeArtist(
                                        parameter));
                                    break;
                                case "song":
                                    parameter = line.nextLine().trim();
                                    System.out.println(manager.removeSong(
                                        parameter));
                                    break;
                            }

                            break;
                        case "print":
                            type = line.next();

                            switch (type) {
                                case "artists":
                                    System.out.println(manager
                                        .artistsToString());
                                    break;
                                case "songs":
                                    System.out.println(manager.songsToString());
                                    break;
                                case "blocks":
                                    System.out.println(manager
                                        .blocksToString());
                                    break;
                            }

                            break;
                    } // End of switch statement
                } // End of if clause
            } // End of while loop
        } // End of try
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
