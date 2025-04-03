package tasktracker.model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import tasktracker.utils.JSONUtils;

@SuppressWarnings("serial")
public class TaskList<T extends Task> extends ArrayList<Task> {

	// public TaskList() {
	// // TODO Auto-generated constructor stub
	// }

	public TaskList(String path) {

		JSONArray jArray = JSONUtils.getArrayFromFile(path);

		int lastId = 0;
		for (int i = 0; i < jArray.length(); i++) {

			JSONObject jOby = jArray.getJSONObject(i);
			Task task = new Task(jOby);
			this.add(task);
			if (task.getId() > lastId) {
				lastId = task.getId();
			}

		}

		Task.setLastID(lastId);
	}

	private TaskList() {

	}

	public Task getTaskByID(int ID) {
		for (Task t : this) {
			if (t.getId() == ID) {
				return t;
			}
		}

		return null;
	}

	public void updateDescription(int ID, String newDescription, String path) {
		Task taskToUpdate = getTaskByID(ID);
		if (!(taskToUpdate == null)) {
			taskToUpdate.setDescription(newDescription);
			JSONUtils.updateJSON(this, path);
		} else {
			System.out.println("Task with id: " + ID + " not found");
		}
	}

	public void markStatus(int ID, String status, String path) {
		Task taskToUpdate = getTaskByID(ID);
		if (!(taskToUpdate == null)) {
			taskToUpdate.setStatus(status);
			JSONUtils.updateJSON(this, path);
		} else {
			System.out.println("Wrong ID");
		}
	}

	public void deleteTask(int ID, String path) {
		Task taskToDelete = getTaskByID(ID);
		if (!(taskToDelete == null)) {
			this.remove(taskToDelete);
			JSONUtils.updateJSON(this, path);
		} else {
			System.out.println("Wrong id");
		}
	}

	public void addTask(Task task, String path) {
		this.add(task);
		JSONUtils.updateJSON(this, path);
	}

	public String toString() {
		String taskList = "";

		if (this.isEmpty()) {
			taskList = Color.RED_BOLD.toString() + "No task found";
		}
		for (Task t : this) {
			taskList += t.toString() + "\n";
		}

		return taskList + Color.RESET.toString();
	}

	public String toString(String status) {
		TaskList<Task> list = new TaskList<Task>();
		for (Task t : this) {
			if (t.getStatus().equals(status)) {
				list.add(t);
			}
		}
		return list.toString();
	}
}
