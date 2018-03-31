package com.cmput301w18t26.taskit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        setTitle(Html.fromHtml("<font color=#ffffff>" + "Home" + "</font>"));

        Button inProgressButton = (Button) findViewById(R.id.inprogresstasks);
        Button assignedTasksButton = (Button) findViewById(R.id.assignedtasks);
        Button biddedTasksButton = (Button) findViewById(R.id.biddedtasks);
        Button requestedBidsButton = (Button) findViewById(R.id.requestedbidstasks);
        Button allRequestedButton = (Button) findViewById(R.id.allrequestedtasks);
        Button searchTasksButton = (Button) findViewById(R.id.searchtasks);
        Button profileButton = (Button) findViewById(R.id.profile);
        Button mapButton = (Button) findViewById(R.id.mapbutton);

        final Intent taskList = new Intent(getApplicationContext(),ListActivity.class);


        inProgressButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                taskList.putExtra(TYPE, "My Tasks");
                taskList.putExtra(FILTER, "myOwnedInProgress");
                startActivity(taskList);
                setResult(RESULT_OK);
            }
        });

        assignedTasksButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                taskList.putExtra(TYPE, "My Tasks");
                taskList.putExtra(FILTER, "myAssigned");
                startActivity(taskList);
                setResult(RESULT_OK);
            }
        });

        biddedTasksButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                taskList.putExtra(TYPE, "My Tasks");
                taskList.putExtra(FILTER, "tasksWithMyBids");
                startActivity(taskList);
                setResult(RESULT_OK);
            }
        });

        requestedBidsButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                taskList.putExtra(TYPE, "Requested Tasks");
                taskList.putExtra(FILTER, "myTasksWithBids");
                startActivity(taskList);
                setResult(RESULT_OK);
            }
        });

        allRequestedButton.setOnClickListener(new View.OnClickListener() {

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
                startActivity(intent);
                setResult(RESULT_OK);
            }
        });

    }

}
