/*
 * Copyright 2018, Team 26 CMPUT 301. University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under the terms and coditions fo the Code of Student Behaviour at the University of Alberta.
 */

package com.cmput301w18t26.taskit.ControllerTests;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.cmput301w18t26.taskit.Bid;
import com.cmput301w18t26.taskit.BidList;
import com.cmput301w18t26.taskit.HomeActivity;
import com.cmput301w18t26.taskit.MockBid;
import com.cmput301w18t26.taskit.MockTask;
import com.cmput301w18t26.taskit.MockUser;
import com.cmput301w18t26.taskit.Task;
import com.cmput301w18t26.taskit.TaskItData;
import com.cmput301w18t26.taskit.TaskItFile;
import com.cmput301w18t26.taskit.TaskItServer;
import com.cmput301w18t26.taskit.TaskList;
import com.cmput301w18t26.taskit.User;
import com.cmput301w18t26.taskit.UserList;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;

/**
 * Created by kevingordon on 2018-03-05.
 */

public class TaskItServerTest extends ActivityInstrumentationTestCase2 {

    public TaskItServerTest(){
        super(HomeActivity.class);
    }

    public void testBareAddGetDeleteUser() {
        // TaskItServer.teardownServerJob teardownServer = new TaskItServer.teardownServerJob();
        TaskItServer.addUserJob adduser = new TaskItServer.addUserJob();
        TaskItServer.getUserJob getUser = new TaskItServer.getUserJob();
        TaskItServer.deleteUserJob delUser = new TaskItServer.deleteUserJob();

        // Wipe the server
        // teardownServer.execute();

        // Add a user
        User user = new MockUser();
        adduser.execute(user);

        // Need to sleep to be sure the data is available on server
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Retrieve the data from server
        getUser.execute("");

        UserList users = new UserList();

        try {
            // Store the fetched results in a userlist
            users = getUser.get();
        } catch (Exception e) {
            // Log.i("TaskItServerTest", "problem loading users");
        }

        assertTrue(users.hasUser(user));

        delUser.execute(user);

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Retrieve the data from server
        TaskItServer.getUserJob getUser2 = new TaskItServer.getUserJob();
        getUser2.execute("");

        try {
            // Store the fetched results in a userlist
            users = getUser2.get();
        } catch (Exception e) {
            // Log.i("TaskItServerTest", "problem loading users");
        }

        assertFalse(users.hasUser(user));

    }

    public void testConvenientAddGetDeleteUser() {
        // Wipe the server
        // TaskItServer.teardownServerJob teardownServer = new TaskItServer.teardownServerJob();
        // teardownServer.execute();


        TaskItServer server = new TaskItServer();

        User user = new MockUser();
        server.addUser(user);

        // Need to sleep to be sure the data is available on server
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        UserList users = new UserList();
        users = server.getUsers();

        assertTrue(users.hasUser(user));

        server.delUser(user);

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        users = server.getUsers();

        assertFalse(users.hasUser(user));

    }

    public void testBareAddGetDeleteTask() {
        // TaskItServer.teardownServerJob teardownServer = new TaskItServer.teardownServerJob();
        TaskItServer.addTaskJob addTask = new TaskItServer.addTaskJob();
        TaskItServer.getTaskJob getTask = new TaskItServer.getTaskJob();
        TaskItServer.getTaskJob getTask2 = new TaskItServer.getTaskJob();
        TaskItServer.deleteTaskJob delTask = new TaskItServer.deleteTaskJob();

        // Wipe the server
        // teardownServer.execute();

        // Add a user
        Task task = new MockTask();
        addTask.execute(task);

        // Need to sleep to be sure the data is available on server
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Retrieve the data from server
        getTask.execute("");

        TaskList tasks = new TaskList();

        try {
            // Store the fetched results in a userlist
            tasks = getTask.get();
        } catch (Exception e) {
            // Log.i("TaskItServerTest", "problem loading users");
        }

        assertTrue(tasks.hasTask(task));

        delTask.execute(task);

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Retrieve the data from server

        getTask2.execute("");

        try {
            // Store the fetched results in a userlist
            tasks = getTask2.get();
        } catch (Exception e) {
            // Log.i("TaskItServerTest", "problem loading users");
        }

        assertFalse(tasks.hasTask(task));
    }

    public void testConvenientAddGetDeleteTask() {
        // Wipe the server
        // TaskItServer.teardownServerJob teardownServer = new TaskItServer.teardownServerJob();
        // teardownServer.execute();


        TaskItServer server = new TaskItServer();

        Task task = new MockTask();
        server.addTask(task);

        // Need to sleep to be sure the data is available on server
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        TaskList tasks = new TaskList();
        tasks = server.getTasks();

        assertTrue(tasks.hasTask(task));

        server.delTask(task);

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        tasks = server.getTasks();

        assertFalse(tasks.hasTask(task));

    }

