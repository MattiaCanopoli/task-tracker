package tasktracker.model;

import java.util.ArrayList;
import java.util.List;

public class TaskList {

	private List<Task> list;

	public TaskList() {
		this.list = new ArrayList<>();
	}
	public void addTask(Task task) {
		this.list.add(task);
	}
}
