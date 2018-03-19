package com.cmput301w18t26.taskit.IntentTesting;

import android.app.Activity;
import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;

import com.cmput301w18t26.taskit.HomeActivity;
import com.cmput301w18t26.taskit.ListActivity;
import com.cmput301w18t26.taskit.LoginActivity;
import com.cmput301w18t26.taskit.MockUser;
import com.cmput301w18t26.taskit.R;
import com.cmput301w18t26.taskit.Task;
import com.cmput301w18t26.taskit.TaskItData;
import com.cmput301w18t26.taskit.TaskItFile;
import com.cmput301w18t26.taskit.TaskList;
import com.cmput301w18t26.taskit.User;
import com.cmput301w18t26.taskit.UserActivity;
import com.robotium.solo.Solo;

/**
 * Created by kevingordon on 2018-03-17.
 */

import android.app.Activity;
import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cmput301w18t26.taskit.HomeActivity;
import com.cmput301w18t26.taskit.LoginActivity;
import com.cmput301w18t26.taskit.R;
import com.cmput301w18t26.taskit.TaskItData;
import com.cmput301w18t26.taskit.TaskItFile;
import com.cmput301w18t26.taskit.User;
import com.cmput301w18t26.taskit.UserActivity;
import com.robotium.solo.Solo;

import java.util.List;


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
        Button viewMyTasksButton = (Button) solo.getView(R.id.allrequestedtasks);
        solo.clickOnView(viewMyTasksButton);
        solo.waitForActivity(ListActivity.class, 3000);
        solo.assertCurrentActivity("Wrong Activity", ListActivity.class);

        assertFalse(solo.waitForText(taskName, 1, 3000));

        // Get the add new task button
        // fill out the tasks
        // add task
        // check that it's there
        // modify it
        // check that it changed
        // delete it
        // check that it's gone


        // End with cleanup of the mess we made...
        try {
            db.deleteUser(foo);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}

