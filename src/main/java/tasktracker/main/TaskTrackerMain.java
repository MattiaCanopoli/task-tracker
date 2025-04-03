package tasktracker.main;

import java.io.IOException;
import java.util.Scanner;

import tasktracker.model.Color;
import tasktracker.model.Task;
import tasktracker.model.TaskList;

public class TaskTrackerMain {
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
					08. mark todo - change status to "to-do"
					09. mark in-progress - change status to "in-progress"
					10. mark done - change status to "done"
					11. help - show help file
					11. close - exit application
			""";

	public static void main(String[] args) throws IOException {
		String input = "";
		String[] inputArr;
		boolean exit = false;
		Scanner scanner = new Scanner(System.in);

		TaskList<Task> list = new TaskList<>(PATH);

		System.out.println(Color.RED_BOLD.toString() + "tasklist:\t"
				+ Color.RESET.toString()
				+ "Type \"commands\" to show the commands list");

		while (!exit) {

			System.out.print(Color.RED_BOLD.toString() + "tasklist:\t"
					+ Color.RESET.toString());
			input = scanner.nextLine().toLowerCase();
			String command = input.trim().split(" ")[0];

			switch (command) {
				case "commands" -> System.out.println(COMMANDS);
				case "list" -> {
					inputArr = input.trim().split(" ");
					if (inputArr.length == 1) {
						System.out.println(list.toString());
						// TODO: sostituire inputArr.contains con
						// Task.validateStatus
					} else if ((inputArr.length > 1)
							&& (inputArr[1].contains("to-do")
									|| inputArr[1].contains("in-progress")
									|| inputArr[1].contains("done"))) {
						System.out.println(list.toString(inputArr[1]));

					} else {
						System.out.println("\"" + inputArr[1]
								+ "\" is not a valid argument");
						System.out.println("try: " + Color.CYAN_BOLD.toString()
								+ "list to-do \tlist in-progress\tlist done"
								+ Color.RESET.toString());
					}
				}
				case "add" -> {
					inputArr = input.trim().split("[\"]");

					if (inputArr.length == 1 && inputArr[0].equals("add")) {
						list.addTask(new Task(), PATH);
					} else if (inputArr.length == 1
							&& !inputArr[0].equals("add")) {
						System.out.println(Color.RED.toString()
								+ "\tinvalid command" + Color.RESET.toString());
					} else if (inputArr.length == 2) {
						list.addTask(new Task(inputArr[1]), PATH);
					} else if (inputArr.length > 2) {
						System.out.println(Color.RED.toString()
								+ "\tToo many arguments for \"add\""
								+ Color.RESET.toString());
					}

				}
				// placeholders
				case "delete" -> {
					inputArr = input.trim().split(" ");

					if (inputArr.length < 2) {
						System.out.println("A valid ID must be provided");
					} else {
						try {
							Integer id = Integer.valueOf(inputArr[1]);
							list.deleteTask(id, PATH);

						} catch (NumberFormatException e) {
							System.out.println(Color.RED.toString()
									+ "\tThe provided ID is not a number"
									+ Color.RESET.toString());
						}
					}

				}
				case "mark" -> {

					int id;
					String status;
					try {
						String arguments = input.substring(input.indexOf(' '))
								.trim();

						id = Integer.valueOf(arguments.split(" ")[0].trim());
						status = arguments.substring(arguments.indexOf(" "))
								.trim();

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
								+ "A valid ID must be provided"
								+ Color.RESET.toString());

					} catch (StringIndexOutOfBoundsException e) {
						System.out.println("\t" + Color.RED.toString()
								+ "A valid ID and a valid Status must be provided"
								+ Color.RESET.toString());
					}
				}
				case "update" -> System.out.println("update");
				case "help" -> System.out.println("help");
				case "close" -> exit = true;
				default -> System.out.println("\t" + Color.RED.toString()
						+ "invalid command" + Color.RESET.toString());
			}
		} // while end
		scanner.close();
	}

}
