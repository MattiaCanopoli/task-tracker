package tasktracker.model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import tasktracker.utils.JSONUtils;

public class TaskList<T extends Task> extends ArrayList<Task> {

	public TaskList(String filePath) {

		JSONArray jArray = JSONUtils.getArrayFromFile(filePath);

		for (int i = 0; i < jArray.length(); i++) {

			JSONObject jOby = jArray.getJSONObject(i);
			Task task = new Task(jOby);
			this.add(task);

		}
	}

	public Task getTaskByID(int ID) {
		for (Task t : this) {
			if (t.getId() == ID) {
				return t;
			}
		}

		return null;
	}

	public void deleteTask(int ID) {
		Task taskToDelete = getTaskByID(ID);
		if (!(taskToDelete == null)) {

			this.remove(taskToDelete);
		} else {
			System.out.println("Wrong id");
		}
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

}
