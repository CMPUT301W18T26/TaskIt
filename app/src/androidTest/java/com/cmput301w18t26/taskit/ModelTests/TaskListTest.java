/*
 * Copyright 2018, Team 26 CMPUT 301. University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under the terms and coditions fo the Code of Student Behaviour at the University of Alberta.
 */

package com.cmput301w18t26.taskit.ModelTests;

import android.test.ActivityInstrumentationTestCase2;

import com.cmput301w18t26.taskit.ListActivity;
import com.cmput301w18t26.taskit.MockTask;
import com.cmput301w18t26.taskit.Task;
import com.cmput301w18t26.taskit.TaskList;

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

    // test adding a task to TaskList
    public void testAddTask() {
        TaskList tasks = new TaskList();
        Task task = new MockTask();
        tasks.addTask(task);

        assertTrue(tasks.hasTask(task));
    }

    // test method for return boolean -- true if TaskList is non-empty
    public void testHasTask() {
        TaskList tasks = new TaskList();
        Task task = new MockTask();
        assertFalse(tasks.hasTask(task));
        tasks.addTask(task);
        assertTrue(tasks.hasTask(task));
    }

    // test getTask method, returns Task object by index or UUID
    public void testGetTask() {
        TaskList tasks = new TaskList();
        Task task = new MockTask();
        tasks.addTask(task);
        Task returnedTask = tasks.getTask(0);
        assertEquals(task.getUUID(), returnedTask.getUUID());
    }

    // test deleting a task object from TaskList
    public void testDelete() {
        TaskList tasks = new TaskList();
        Task task = new MockTask();
        tasks.addTask(task);
        tasks.deleteTask(task);
        assertFalse(tasks.hasTask(task));
    }

    // test getting a count of the number of Task objects in TaskList
    public void testGetTaskCount() {
        TaskList tasks = new TaskList();
        assertEquals(0, tasks.getTaskCount());
        Task task1 = new MockTask();
        tasks.addTask(task1);
        assertEquals(1, tasks.getTaskCount());
        Task task2 = new MockTask();
        tasks.addTask(task2);
        assertEquals(2, tasks.getTaskCount());
        Task task3 = new MockTask();
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
