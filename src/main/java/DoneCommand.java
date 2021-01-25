public class DoneCommand extends Command {

    DoneCommand(String commandType, int index) {
        super.commandType = commandType;
        super.commandDetails = "";
        super.dateTime = "";
        super.outputMessage = "";
        super.index = index;
    }

    public void markDoneTask(TaskList taskList) {
        Task doneTask = taskList.get(this.index - 1);
        doneTask.markAsDone();
        this.outputMessage = " Nice! I've marked this task as done:\n" + "\t  " + doneTask.toString();
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        if (index <= 0 || index > taskList.size()) {
            throw new DukeException(ExceptionType.INVALID_INTEGER, "");
        }
        markDoneTask(taskList);
        storage.saveData(taskList);
        ui.display(outputMessage);
    }

    @Override
    public boolean continueInput() {
        return true;
    }
}
