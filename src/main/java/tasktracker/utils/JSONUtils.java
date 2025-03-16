package tasktracker.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.json.JSONArray;

import tasktracker.model.Color;

public class JSONUtils {

	public static JSONArray getArrayFromFile(String path) {
		JSONArray jArray = new JSONArray();

		File jsonFile = JSONUtils.createNewJSON(path, jArray);

		String content = JSONUtils.getFileContent(jsonFile);

		jArray = new JSONArray(content);

		return jArray;
	}

	private static File createNewJSON(String path, JSONArray jArray) {
		File file = new File(path);
		try {
			if (!file.exists()) {
				file.createNewFile();
				writeJSONtoFile(file, jArray);
				System.out.println(Color.CYAN_BOLD.toString()
						+ "new task-tracker file created in: "
						+ Color.RESET.toString() + "\"" + path + "\"");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return file;

	}

	private static String getFileContent(File file) {
		String fileContent = "";
		try {
			fileContent += Files.readString(Path.of(file.getAbsolutePath()));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return fileContent;
	}

	private static void writeJSONtoFile(File file, JSONArray jArray) {
		FileWriter fw;

		try {
			fw = new FileWriter(file);
			fw.append(jArray.toString());
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
