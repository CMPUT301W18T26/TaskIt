/*
 * Copyright 2018, Team 26 CMPUT 301. University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under the terms and coditions fo the Code of Student Behaviour at the University of Alberta.
 */

package com.cmput301w18t26.taskit.ModelTests;

import android.location.Location;
import android.test.ActivityInstrumentationTestCase2;

import com.cmput301w18t26.taskit.BidList;
import com.cmput301w18t26.taskit.Task;
import com.cmput301w18t26.taskit.TaskActivity;

import java.util.Date;

/**
 * Created by kevingordon on 2018-02-26.
 */

public class TaskTest extends ActivityInstrumentationTestCase2 {
    public TaskTest() {
        super(TaskActivity.class);
    }

    // test setting and getting Tasks timestamp
    public void testSetGetDate() {
        Task task = new Task();
        Date date = new Date();
        task.setDate(date);
        assertEquals(date, task.getDate());
    }

    // test setting and getting the User associated with Task object
    public void testSetGetUser() {
        Task task = new Task();
        String user = "AliceBob";
        task.setUser(user);
        assertEquals(user, task.getUser());
    }

    // test setting and getting the description field (string) in a Task object
    public void testSetGetDescription() {
        Task task = new Task();
        String desc = "A very lovely viewtask...";
        task.setDescription(desc);
        assertEquals(desc, task.getDescription());
    }

    // test setting and getting Task location object
    public void testSetGetLocation() {
        Location loc = new Location("");
        Task task = new Task();
        task.setLocation(loc);
        assertEquals(loc, task.getLocation());
    }

    // test method to return boolean depending on whether location field in task has been assigned
    public void testHasLocation() {
        Location loc = new Location("");
        Task task = new Task();
        assertFalse(task.hasLocation());
        task.setLocation(loc);
        assertTrue(task.hasLocation());

    }

    // test the setting and getting of a Task status (string)
    public void testSetGetStatus() {
        Task task = new Task();
        String status = "Requested";
        task.setStatus(status);
        assertEquals(status, task.getStatus());
    }

    // test the setting and getting of a Task title (string)
    public void testSetGetTitle() {
        Task task = new Task();
        String title = "Super great viewtask right here!";
        task.setTitle(title);
        assertEquals(title, task.getTitle());
    }

}