/*
 * Copyright 2018, Team 26 CMPUT 301. University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under the terms and coditions fo the Code of Student Behaviour at the University of Alberta.
 */

package com.cmput301w18t26.taskit.IntentTesting;

import android.app.Activity;
import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.cmput301w18t26.taskit.BidActivity;
import com.cmput301w18t26.taskit.BidListActivity;
import com.cmput301w18t26.taskit.HomeActivity;
import com.cmput301w18t26.taskit.ListActivity;
import com.cmput301w18t26.taskit.LoginActivity;
import com.cmput301w18t26.taskit.MapActivity;
import com.cmput301w18t26.taskit.MockTask;
import com.cmput301w18t26.taskit.MockUser;
import com.cmput301w18t26.taskit.R;
import com.cmput301w18t26.taskit.Task;
import com.cmput301w18t26.taskit.TaskActivity;
import com.cmput301w18t26.taskit.TaskItData;
import com.cmput301w18t26.taskit.TaskItFile;
import com.cmput301w18t26.taskit.TaskList;
import com.cmput301w18t26.taskit.User;
import com.cmput301w18t26.taskit.UserActivity;
import com.google.android.gms.maps.MapFragment;
import com.robotium.solo.Solo;

/**
 * Created by kevingordon on 2018-03-17.
 */



public class BidTest extends ActivityInstrumentationTestCase2 {

    private Solo solo;

    public BidTest(){
        super(HomeActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    public void testCreateEditViewUser() {
        // ADD THIS FOR ALL TEST
        Context c = getInstrumentation().getTargetContext().getApplicationContext();
        TaskItFile.setContext(c);
        TaskItData db = TaskItData.getInstance();
        // END ADD ALL THIS

        String myUsername = "Foobar";
        String otherUsername = "Example User";
        String name2 = "testName2";
        String taskName = "Example Task";

        User foo = new MockUser();
        User foo2 = new MockUser();
        TaskList tl;
        try {
            foo = db.getUserByUsername(myUsername);
            tl = db.userTasks(foo);
            for (Task t: tl.getTasks()) {
                db.deleteTask(t);
            }
            db.deleteUser(foo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        foo = new MockUser();
        foo.setUsername(myUsername);
        foo.setName("FooBar");
        db.addUser(foo);
        foo2.setUsername(otherUsername);
        db.addUser(foo2);
        db.setCurrentUser(foo);

        Task exampleTask = new MockTask();

        exampleTask.setTitle(taskName);
        exampleTask.setStatus("Requested");
        exampleTask.setOwner(foo2);
        db.addTask(exampleTask);


        solo.assertCurrentActivity("Wrong Activity", HomeActivity.class);

        assertFalse(solo.waitForText(taskName, 1, 3000));

        //TEST USE-CASE  Place Bid on Task
        ImageButton SearchTasksButton = (ImageButton) solo.getView(R.id.searchicon2);
        solo.clickOnView(SearchTasksButton);
        solo.assertCurrentActivity("Wrong Activity", ListActivity.class);
        //click on task
        solo.clickOnText(taskName);
        solo.assertCurrentActivity("Wrong Activity", TaskActivity.class);
        //click on bid
        Button bidButton = (Button) solo.getView(R.id.bidTask);
        solo.clickOnView(bidButton);
        solo.assertCurrentActivity("Wrong Activity", BidActivity.class);
        //make a bid
        String bid = "20";
        solo.enterText((EditText) solo.getView(R.id.bidText), bid);
        Button bidButton2 = (Button) solo.getView(R.id.bidButton);
        solo.clickOnView(bidButton2);
        solo.assertCurrentActivity("Wrong Activity", TaskActivity.class);
        Button viewBids = (Button) solo.getView(R.id.viewBids);
        solo.clickOnView(viewBids);
        solo.assertCurrentActivity("Wrong Activity", BidListActivity.class);
        solo.waitForActivity("Wait for Activity", 2000);
        solo.goBack();
        solo.goBack();






        // End with cleanup of the mess we made...
        try {
            db.deleteUser(foo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            db.deleteUser(foo2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            db.deleteTask(exampleTask);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}

