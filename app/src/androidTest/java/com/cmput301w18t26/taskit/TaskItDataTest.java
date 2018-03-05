package com.cmput301w18t26.taskit;

import android.app.Activity;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ServiceTestCase;
import android.util.Log;

import org.junit.Before;

import java.io.File;
import java.lang.reflect.Method;

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

        assertFalse(file.exists());

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

        assertFalse(file.exists());

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

        assertFalse(file.exists());

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
        String filename = db.getUserFilename(user, false);

        File file = new File(filename);

        UserList users = db.getUsers();

        assertFalse(users.hasUser(user));

        db.addUser(user);
        assertTrue(users.hasUser(user));
        assertTrue(file.exists());

        users.deleteUser(user);
        assertFalse(users.hasUser(user));
        assertTrue(file.exists());

        db.loadAllFromFile();
        assertTrue(users.hasUser(user));
    }


}
