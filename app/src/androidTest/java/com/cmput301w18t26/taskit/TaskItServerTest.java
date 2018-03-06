package com.cmput301w18t26.taskit;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;

/**
 * Created by kevingordon on 2018-03-05.
 */

public class TaskItServerTest extends ActivityInstrumentationTestCase2 {

    public TaskItServerTest(){
        super(TaskItDataActivity.class);
    }

    public void testAddGetUser() {
        TaskItServer.teardownServerJob teardownServer = new TaskItServer.teardownServerJob();
        TaskItServer.addUserJob adduser = new TaskItServer.addUserJob();
        TaskItServer.getUserJob getUser = new TaskItServer.getUserJob();

        // Wipe the server
        teardownServer.execute();

        // Add a user
        User user = new MockUser("AliceBobOnline");
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
    }

    public void testAddGetTask() {
        TaskItServer.teardownServerJob teardownServer = new TaskItServer.teardownServerJob();
        TaskItServer.addTaskJob addTask = new TaskItServer.addTaskJob();
        TaskItServer.getTaskJob getTask = new TaskItServer.getTaskJob();

        // Wipe the server
        teardownServer.execute();

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
    }

    public void testAddGetBid() {
        TaskItServer.teardownServerJob teardownServer = new TaskItServer.teardownServerJob();
        TaskItServer.addBidJob addBid = new TaskItServer.addBidJob();
        TaskItServer.getBidJob getBid = new TaskItServer.getBidJob();

        // Wipe the server
        teardownServer.execute();

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

        BidList bids = new BidList();

        try {
            // Store the fetched results in a userlist
            bids = getBid.get();
        } catch (Exception e) {
            // Log.i("TaskItServerTest", "problem loading users");
        }

        assertTrue(bids.hasBid(bid));
    }

}
