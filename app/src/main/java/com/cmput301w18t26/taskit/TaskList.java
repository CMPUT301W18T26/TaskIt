package com.cmput301w18t26.taskit;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by kevingordon on 2018-02-26.
 */

public class TaskList {
    private ArrayList<Task> tasks = new ArrayList<Task>();
    public void addTask(Task task) {
        tasks.add(task);
    }

    public boolean hasTask(Task task) {
        for (int i=0; i<tasks.size(); i++) {
            if (getTask(i).getUUID().equals(task.getUUID())) {
                return true;
            }
        }
        return false;
    }

    public Task getTask(int index) {
        return tasks.get(index);
    }

    public void deleteTask(Task task) {
        tasks.remove(task);
    }

    public int getTaskCount() {
        return tasks.size();
    }

    public void addAll(Collection<Task> l) {
        tasks.addAll(l);
    }
}
