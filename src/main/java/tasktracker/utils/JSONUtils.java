package tasktracker.utils;

import org.json.JSONArray;
import org.json.JSONObject;
import tasktracker.model.Color;
import tasktracker.model.Task;
import tasktracker.model.TaskList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class JSONUtils {

    /**
     * creates a JSONArray object form a file in the path passed as String
     *
     * @param path
     * @return
     */
    public static JSONArray getArrayFromFile(String path) {
        JSONArray jArray = new JSONArray();

        File jsonFile = JSONUtils.createNewJSON(path, jArray);

        String content = JSONUtils.getFileContent(jsonFile);

        jArray = new JSONArray(content);

        return jArray;
    }

    /**
     * Checks if there is already a JSON file in the path passed as String. if
     * not, creates a new file and write it with the content of the JSONArray.
     * returns the file
     *
     * @param path
     * @param jArray
     * @return
     */
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
            e.printStackTrace();
        }

        return file;

    }

    /**
     * return the content of the provided file as String
     *
     * @param file
     * @return
     */
    private static String getFileContent(File file) {
        String fileContent = "";
        try {
            fileContent += Files.readString(Path.of(file.getAbsolutePath()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileContent;
    }

    /**
     * print the content of a JSONArray to a File, both passed as argument
     *
     * @param file
     * @param jArray
     */
    private static void writeJSONtoFile(File file, JSONArray jArray) {
        FileWriter fw;

        try {
            fw = new FileWriter(file);
            fw.append(jArray.toString());
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * print the content of a TaskList<T extends Task> to a file. file path is
     * passed as String in arguments
     *
     * @param <T>
     * @param taskList
     * @param path
     */
    public static <T extends Task> void updateJSON(TaskList<T> taskList,
                                                   String path) {
        JSONArray jArray = new JSONArray();

        for (Task t : taskList) {
            JSONObject jObj = t.toJSON();
            jArray.put(jObj);
        }

        File file = new File(path);

        writeJSONtoFile(file, jArray);
    }
}
