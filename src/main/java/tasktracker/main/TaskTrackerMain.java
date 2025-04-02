package tasktracker.main;

import java.io.IOException;
import java.util.Scanner;

import tasktracker.model.Color;
import tasktracker.model.Task;
import tasktracker.model.TaskList;

public class TaskTrackerMain {

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
		String path = System.getProperty("user.dir") + "/tasklist.json";

		System.out.println(path);
		TaskList<Task> list = new TaskList<>(path);

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
				// placeholders
				case "add" -> System.out.println("add");
				case "delete" -> System.out.println("delete");
				case "mark" -> System.out.println("mark");
				case "update" -> System.out.println("update");
				case "help" -> System.out.println("help");
				case "close" -> exit = true;
				default -> System.out.println("invalid command");
			}
		} // while end
		scanner.close();
	}

}
