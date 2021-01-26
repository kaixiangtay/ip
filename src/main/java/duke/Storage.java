package duke;

import duke.exception.*;
import duke.task.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Storage class to manage saving tasks into the list and loading of tasks from the list when requested by the user
 */
public class Storage {
    private final String fileDirectory;
    private final String pathDirectory;

    /**
     * Storage class constructor
     *
     * @param fileDirectory Location where the list of tasks will be stored
     */
    public Storage(String fileDirectory) {
        this.fileDirectory = fileDirectory;
        this.pathDirectory = fileDirectory.replaceFirst("/Duke.txt", "");
    }

    private File fileConfiguration() throws DukeException {
        File dataDirectory = new File(this.pathDirectory);
        File dataFile = new File(this.fileDirectory);

        try {
            if (!(Files.isDirectory(Paths.get(this.pathDirectory)))) {
                // Handles folder does not exist case
                dataDirectory.mkdir();
                dataFile.createNewFile();
            } else if (!dataFile.exists()) {
                // Handles file does not exist
                dataFile.createNewFile();
            }
            return dataFile;
        } catch (IOException ex) {
            throw new DukeException(ExceptionType.INVALID_FILE_CONFIGURATION, "");
        }
    }


    /**
     * Write list of tasks into the data file Duke.txt
     *
     * @param tasks List of tasks to write into data file
     * @throws DukeException If there is error in saving the data into file Duke.txt
     */
    public void saveData(TaskList tasks) throws DukeException {
        try {
            File dataFile = fileConfiguration();
            FileWriter fileWriter = new FileWriter(dataFile, false);

            for (int index = 0; index < tasks.size(); index++) {
                Task currTask = tasks.get(index);
                fileWriter.write(currTask.formatTask() + System.lineSeparator());
            }
            fileWriter.close();
        } catch (IOException ex) {
            throw new DukeException(ExceptionType.SAVING_ERROR, "");
        }
    }

    /**
     * Read list of tasks from data file Duke.txt
     *
     * @return List of tasks from storage data file in ArrayList format
     * @throws DukeException If there is an error in loading the data from file Duke.txt
     */
    public ArrayList<Task> loadData() throws DukeException {
        try {
            ArrayList<Task> tasks = new ArrayList<>();
            File dataFile = fileConfiguration();
            Scanner sc = new Scanner(dataFile);

            while (sc.hasNext()) {
                String[] taskDetails = sc.nextLine().split("[|]");
                String taskType = taskDetails[0];
                Task newTask = null;

                switch (taskType) {
                case "T":
                    newTask = new ToDo(taskDetails[2]);
                    break;
                case "E":
                    newTask = new Event(taskDetails[2], LocalDate.parse(taskDetails[3]));
                    break;
                case "D":
                    newTask = new Deadline(taskDetails[2], LocalDate.parse(taskDetails[3]));
                    break;
                default:
                    break;
                }

                if (taskType.equals("T") || taskType.equals("E") || taskType.equals("D")) {
                    if (taskDetails[1].equals("1")) {
                        newTask.markAsDone();
                    }
                    tasks.add(newTask);
                }
            }
            return tasks;
        } catch (IOException ex) {
            throw new DukeException(ExceptionType.LOADING_ERROR, "");
        }
    }
}
