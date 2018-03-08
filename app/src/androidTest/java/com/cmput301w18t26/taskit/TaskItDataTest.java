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
        TaskItFile.setContext(c);
        TaskItData db = TaskItData.getInstance();

        User user = new MockUser();

        db.addUser(user);

        String filename = TaskItFile.getUserFilename(user);
        File file = new File(filename);



        assertTrue(file.exists());

        db.deleteUser(user);

        assertFalse(file.exists());

    }

    public void testAddDeleteTask() {
        Context c = getInstrumentation().getTargetContext().getApplicationContext();
        // Typically from an activity a call would be more like:
        //   TaskItData db = TaskItData.getInstance(this);
        TaskItFile.setContext(c);
        TaskItData db = TaskItData.getInstance();

        Task task = new MockTask();

        db.addTask(task);

        String filename = TaskItFile.getTaskFilename(task);
        File file = new File(filename);

        assertTrue(file.exists());

        db.deleteTask(task);

        assertFalse(file.exists());

    }

    public void testAddDeleteBid() {
        Context c = getInstrumentation().getTargetContext().getApplicationContext();
        // Typically from an activity a call would be more like:
        //   TaskItData db = TaskItData.getInstance(this);
        TaskItFile.setContext(c);
        TaskItData db = TaskItData.getInstance();

        Bid bid = new MockBid();

        db.addBid(bid);

        String filename = TaskItFile.getBidFilename(bid);
        File file = new File(filename);

        assertTrue(file.exists());

        db.deleteBid(bid);

        assertFalse(file.exists());

    }

    public void testLoadAllFromFile() {
        Context c = getInstrumentation().getTargetContext().getApplicationContext();
        // Typically from an activity a call would be more like:
        //   TaskItData db = TaskItData.getInstance(this);
        TaskItFile.setContext(c);
        TaskItData db = TaskItData.getInstance();

        User user = new MockUser();
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

        db.refresh();

        assertTrue(users.hasUser(user));
        assertTrue(tasks.hasTask(task));
        assertTrue(bids.hasBid(bid));
    }


}
