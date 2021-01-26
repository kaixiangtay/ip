package duke.task;

/**
 * Task class created when user inputs todo, event, deadline
 */
public class Task {
    protected final String description;
    protected boolean isDone;

    /**
     * Constructor for Task class
     * @param description details of the task
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Retrieve the status of task
     * @return Cross if task is marked as completed, empty string if incomplete
     */
    public String getStatusIcon() {
        return (this.isDone ? "\u2718" : " ");
    }

    /**
     * Set state of task as done once the task is completed
     */
    public void markAsDone() {
        this.isDone = true;
    }

    public String getDescription() {
        return this.description;
    }

    /**
     * Format the task into custom format before saving into data file
     */
    public String formatTask() {
        return String.format("%s | %s", this.isDone ? "1" : "0", this.description);
    }

    /**
     * Format task status icon and task details as string
     * @return task status icon and task details
     */
    @Override
    public String toString() {
        return String.format("[%s] %s", getStatusIcon(), this.description);
    }
}