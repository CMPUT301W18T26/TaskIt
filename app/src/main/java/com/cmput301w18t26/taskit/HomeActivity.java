package com.cmput301w18t26.taskit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by kevingordon on 2018-02-26.
 */

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        Button inProgressButton = (Button) findViewById(R.id.inprogresstasks);
        Button assignedTasksButton = (Button) findViewById(R.id.assignedtasks);
        Button biddedTasksButton = (Button) findViewById(R.id.biddedtasks);
        Button requestedBidsButton = (Button) findViewById(R.id.requestedbidstasks);
        Button allRequestedButton = (Button) findViewById(R.id.allrequestedtasks);
        final Intent taskList = new Intent(getApplicationContext(),ListActivity.class);


        inProgressButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startActivity(taskList);
                setResult(RESULT_OK);
            }
        });

        assignedTasksButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startActivity(taskList);
                setResult(RESULT_OK);
            }
        });

        biddedTasksButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startActivity(taskList);
                setResult(RESULT_OK);
            }
        });

        requestedBidsButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startActivity(taskList);
                setResult(RESULT_OK);
            }
        });

        allRequestedButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startActivity(taskList);
                setResult(RESULT_OK);
            }
        });
    }

}
