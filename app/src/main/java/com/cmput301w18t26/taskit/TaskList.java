package com.cmput301w18t26.taskit;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by kevingordon on 2018-02-26.
 */

public class TaskList {
    private ArrayList<Task> tasks = new ArrayList<Task>();

    public ArrayList<Task> getTasks() {return tasks;}

    public void addTask(Task task) {
        tasks.add(task);
    }

    public boolean hasTask(Task task) {
        if (getIndex(task) > -1) {
            return true;
        } else {
            return false;
        }
    }

    public int getIndex(Task task) {
        for (int i=0; i<tasks.size(); i++) {
            if (getTask(i).getUUID().equals(task.getUUID())) {
                return i;
            }
        }
        return -1;
    }

    public int getIndex(String uuid) {
        for (int i=0; i<tasks.size(); i++) {
            if (getTask(i).getUUID().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    public Task getTask(int index) {
        return tasks.get(index);
    }

    public Task getTask(Task task) {
        return tasks.get(getIndex(task));
    }

    public Task getTask(String uuid) {
        return tasks.get(getIndex(uuid));
    }

    public void deleteTask(Task task) {
        int index = getIndex(task);

        if (index > -1) {
            tasks.remove(index);
        }
    }

    public int getTaskCount() {
        return tasks.size();
    }

    public void addAll(Collection<Task> l) {
        tasks.addAll(l);
    }
}
