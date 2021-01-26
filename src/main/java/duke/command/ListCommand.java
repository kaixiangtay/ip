package duke.command;

import duke.Storage;
import duke.task.TaskList;
import duke.task.Task;
import duke.Ui;

/**
 * ListCommand handles the retrieval of all the tasks in the TaskList as requested by the user
 */
public class ListCommand extends Command {

    /**
     * ListCommand Constructor
     *
     * @param commandType Task name
     */
    public ListCommand(String commandType) {
        super.commandType = commandType;
        super.commandDetails = "";
        super.dateTime = "";
        super.outputMessage = "";
        // index is -1 because it is only used in done and delete tasks
        super.index = -1;
    }

    private void retrieveList(TaskList taskList) {
        StringBuilder currText = new StringBuilder(" Here are the tasks in your list:");

        for (int num = 1; num <= taskList.size(); num++) {
            Task currentTask = taskList.get(num - 1);
            currText.append("\n\t ").append(num).append(".").append(currentTask.toString());
        }
        this.outputMessage = currText.toString();
    }

    /**
     * Retrieve all the tasks from the TaskList given a keyword input from the user and
     * Output the whole TaskList as message to the command line
     *
     * @param tasks TaskList
     * @param ui Instance of Ui
     * @param storage Instance of Storage
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        retrieveList(tasks);
        ui.display(outputMessage);
    }

    /**
     * Determines if whether the Duke Bot should continue processing the user input
     *
     * @return True
     */
    @Override
    public boolean continueInput() {
        return true;
    }
}
