package com.cmput301w18t26.taskit;

import java.util.ArrayList;

/**
 * Created by kevingordon on 2018-02-26.
 */

public class TaskList {
    private ArrayList<Task> tasks = new ArrayList<Task>();
    public void addTask(Task task) {
    }

    public boolean hasTask(Task task) {
        return true;
    }

    public Task getTask(int index) {
        return tasks.get(index);
    }

    public void deleteTask(Task task) {
        tasks.remove(task);
    }

    public int getTaskCount() {
        return 0;
    }
}
