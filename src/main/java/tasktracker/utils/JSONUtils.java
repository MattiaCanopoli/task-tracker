package tasktracker.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.json.JSONArray;

public class JSONUtils {

	public static JSONArray getArrayFromFile(File json) {
		JSONArray jsonArray = null;

		if (!json.exists()) {
			System.out.println("File not found");
			return null;
		}

		String jsonContent = null;;
		try {
			jsonContent = Files.readString(Path.of(json.getAbsolutePath()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (!(jsonContent == null)) {
			jsonArray = new JSONArray(jsonContent);
		}

		return jsonArray;
	}

}
