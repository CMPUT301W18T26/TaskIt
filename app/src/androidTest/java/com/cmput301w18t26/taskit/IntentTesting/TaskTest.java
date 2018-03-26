package com.cmput301w18t26.taskit.IntentTesting;

import android.app.Activity;
import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;

import com.cmput301w18t26.taskit.HomeActivity;
import com.cmput301w18t26.taskit.ListActivity;
import com.cmput301w18t26.taskit.LoginActivity;
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

//        Task notyourTask = new MockTask();
//        notyourTask.setOwner("Kevin");
//        notyourTask.setTitle("Someone else's task");
//        notyourTask.setDescription("Someone else's description");
//        db.addTask(notyourTask);
//        db.sync();

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

        //TEST USE-CASE 01.01.01 - Add task to task list
        // Get the add new task button
        Button addTaskButton = (Button) solo.getView(R.id.newtask);
        solo.clickOnView(addTaskButton);
        // fill out the tasks
        String testtitle = "An example task!";
        String testdescr = "A fun and exciting description!";
        solo.enterText((EditText) solo.getView(R.id.update_title), testtitle);
        solo.enterText((EditText) solo.getView(R.id.update_description), testdescr);
        // add task
        Task testTask = new MockTask();
        testTask.setTitle(testtitle);
        testTask.setDescription(testdescr);
        Button confirmNewTask = (Button) solo.getView(R.id.createtask);
        solo.clickOnView(confirmNewTask);
        solo.waitForActivity(ListActivity.class, 3000);
        // check that it's there
        solo.waitForText("An example task!");
        solo.clickOnText(testtitle);

        //TEST USE-CASE 01.03.01 - Edit task details
        // modify it
        Button editTaskButton = (Button) solo.getView(R.id.edittask);
        solo.clickOnView(editTaskButton);
        String edittitle = "An edited example task!";
        String editdesc = "An edited fun and exciting description!";
        solo.clearEditText((EditText) solo.getView(R.id.editTitle));
        solo.clearEditText((EditText) solo.getView(R.id.editDescription));
        solo.enterText((EditText) solo.getView(R.id.editTitle), edittitle);
        solo.enterText((EditText) solo.getView(R.id.editDescription), editdesc);
        // check that it changed
        Button confirmedit = (Button) solo.getView(R.id.confirmedit);
        solo.clickOnView(confirmedit);

        //TEST USE-CASE 01.04.01 - Delete task
        // delete it
        Button deletetask = (Button) solo.getView(R.id.deletetask);
        solo.clickOnView(deletetask);
        // check that it's gone
        solo.waitForActivity(ListActivity.class, 3000);


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

