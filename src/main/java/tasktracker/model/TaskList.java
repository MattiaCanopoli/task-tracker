package tasktracker.model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import tasktracker.utils.JSONUtils;

@SuppressWarnings("serial")
public class TaskList<T extends Task> extends ArrayList<Task> {

	/**
	 * constructs a new TaskList<> starting from a JSON file. file path is
	 * passed as string
	 * 
	 * @param path
	 */
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

	/**
	 * return the task having the provided ID. if no task is found, return null
	 * 
	 * @param ID
	 *            int
	 * @return Task if is found, otherwise return null
	 */
	private Task getTaskByID(int ID) {
		for (Task t : this) {
			if (t.getId() == ID) {
				return t;
			}
		}

		return null;
	}

	/**
	 * replace description of an existing task with a new one passed as String
	 * and update the updatedAt field. task is get by the provided id. updates
	 * JSON file
	 * 
	 * @param ID
	 * @param newDescription
	 * @param path
	 */
	public void updateDescription(int ID, String newDescription, String path) {
		Task taskToUpdate = getTaskByID(ID);
		if (!(taskToUpdate == null)) {
			taskToUpdate.setDescription(newDescription);
			JSONUtils.updateJSON(this, path);
		} else {
			System.out.println("Task with id: " + ID + " not found");
		}
	}
	/**
	 * updates status of an existing task with a new one, passed as String and
	 * update the updatedAt field. task is get by the provided id. updates JSON
	 * file
	 * 
	 * @param ID
	 * @param newDescription
	 * @param path
	 */
	public void markStatus(int ID, String status, String path) {
		Task taskToUpdate = getTaskByID(ID);
		if (!(taskToUpdate == null)) {
			taskToUpdate.setStatus(status);
			JSONUtils.updateJSON(this, path);
		} else {
			System.out.println("Task with id: " + ID + " not found");
		}
	}

	/**
	 * delete the task having the provided ID from TaskList and updates JSON
	 * file. if there is no task with such ID, prints an error message
	 * 
	 * @param ID
	 * @param path
	 */
	public void deleteTask(int ID, String path) {
		Task taskToDelete = getTaskByID(ID);
		if (!(taskToDelete == null)) {
			this.remove(taskToDelete);
			JSONUtils.updateJSON(this, path);
		} else {
			System.out.println("Task with id: " + ID + " not found");
		}
	}

	/**
	 * adds a new task to TaskList and updates JSONFile. File path is passed as
	 * String.
	 * 
	 * @param task
	 * @param path
	 */
	public void addTask(Task task, String path) {
		this.add(task);
		JSONUtils.updateJSON(this, path);
	}

	/**
	 * returns task in TaskList as String
	 * 
	 * @return String
	 */
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

	private TaskList() {
	}

	/**
	 * returns as String all task having the provided status, passed as String,
	 * 
	 * @param status
	 * @return String
	 */
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
