package com.cmput301w18t26.taskit;

import android.test.ActivityInstrumentationTestCase2;

import java.util.Date;

/**
 * Created by kevingordon on 2018-02-26.
 */

public class TaskTest extends ActivityInstrumentationTestCase2 {
    public TaskTest() {
        super(TaskActivity.class);
    }

    public void testSetGetID() {
        Task task = new Task();
        task.setID(0);
        assertEquals(0, task.getID());
    }

    public void testSetGetDate() {
        Task task = new Task();
        Date date = new Date();
        task.setDate(date);
        assertEquals(date, task.getDate());
    }

    public void testSetGetUser() {
        Task task = new Task();
        String user = "AliceBob";
        task.setUser(user);
        assertEquals(user, task.getUser());
    }

    public void testSetGetDescription() {
        Task task = new Task();
        String desc = "A very lovely viewtask...";
        task.setDescription(desc);
        assertEquals(desc, task.getDescription());
    }

    public void testSetGetLocation() {
        Task task = new Task();
        String loc = "Odd type for location...";
        task.setLocation(loc);
        assertEquals(loc, task.getLocation());
    }

    public void testSetGetBids() {
        Task task = new Task();
        BidList bids = new BidList();
        task.setBids(bids);
        assertEquals(bids, task.getBids());
    }

    public void testSetGetStatus() {
        Task task = new Task();
        String status = "Requested";
        task.setStatus(status);
        assertEquals(status, task.getStatus());
    }

    public void testSetGetTitle() {
        Task task = new Task();
        String title = "Super great viewtask right here!";
        task.setTitle(title);
        assertEquals(title, task.getTitle());
    }

}