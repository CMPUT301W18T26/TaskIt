package com.cmput301w18t26.taskit;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by kevingordon on 2018-02-26.
 */

/**
 * Represents a list of bids.
 * @author UAlberta-Cmput301-Team26 crew
 * @see Bid
 */
public class TaskList {

    /**
     * List of tasks.
     */
    private ArrayList<Task> tasks = new ArrayList<Task>();

    /**
     * Some services (adapters in particular) need a list type.
     * @return the bare arraylist of tasks.
     */
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

    public boolean hasTask(String UUID) {
        if (getIndex(UUID) > -1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Find tasks in tasklist using their UUIDs
     * @param task the task to check for membership
     * @return index of task if found, -1 o.w.
     */
    public int getIndex(Task task) {
        for (int i=0; i<tasks.size(); i++) {
            if (getTask(i).getUUID().equals(task.getUUID())) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Find tasks in tasklist using their UUIDs
     * @param uuid the uuid of the task to check for membership
     * @return index of task if found, -1 o.w.
     */
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

    public void addAll(TaskList l) {
        tasks.addAll(l.getTasks());
    }

    public void clear() {
        tasks.clear();
    }
}
