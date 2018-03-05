package com.cmput301w18t26.taskit;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by kevingordon on 2018-02-26.
 */

public class TaskList {
    private ArrayList<Task> tasks = new ArrayList<Task>();
    public void addTask(Task task) {
        tasks.add(task);
    }

    public boolean hasTask(Task task) {
        Log.d("TaskList", "Entering");
        Log.d("TaskList", String.valueOf(tasks.size()));
        for (int i=0; i<tasks.size(); i++) {
            Log.d("TaskList", String.valueOf(getTask(i).getID()) +" : "+ String.valueOf(task.getID()));
            if (getTask(i).getID()==(task.getID())) {
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
}
