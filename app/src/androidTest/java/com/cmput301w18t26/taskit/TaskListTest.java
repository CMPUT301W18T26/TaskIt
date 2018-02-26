package com.cmput301w18t26.taskit;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by kevingordon on 2018-02-26.
 */

public class TaskListTest extends ActivityInstrumentationTestCase2 {
    public TaskListTest() {
        super(ListActivity.class);
    }

    public void testTest() {
        assertTrue(true);
    }

    public void testAddTask() {
        TaskList tasks = new TaskList();
        Task task = new Task();
        tasks.addTask(task);

        assertTrue(tasks.hasTask(task));
    }

    public void testHasTask() {
        TaskList tasks = new TaskList();
        Task task = new Task();
        assertFalse(tasks.hasTask(task));
        tasks.addTask(task);
        assertTrue(tasks.hasTask(task));
    }

    public void testGetTask() {
        TaskList tasks = new TaskList();
        Task task = new Task();
        tasks.addTask(task);
        Task returnedTask = tasks.getTask(0);
        assertEquals(task.getID(), returnedTask.getID());
    }


    public void testDelete() {
        TaskList tasks = new TaskList();
        Task task = new Task();
        tasks.addTask(task);
        tasks.deleteTask(task);
        assertFalse(tasks.hasTask(task));
    }

    public void testGetTaskCount() {
        TaskList tasks = new TaskList();
        assertEquals(0, tasks.getTaskCount());
        Task task1 = new Task();
        tasks.addTask(task1);
        assertEquals(1, tasks.getTaskCount());
        Task task2 = new Task();
        tasks.addTask(task2);
        assertEquals(2, tasks.getTaskCount());
        Task task3 = new Task();
        tasks.addTask(task3);
        assertEquals(3, tasks.getTaskCount());
        tasks.deleteTask(task3);
        assertEquals(2, tasks.getTaskCount());
        tasks.deleteTask(task2);
        assertEquals(1, tasks.getTaskCount());
        tasks.deleteTask(task1);
        assertEquals(0, tasks.getTaskCount());
    }
    
}