    public void testBareAddGetDeleteBid() {
        // TaskItServer.teardownServerJob teardownServer = new TaskItServer.teardownServerJob();
        TaskItServer.addBidJob addBid = new TaskItServer.addBidJob();
        TaskItServer.getBidJob getBid = new TaskItServer.getBidJob();
        TaskItServer.getBidJob getBid2 = new TaskItServer.getBidJob();
        TaskItServer.deleteBidJob delBid = new TaskItServer.deleteBidJob();

        // Wipe the server
        // teardownServer.execute();

        // Add a user
        Bid bid = new MockBid();
        addBid.execute(bid);

        // Need to sleep to be sure the data is available on server
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Retrieve the data from server
        getBid.execute("");

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        BidList bids = new BidList();

        try {
            // Store the fetched results in a userlist
            bids = getBid.get();
        } catch (Exception e) {
            // Log.i("TaskItServerTest", "problem loading users");
        }

        assertTrue(bids.hasBid(bid));

        delBid.execute(bid);

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Retrieve the data from server

        getBid2.execute("");

        try {
            // Store the fetched results in a userlist
            bids = getBid2.get();
        } catch (Exception e) {
            // Log.i("TaskItServerTest", "problem loading users");
        }

        assertFalse(bids.hasBid(bid));
    }

    public void testConvenientAddGetDeleteBid() {
        // Wipe the server
        // TaskItServer.teardownServerJob teardownServer = new TaskItServer.teardownServerJob();
        // teardownServer.execute();


        TaskItServer server = new TaskItServer();

        Bid bid = new MockBid();
        server.addBid(bid);

        // Need to sleep to be sure the data is available on server
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        BidList bids = new BidList();
        bids = server.getBids();

        assertTrue(bids.hasBid(bid));

        server.delBid(bid);

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        bids = server.getBids();

        assertFalse(bids.hasBid(bid));

    }

    public void testWipeServer() {
        // Wipe the server
//         TaskItServer.teardownServerJob teardownServer = new TaskItServer.teardownServerJob();
//         teardownServer.execute();
    }

    public void testCleanupOrphanedBids() {
        Context c = getInstrumentation().getTargetContext().getApplicationContext();
        TaskItFile.setContext(c);
        TaskItData db = TaskItData.getInstance();
        TaskItServer server = new TaskItServer();
        db.setCurrentUser(new MockUser("admin"));

        db.sync();

        BidList bl = db.getBids();
        BidList deleteThese = new BidList();

        Log.d("CleanupTest", "bid len "+bl.getBidCount());

        for (Bid b: bl.getBids()) {
            Log.d("CleanupTest", "processing bid "+b.getUUID());
            if (db.getTasks().getIndex(b.getParentTask()) == -1) {
                Log.d("CleanupTest", "deleting bid for " + b.getOwner());
                deleteThese.addBid(b);
            }
        }
        for (Bid b: deleteThese.getBids()) {
            db.setCurrentUser(new MockUser(b.getOwner()));
            db.deleteBid(b);
        }
    }

    public void testCleanupBids() {
        Context c = getInstrumentation().getTargetContext().getApplicationContext();
        TaskItFile.setContext(c);
        TaskItData db = TaskItData.getInstance();
        TaskItServer server = new TaskItServer();
        db.setCurrentUser(new MockUser("admin"));

        db.sync();

        BidList bl = db.getBids();
        BidList deleteThese = new BidList();

        Log.d("CleanupTest", "bid len "+bl.getBidCount());

        for (Bid b: bl.getBids()) {
            deleteThese.addBid(b);
        }

        for (Bid b: deleteThese.getBids()) {
            db.setCurrentUser(new MockUser(b.getOwner()));
            db.deleteBid(b);
        }
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void testCleanupOrphanedTasks() {
        Context c = getInstrumentation().getTargetContext().getApplicationContext();
        TaskItFile.setContext(c);
        TaskItData db = TaskItData.getInstance();
        TaskItServer server = new TaskItServer();

        db.setCurrentUser(new MockUser("admin"));

        db.sync();


        TaskList tl = db.getTasks();
        TaskList deleteThese = new TaskList();

        for (Task t: tl.getTasks()) {
            Log.d("CleanupTest", "processing "+t.getOwner());
            if (db.getTasks().getIndex(t.getOwner()) == -1) {
                Log.d("CleanupTest", "deleting task for " + t.getOwner());
                deleteThese.addTask(t);
            }
        }

        for (Task t: deleteThese.getTasks()) {
            db.setCurrentUser(new MockUser(t.getOwner()));
            db.deleteTask(t);
        }
    }

}
