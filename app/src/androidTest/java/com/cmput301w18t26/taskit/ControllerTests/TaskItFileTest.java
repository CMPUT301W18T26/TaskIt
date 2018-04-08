/*
 * Copyright 2018, Team 26 CMPUT 301. University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under the terms and coditions fo the Code of Student Behaviour at the University of Alberta.
 */

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
//         Context c = getInstrumentation().getTargetContext().getApplicationContext();
//         TaskItFile.setContext(c);
//         TaskItFile.deleteAllFromFile();
    }
}
