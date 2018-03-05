package com.cmput301w18t26.taskit;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import java.io.File;


/**
 * Created by kevingordon on 2018-03-04.
 */

public class TaskItDataTest extends ActivityInstrumentationTestCase2{

    public TaskItDataTest(){
        super(TaskItDataActivity.class);
    }

    public void testAddDeleteUser() {
        Context c = getInstrumentation().getTargetContext().getApplicationContext();
        // Typically from an activity a call would be more like:
        //   TaskItData db = TaskItData.getInstance(this);
        TaskItData db = TaskItData.getInstance(c);

        User user = new MockUser("AliceBob");

        String filename = db.getUserFilename(user, false);
        File file = new File(filename);

        db.addUser(user);

        assertTrue(file.exists());

        db.deleteUser(user);

        assertFalse(file.exists());

    }

    public void testAddDeleteTask() {
        Context c = getInstrumentation().getTargetContext().getApplicationContext();
        // Typically from an activity a call would be more like:
        //   TaskItData db = TaskItData.getInstance(this);
        TaskItData db = TaskItData.getInstance(c);

        Task task = new MockTask();

        String filename = db.getTaskFilename(task, false);
        File file = new File(filename);

        db.addTask(task);

        assertTrue(file.exists());

        db.deleteTask(task);

        assertFalse(file.exists());

    }

    public void testAddDeleteBid() {
        Context c = getInstrumentation().getTargetContext().getApplicationContext();
        // Typically from an activity a call would be more like:
        //   TaskItData db = TaskItData.getInstance(this);
        TaskItData db = TaskItData.getInstance(c);

        Bid bid = new MockBid();

        String filename = db.getBidFilename(bid, false);
        File file = new File(filename);

        db.addBid(bid);

        assertTrue(file.exists());

        db.deleteBid(bid);

        assertFalse(file.exists());

    }

    public void testLoadAllFromFile() {
        Context c = getInstrumentation().getTargetContext().getApplicationContext();
        // Typically from an activity a call would be more like:
        //   TaskItData db = TaskItData.getInstance(this);
        TaskItData db = TaskItData.getInstance(c);
        String username = "LoadMe";

        User user = new MockUser(username);
        Task task = new MockTask();
        Bid bid = new MockBid();

        UserList users = db.getUsers();
        TaskList tasks = db.getTasks();
        BidList bids = db.getBids();

        assertFalse(users.hasUser(user));
        assertFalse(tasks.hasTask(task));
        assertFalse(bids.hasBid(bid));

        db.addUser(user);
        db.addTask(task);
        db.addBid(bid);

        assertTrue(users.hasUser(user));
        assertTrue(tasks.hasTask(task));
        assertTrue(bids.hasBid(bid));

        users.deleteUser(user);
        tasks.deleteTask(task);
        bids.deleteBid(bid);

        assertFalse(users.hasUser(user));
        assertFalse(tasks.hasTask(task));
        assertFalse(bids.hasBid(bid));

        db.loadAllFromFile();

        assertTrue(users.hasUser(user));
        assertTrue(tasks.hasTask(task));
        assertTrue(bids.hasBid(bid));
    }


}
