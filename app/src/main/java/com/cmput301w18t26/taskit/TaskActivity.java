package com.cmput301w18t26.taskit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by kevingordon on 2018-02-26.
 */

public class TaskActivity extends AppCompatActivity {

    protected static final String TYPE = "type";

    private TextView titleText;
    private TextView dateText;
    private TextView statusText;
    private TextView descriptionText;
    private TextView ownerText;
    private TextView locationText;
    TaskItData db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String type = intent.getStringExtra(HomeActivity.TYPE);
        db = TaskItData.getInstance();
        if (type.equals("New Task")) {
            setContentView(R.layout.edittask);
            Button createTaskButton = (Button) findViewById(R.id.createtask);
            createTaskButton.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    Intent createTaskIntent = new Intent(getApplicationContext(),ListActivity.class);
                    createTaskIntent.putExtra(TYPE, "Requested Tasks");
                    startActivity(createTaskIntent);
                    setResult(RESULT_OK);
                }
            });
        } else {
            setContentView(R.layout.viewtask);
            Task task = db.getTask(intent.getStringExtra("UUID"));

            titleText = (TextView) findViewById(R.id.tasktitle);
            descriptionText = (TextView) findViewById(R.id.taskdescription);
            statusText = (TextView) findViewById(R.id.taskstatus);
            locationText = (TextView) findViewById(R.id.tasklocation);
            ownerText = (TextView) findViewById(R.id.taskowner);
            dateText = (TextView) findViewById(R.id.taskdate);

            titleText.setText(task.getTitle());
            descriptionText.setText(task.getDescription());
            statusText.setText(task.getStatus());
            locationText.setText(task.getLocation());
            ownerText.setText(task.getOwner());
            dateText.setText(task.getDateString());

        }
        setTitle(type);
    }
}
