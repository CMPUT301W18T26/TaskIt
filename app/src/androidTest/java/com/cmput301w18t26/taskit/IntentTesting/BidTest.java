package com.cmput301w18t26.taskit.IntentTesting;


import android.app.Activity;
import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;

import com.cmput301w18t26.taskit.MockTask;
import com.cmput301w18t26.taskit.MockUser;
import com.cmput301w18t26.taskit.Task;
import com.cmput301w18t26.taskit.TaskActivity;
import com.cmput301w18t26.taskit.TaskItData;
import com.cmput301w18t26.taskit.TaskItFile;
import com.cmput301w18t26.taskit.TaskList;
import com.cmput301w18t26.taskit.User;
import com.robotium.solo.Solo;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

/**
 * Created by Sharon Hains on 2018-03-19.
 */

public class BidTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public BidTest(){
        super(TaskActivity.class);
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

        User foo;
        TaskList tl;
        try {
            foo = db.getUserByUsername(myUsername);
            tl = db.userTasks(foo);
            for (Task t : tl.getTasks()) {
                db.deleteTask(t);
            }
            db.deleteUser(foo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        foo = new MockUser();
        foo.setUsername(myUsername);

        Task testTask = new MockTask();
        testTask.setTitle("Test Task");
        testTask.setDescription("Test desc");
        db.addTask(testTask);

        solo.assertCurrentActivity("Wrong Activity", TaskActivity.class);


    }
}

