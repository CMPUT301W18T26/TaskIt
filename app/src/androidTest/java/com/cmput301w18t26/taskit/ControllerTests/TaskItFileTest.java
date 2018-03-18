package com.cmput301w18t26.taskit.ControllerTests;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;

import com.cmput301w18t26.taskit.HomeActivity;
import com.cmput301w18t26.taskit.TaskItFile;

/**
 * Created by kevingordon on 2018-03-17.
 */

public class TaskItFileTest extends ActivityInstrumentationTestCase2 {

    public TaskItFileTest(){
        super(HomeActivity.class);
    }

    public void testWipeFileSystem() {
        Context c = getInstrumentation().getTargetContext().getApplicationContext();
        // Typically from an activity a call would be more like:
        //   TaskItData db = TaskItData.getInstance(this);
        TaskItFile.setContext(c);
        TaskItFile.deleteAllFromFile();
    }
}
