package seedu.duke.task;

import seedu.duke.exception.EmptyTasklistException;

import java.util.ArrayList;

public class TaskManager {

    private ArrayList<Task> tasklist;

    public TaskManager() {
        this.tasklist = new ArrayList<Task>();
    }

    public String listTasklist() throws EmptyTasklistException {
        assert tasklist.size() >= 0 : "Tasklist cannot be negative";

        if (tasklist.size() == 0) {
            throw new EmptyTasklistException();
        }

        String tasks = "MY TASKLIST:\n";

        for (int i = 0; i < tasklist.size(); i++) {
            tasks += i + 1 + ". " + tasklist.get(i).getTaskEntryDescription() + "\n";
        }

        return tasks;
    }

    public ArrayList<Task> getTasklist() {
        return this.tasklist;
    }

    public void setTasklist(ArrayList<Task> tasklist) {
        this.tasklist = tasklist;
    }



}
