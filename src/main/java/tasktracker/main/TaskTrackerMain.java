package tasktracker.main;

import tasktracker.model.Color;
import tasktracker.model.Task;
import tasktracker.model.TaskList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

public class TaskTrackerMain {
    //defines path for tasklist.json. the path will be the same of the runnable jar file
    private static final String PATH = System.getProperty("user.dir")
            + "/tasklist.json";
    private static final String COMMANDS = """
            		01. commands - show this list
            		02. list - list all saved tasks
            		03. list to-do - show to-do tasks
            		04. list in-progress - show in-progress tasks
            		05. list done - show completed tasks
            		06. add - add a new task
            		07. delete - delete an existing task
            		08. mark - change status
            		09. update - change description
            		10. help - show help file
            		11. close - exit application
            """;

    public static void main(String[] args){
        String input = "";
        String[] inputArr;
        boolean exit = false;
        Scanner scanner = new Scanner(System.in);
        //creates a new TaskList obj retrieving data from the json file. if json does not exist will be created
        TaskList<Task> list = new TaskList<>(PATH);

        System.out.println(Color.RED_BOLD.toString() + "tasklist:\t"
                + Color.RESET.toString()
                + "Type \"commands\" to show the commands list");

        while (!exit) {

            System.out.print(Color.RED_BOLD.toString() + "tasklist:\t"
                    + Color.RESET.toString());
            input = scanner.nextLine().toLowerCase();
            //splits user input into a String[]. only the element at [0] (the command) is taken
            String command = input.trim().split(" ")[0];

            switch (command) {
                case "commands" -> System.out.println(COMMANDS);
                //prints tasks
                case "list" -> {
                    //user input is split into a String[] and its length is evaluated to verify if there are arguments.
                    //if no argument is found, the whole list is printed, else the argument will be validated.
                    //if the argument is a valid status, a list of tasks with matching status is printed, else an error will be printed an error message.
                    inputArr = input.trim().split(" ");
                    if (inputArr.length == 1) {
                        System.out.println(list.toString());
                    } else if ((inputArr.length > 1)
                            && (Task.isValidStatus(inputArr[1]))) {
                        System.out.println(list.toString(inputArr[1]));

                    } else {
                        System.out.println("\"" + inputArr[1]
                                + "\" is not a valid argument");
                        System.out.println("try: " + Color.CYAN_BOLD.toString()
                                + "list to-do \tlist in-progress\tlist done"
                                + Color.RESET.toString());
                    }
                }
                //adds a new task both to TaskList list and json file
                case "add" -> {
                    //user input is split then evaluated
                    inputArr = input.trim().split("[\"]");
                    //case 1: user input is just the command:
                    // a new task is added to TaskList list with default description.
                    //json file is updated with the new task
                    if (inputArr.length == 1 && inputArr[0].equals("add")) {
                        list.addTask(new Task(), PATH);
                        //case 2: user input is "add" command followed by a single or multiple words not within double quotes
                        // an error message is printed
                    } else if ((inputArr.length == 1
                            && !inputArr[0].equals("add")) || (inputArr.length > 2)) {
                        System.out.println(Color.RED.toString()
                                + "\tinvalid argument. description must be in double quotes" + Color.RESET.toString());
                        //case 3: user input is "add" command followed by a valid description (single or multiple word within double quotes)
                        //a new task is added to TaskList list with the provided description
                        //json file is updated with the new task
                    } else if (inputArr.length == 2) {
                        list.addTask(new Task(inputArr[1]), PATH);
                    }

                }
                //delete a given task both from TaskList list and json file
                case "delete" -> {
                    //user input is split
                    inputArr = input.trim().split(" ");
                    //first, length of the array is evaluated. if no argument is provided, an error message is printed
                    if (inputArr.length < 2) {
                        System.out.println("A valid ID must be provided");
                    } else {
                        //the provided argument is converted to Integer. if casting fails, NumberFormatException is thrown and an error message is printed.
                        //else the task matching the provided ID is deleted form TaskList list and json file is updated
                        try {
                            int id = Integer.parseInt(inputArr[1]);
                            list.deleteTask(id, PATH);

                        } catch (NumberFormatException e) {
                            System.out.println(Color.RED.toString()
                                    + "\tThe provided ID is not a number"
                                    + Color.RESET.toString());
                        }
                    }

                }
                //updates status of a given task
                case "mark" -> {
                    try {
                        //user input is split into substring. this substring must contain a valid integer for ID and a valid status
                        String arguments = input.substring(input.indexOf(' '))
                                .trim();
                        //first argument is converted from String to int. if casting fails, NumberFormatException is thrown and an error message is printed.
                        int id = Integer.parseInt(arguments.split(" ")[0].trim());
                        String status = arguments.substring(arguments.indexOf(" "))
                                .trim();
                        //second argument is evaluated. if doesn't match a valid status, error message is printed.
                        //else, status of the task with the provided id is updated
                        if (Task.isValidStatus(status)) {
                            list.markStatus(id, status, PATH);
                        } else {
                            System.out.println("\t" + Color.RED.toString()
                                    + "A valid status must be provided"
                                    + Color.RESET.toString());
                            System.out.println("\t" + Color.RED.toString()
                                    + "Valid status are: "
                                    + Color.CYAN_BOLD.toString()
                                    + Task.getReadabelStatusList()
                                    + Color.RESET.toString());
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("\t" + Color.RED.toString()
                                + "A valid ID must be provided as first argument. ID must be a number"
                                + Color.RESET.toString());
                        //this exception is thrown if not enough arguments (ID and status) are provided
                    } catch (StringIndexOutOfBoundsException e) {
                        System.out.println("\t" + Color.RED.toString()
                                + "A valid ID and a valid Status must be provided"
                                + Color.RESET.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                //change description of a given task to a new one
                case "update" -> {
                    try {
                        //user input is split into substring. this substring must contain a valid integer for ID and a valid description
                        String arguments = input.substring(input.indexOf(' '))
                                .trim();
                        //first argument is converted from String to int. if casting fails, NumberFormatException is thrown and an error message is printed.
                        int id = Integer.parseInt(arguments.split(" ")[0].trim());
                        //second argument is taken and all double quotes are removed.
                        // if there is no second argument, or it's not within double quotes, StringIndexOutOfBoundsException is thrown and an error message is printed
                        String description = arguments
                                .substring(arguments.indexOf("\"")).trim()
                                .replaceAll("\"", "");

                        list.updateDescription(id, description, PATH);
                        //NumberFormatException is thrown if casting user input to int fails (user input is not an int or an int is not the first argument)
                    } catch (NumberFormatException e) {
                        System.out.println("\t" + Color.RED.toString()
                                + "A valid ID must be provided as first argument. ID must be a number"
                                + Color.RESET.toString());
                        //StringIndexOutOfBoundsException is thrown if description is not the second argument, or it's not within double quotes
                    } catch (StringIndexOutOfBoundsException e) {
                        System.out.println("\t" + Color.RED.toString()
                                + "A valid ID and a valid Description must be provided"
                                + Color.RESET.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                //prints the help content. help file is read from resources
                case "help" -> {
                    try (InputStream is = TaskTrackerMain.class
                            .getResourceAsStream("/help.txt")) {

                        if (is == null) {
                            System.out.println("Help file not found");
                        } else {
                            BufferedReader reader = new BufferedReader(
                                    new InputStreamReader(is));
                            String line;
                            while ((line = reader.readLine()) != null) {
                                System.out.println(line);
                            }
                        }
                    } catch (IOException e) {
                        System.out.println(
                                "Error reading help file: " + e.getMessage());
                    }
                }
                case "close", "exit" -> exit = true;
                //if user input is unknown, falls into default case. an error message is printed
                default -> System.out.println("\t" + Color.RED.toString()
                        + "invalid command" + Color.RESET.toString());
            }
        } // while end
        scanner.close();
    }

}
