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



public class TaskTest extends ActivityInstrumentationTestCase2 {

    private Solo solo;

    public TaskTest(){
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
        String name1 = "testName1";
        String name2 = "testName2";
        String taskName = "cuztomName";

        User foo = new MockUser();
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

        solo.assertCurrentActivity("Wrong Activity", HomeActivity.class);

        assertFalse(solo.waitForText(taskName, 1, 3000));

        //TEST USE-CASE 01.01.01 - Add task to task list
        // Get the add new task button
        ImageButton addTaskButton = (ImageButton) solo.getView(R.id.newtask2);
        solo.clickOnView(addTaskButton);
        solo.assertCurrentActivity("Wrong Activity", TaskActivity.class);
        // fill out the tasks
        String testtitle = "An example task!";
        String testdescr = "A fun and exciting description!";
        solo.enterText((EditText) solo.getView(R.id.update_title), testtitle);
        solo.enterText((EditText) solo.getView(R.id.update_description), testdescr);

        Button addLocationButton = (Button) solo.getView(R.id.add_location);
        solo.clickOnView(addLocationButton);

        solo.assertCurrentActivity("Wrong Activity", MapActivity.class);
        solo.waitForActivity("Wait for Activity", 2000);
        solo.clickLongOnText("Tap And Hold");
        //solo.goBack();
        solo.waitForActivity(TaskActivity.class, 3000);

        Button addPhotosButton = (Button) solo.getView(R.id.add_photos);
        solo.clickOnView(addPhotosButton);
        solo.waitForActivity("Wait for Activity", 2000);
        solo.goBack();
        solo.assertCurrentActivity("Wrong Activity", TaskActivity.class);
        // add task
        //Task testTask = new MockTask();
        //testTask.setTitle(testtitle);
        //testTask.setDescription(testdescr);
        Button confirmNewTask = (Button) solo.getView(R.id.createtask);
        solo.clickOnView(confirmNewTask);
        //solo.goBack();
        solo.assertCurrentActivity("Wrong Activity", HomeActivity.class);
        // check that it's there
        //solo.waitForText("An example task!");
        //solo.clickOnText(testtitle);

        //TEST USE-CASE 01.03.01 - Edit task details
        // modify it
        ImageButton ViewTasksButton = (ImageButton) solo.getView(R.id.mytasks2);
        solo.clickOnView(ViewTasksButton);
        solo.assertCurrentActivity("Wrong Activity", ListActivity.class);

        ListView listOfTasks = (ListView) solo.getView(R.id.listOfTasks);
        solo.clickOnText("An example task!");
        solo.assertCurrentActivity("Wrong Activity", TaskActivity.class);

        Button editTask = (Button) solo.getView(R.id.edittask);
        solo.clickOnView(editTask);
        solo.assertCurrentActivity("Wrong Activity", TaskActivity.class);


        String edittitle = "An edited example task!";
        String editdesc = "An edited fun and exciting description!";
        solo.clearEditText((EditText) solo.getView(R.id.editTitle));
        solo.clearEditText((EditText) solo.getView(R.id.editDescription));
        solo.enterText((EditText) solo.getView(R.id.editTitle), edittitle);
        solo.enterText((EditText) solo.getView(R.id.editDescription), editdesc);
        // check that it changed
        Button confirmedit = (Button) solo.getView(R.id.confirmedit);
        solo.clickOnView(confirmedit);

        Button confirmeditPrompt = (Button) solo.getView(R.id.yes);
        solo.clickOnView(confirmeditPrompt);
        solo.waitForActivity(TaskActivity.class, 1000);
        //TEST USE-CASE 01.04.01 - Delete task
        // delete it
        Button deletetask = (Button) solo.getView(R.id.deletetask);
        solo.clickOnView(deletetask);
        // check that it's gone
        solo.waitForActivity(ListActivity.class, 1000);

        Button confirmedeletePrompt = (Button) solo.getView(R.id.yes);
        solo.clickOnView(confirmedeletePrompt);

        solo.assertCurrentActivity("Wrong Activity", ListActivity.class);
        solo.waitForActivity(ListActivity.class, 2000);

        //bid on task
        //solo.clickOnText("Someone else's task");



        // End with cleanup of the mess we made...
        try {
            db.deleteUser(foo);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}

