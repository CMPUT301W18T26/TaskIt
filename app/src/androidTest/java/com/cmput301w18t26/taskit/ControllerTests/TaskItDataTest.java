/*
 * Copyright 2018, Team 26 CMPUT 301. University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under the terms and coditions fo the Code of Student Behaviour at the University of Alberta.
 */

package com.cmput301w18t26.taskit.ControllerTests;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;

import com.cmput301w18t26.taskit.Bid;
import com.cmput301w18t26.taskit.BidList;
import com.cmput301w18t26.taskit.HomeActivity;
import com.cmput301w18t26.taskit.MockBid;
import com.cmput301w18t26.taskit.MockTask;
import com.cmput301w18t26.taskit.MockUser;
import com.cmput301w18t26.taskit.Task;
import com.cmput301w18t26.taskit.TaskItData;
import com.cmput301w18t26.taskit.TaskItFile;
import com.cmput301w18t26.taskit.TaskList;
import com.cmput301w18t26.taskit.User;
import com.cmput301w18t26.taskit.UserList;

import java.io.File;
import java.util.concurrent.TimeUnit;


/**
 * Created by kevingordon on 2018-03-04.
 */

public class TaskItDataTest extends ActivityInstrumentationTestCase2{

    public TaskItDataTest(){
        super(HomeActivity.class);
    }

    /**
     * Test adding and deleting of user in the database
     */
    public void testAddDeleteUser() {
        // Filesystem setup
        Context c = getInstrumentation().getTargetContext().getApplicationContext();
        TaskItFile.setContext(c);

        // Database setup
        TaskItData db = TaskItData.getInstance();
        db.sync();

        // Setup our mock user
        String username = "Bob";
        User u1 = new MockUser(username);

        // Need to set current user for correct operation
        db.setCurrentUser(u1);

        // Begin test
        assertFalse(db.getUsers().hasUser(u1));

        db.addUser(u1);

        assertTrue(db.getUsers().hasUser(u1));

        db.deleteUser(u1);

        assertFalse(db.getUsers().hasUser(u1));

    }

    /**
     * Test adding and deleting of task in the database
     */
    public void testAddDeleteTask() {
        // Filesystem setup
        Context c = getInstrumentation().getTargetContext().getApplicationContext();
        TaskItFile.setContext(c);

        // Database setup
        TaskItData db = TaskItData.getInstance();
        db.sync();

        // Need to setup a mock user,
        //  and make it the current database user
        //  and make it the task owner
        String username = "Bob";
        User u = new MockUser(username);
        Task t = new MockTask(u.getOwner());
        db.setCurrentUser(u);
        db.addUser(u);

        // Begin Test
        assertFalse(db.getTasks().hasTask(t));

        String UUID = db.addTask(t);

        assertTrue(db.taskExists(UUID));

        db.deleteTask(t);

        assertFalse(db.taskExists(UUID));

        // Cleanup our mess
        db.deleteUser(u);

    }

    /**
     * Test adding and deleting of bid in the database
     */
    public void testAddDeleteBid() {
        // Filesystem setup
        Context c = getInstrumentation().getTargetContext().getApplicationContext();
        TaskItFile.setContext(c);

        // Database setup
        TaskItData db = TaskItData.getInstance();
        db.sync();

        // Need to setup a mock user,
        //  and make it the current database user
        //  and make it the task owner
        String username = "Bob";
        User u = new MockUser(username);
        Task t = new MockTask(u.getOwner());
        db.setCurrentUser(u);
        db.addUser(u);
        db.addTask(t);

        Bid bid = new MockBid(username, t);

        // Begin test
        assertFalse(db.getBids().hasBid(bid));

        db.addBid(bid);

        assertTrue(db.getBids().hasBid(bid));

        db.deleteBid(bid);

        assertFalse(db.getBids().hasBid(bid));

        // Cleanup our mess
        db.deleteTask(t);
        db.deleteUser(u);
    }


}
