/*
 * Copyright 2018, Team 26 CMPUT 301. University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under the terms and coditions fo the Code of Student Behaviour at the University of Alberta.
 */

package com.cmput301w18t26.taskit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by kevingordon on 2018-02-26.
 */


/**
 * contains the buttons for the page after login "homepage", passes a different intent depending on
 * the button clicked so a different list is displayed in ListActivity
 */
public class HomeActivity extends AppCompatActivity {

    protected static final String TYPE = "type";
    protected static final String FILTER = "filter";
    private TaskItData db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homev2);
        TaskItFile.setContext(this);
        db = TaskItData.getInstance();

        setTitle(Html.fromHtml("<font color=#ffffff>" + "Home" + "</font>"));

        ImageButton mapButton = (ImageButton) findViewById(R.id.mapicon2);
        ImageButton searchTasksButton = (ImageButton) findViewById(R.id.searchicon2);
        ImageButton profileButton = (ImageButton) findViewById(R.id.myprofile2);
        ImageButton newTaskButton = (ImageButton) findViewById(R.id.newtask2);
        ImageButton myTasksButton = (ImageButton) findViewById(R.id.mytasks2);

        final Intent taskList = new Intent(getApplicationContext(),ListActivity.class);

        newTaskButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent newTaskIntent = new Intent(getApplicationContext(),TaskActivity.class);
                newTaskIntent.putExtra(TYPE, "New Task");
                startActivity(newTaskIntent);
                setResult(RESULT_OK);
            }
        });

        myTasksButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                taskList.putExtra(TYPE, "Requested Tasks");
                taskList.putExtra(FILTER, "myOwnedTasks");
                startActivity(taskList);
                setResult(RESULT_OK);
            }
        });

        searchTasksButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                taskList.putExtra(TYPE, "Search Tasks");
                taskList.putExtra(FILTER, "allTasks");
                startActivity(taskList);
                setResult(RESULT_OK);
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent registerIntent = new Intent(getApplicationContext(),UserActivity.class);
                registerIntent.putExtra(TYPE, "My Profile");
                startActivity(registerIntent);
                setResult(RESULT_OK);
            }
        });

        mapButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                intent.putExtra("calltype", "viewTasks");
                startActivity(intent);
                setResult(RESULT_OK);
            }
        });

    }

    /**
     * Adds notifications to the My Tasks button based on if there are new bids or not
     */
    @Override
    public void onResume(){
        super.onResume();

        long numNotifications = db.getNotificationCount();
        TextView notifications = findViewById(R.id.notifications);
        if (numNotifications==0) {
            notifications.setVisibility(View.GONE);
        } else {
            notifications.setVisibility(View.VISIBLE);
            notifications.setText(Long.toString(numNotifications));
        }


    }

}
