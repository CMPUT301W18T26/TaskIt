package com.cmput301w18t26.taskit;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by kevingordon on 2018-02-26.
 */

public class TaskListTest extends ActivityInstrumentationTestCase2 {
    public TaskListTest() {
        super(ListActivity.class);
    }

    public void testAddTask() {
        TaskList tasks = new TaskList();
        Task task = new Task();
        tasks.add(task);

        assertTrue(tasks.hasTask(task));
    }

    public void testHasTask() {
        TaskList tasks = new TaskList();
        Task task = new Task();
        assertFalse(tasks.hasTask(task));
        tasks.add(task);
        assertTrue(tasks.hasTask(task));
    }

    public void testGetTask() {
        TaskList tasks = new TaskList();
        Task task = new Task();
        tasks.add(task);
        Task returnedTask = tasks.getTask(0);
        assertEquals(task.getID(), returnedTask.getID());
    }


    public void testDelete() {
        TaskList tasks = new TaskList();
        Task task = new Task();
        tasks.add(task);
        tasks.delete(task);
        assertFalse(tasks.hasTask(task));
    }
}
