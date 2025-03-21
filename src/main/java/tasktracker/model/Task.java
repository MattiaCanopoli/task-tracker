package tasktracker.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.json.JSONObject;

public class Task {

	private static int lastID = 1;
	private DateTimeFormatter formatter = DateTimeFormatter
			.ofPattern("dd/MM/yyyy hh:mm:ss");
	private int id;
	private String description;
	private String status;
	private final static String[] statusList = {"todo", "in-progress", "done"};
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	// constructor
	public Task(String description) {
		this.id = lastID + 1;
		this.description = description;
		this.status = statusList[0];
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();

	}

	public Task() {
		this("no description provided");
	}

	public Task(JSONObject jObj) {
		this.id = jObj.getInt("ID");
		this.description = jObj.getString("description");
		this.status = jObj.getString("status");
		this.createdAt = LocalDateTime.parse(jObj.getString("created-at"));
		this.updatedAt = LocalDateTime.parse(jObj.getString("updated-at"));
	}

	// accessors
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		if (Task.isValidStatus(status)) {
			this.status = status;
		} else {
			System.out.println("Invalid status");
		}

	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public String getFormattedUpadatedAt() {
		return this.updatedAt.format(formatter);
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public static int getLastID() {
		return lastID;
	}

	public static void setLastID(int ID) {
		lastID = ID;
	}

	public DateTimeFormatter getFormatter() {
		return formatter;
	}

	public int getId() {
		return id;
	}

	public static String[] getStatusList() {
		return statusList;
	}

	private static boolean isValidStatus(String status) {

		for (String s : Task.getStatusList()) {
			if (s.equals(status)) {
				return true;
			}
		}

		return false;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public String getFormattedCreatedAt() {
		return this.updatedAt.format(formatter);
	}

	// json methods
	public JSONObject toJSON() {
		JSONObject jObj = new JSONObject();
		jObj.put("ID", this.getId());
		jObj.put("description", this.getDescription());
		jObj.put("status", this.getStatus());
		jObj.put("created-at", this.getCreatedAt().toString());
		jObj.put("updated-at", this.getUpdatedAt().toString());

		return jObj;

	}

	@Override
	public String toString() {
		return Color.CYAN_BOLD.toString() + "ID: " + this.id + ", description: "
				+ this.description + ", status: " + this.status
				+ ", created at: " + this.getFormattedCreatedAt()
				+ ", updated at: " + this.getFormattedUpadatedAt()
				+ Color.RESET.toString();
	}

}
