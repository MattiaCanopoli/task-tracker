package tasktracker.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import tasktracker.utils.JSONUtils;

public class TaskList {

	private List<Task> list;
	private final String RED_BOLD = "\033[1;31m";

	public TaskList() {
		this.list = new ArrayList<>();
	}

	public TaskList(File json) {
		JSONArray jArray = JSONUtils.getArrayFromFile(json);
		if (jArray.length() > 0) {
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject jOby = jArray.getJSONObject(i);
				Task task = new Task(jOby);
				this.list = new ArrayList<Task>();
				this.list.add(task);

			}
		}
	}

	public void addTask(Task task) {
		this.list.add(task);
	}

	public Task getTaskByID(int ID) {
		for (Task t : this.list) {
			if (t.getId() == ID) {
				return t;
			}
		}

		return null;
	}

	public String toString() {
		String taskList = "";

		if (this.list.isEmpty()) {
			taskList = RED_BOLD + "No task found";
			return taskList;
		}
		for (Task t : this.list) {
			taskList += t.toString() + "\n";
		}

		return taskList;
	}

}
