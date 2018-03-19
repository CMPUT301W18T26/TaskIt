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


/**
 * Created by kevingordon on 2018-03-04.
 */

public class TaskItDataTest extends ActivityInstrumentationTestCase2{

    public TaskItDataTest(){
        super(HomeActivity.class);
    }

    public void testAddDeleteUser() {
        Context c = getInstrumentation().getTargetContext().getApplicationContext();
        // Typically from an activity a call would be more like:
        //   TaskItData db = TaskItData.getInstance(this);
        TaskItFile.setContext(c);
        TaskItData db = TaskItData.getInstance();

        String username = "Bob";
        User u = new MockUser(username);
        db.setCurrentUser(u);
        db.addUser(u);

        String filename = TaskItFile.getUserFilename(u);
        File file = new File(filename);

        assertTrue(file.exists());

        db.deleteUser(u);

        assertFalse(file.exists());

    }

    public void testAddDeleteTask() {
        Context c = getInstrumentation().getTargetContext().getApplicationContext();
        // Typically from an activity a call would be more like:
        //   TaskItData db = TaskItData.getInstance(this);
        TaskItFile.setContext(c);
        TaskItData db = TaskItData.getInstance();

        String username = "Bob";
        User u = new MockUser(username);
        Task t = new MockTask(u.getOwner());
        db.setCurrentUser(u);
        db.addUser(u);
        db.addTask(t);

        String filename = TaskItFile.getTaskFilename(t);
        File file = new File(filename);

        assertTrue(file.exists());

        db.deleteTask(t);

        assertFalse(file.exists());

    }

    public void testAddDeleteBid() {
        Context c = getInstrumentation().getTargetContext().getApplicationContext();
        // Typically from an activity a call would be more like:
        //   TaskItData db = TaskItData.getInstance(this);
        TaskItFile.setContext(c);
        TaskItData db = TaskItData.getInstance();

        String username = "Bob";
        User u = new MockUser(username);
        Task t = new MockTask(u.getOwner());
        db.setCurrentUser(u);
        db.addUser(u);
        db.addTask(t);

        Bid bid = new MockBid(username, t);

        db.addBid(bid);

        String filename = TaskItFile.getBidFilename(bid);
        File file = new File(filename);

        assertTrue(file.exists());

        db.deleteBid(bid);

        assertFalse(file.exists());

    }


}
