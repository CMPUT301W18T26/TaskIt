package com.cmput301w18t26.taskit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Set;

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
    private EditText editTitleText;
    private EditText editDescText;
    private TaskItData db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Intent intent = getIntent();
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
            final Task task = db.getTask(intent.getStringExtra("UUID"));
            getTaskDetails(task);

            Button editTaskButton = (Button) findViewById(R.id.edittask);
            editTaskButton.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v){
                    editTaskDetails(task);
                }
            });

            Button viewBids = (Button) findViewById(R.id.viewBids);
            final Intent bidList = new Intent(getApplicationContext(),BidListActivity.class);
            viewBids.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v){
                    startActivity(bidList);
                    setResult(RESULT_OK);
                }
            });

        }
        setTitle(type);
    }

    private void getTaskDetails(Task task) {

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

    private void editTaskDetails (final Task task) {
        setContentView(R.layout.add_modify_task);
        Spinner s = (Spinner) findViewById(R.id.spinner);
        String status = task.getStatus();

        // Sets the dropdown menu, puts default position as the current task status
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, Task.changeableStatuses);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        int spinnerpos = adapter.getPosition(status);
        s.setAdapter(adapter);
        s.setSelection(spinnerpos);

        editTitleText = (EditText) findViewById(R.id.editTitle);
        editDescText = (EditText) findViewById(R.id.editDescription);


        editTitleText.setText(task.getTitle(),TextView.BufferType.EDITABLE);
        editDescText.setText(task.getDescription(),TextView.BufferType.EDITABLE);


        Button confirmEdits = (Button) findViewById(R.id.confirmedit);
        confirmEdits.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v){
                modifyDetails (task,editTitleText,editDescText);
            }
        });

    }

    private void modifyDetails (Task task, EditText title,EditText desc){
        String editedTitle = title.getText().toString();
        String editedDesc = desc.getText().toString();

        task.setTitle(editedTitle);
        task.setDescription(editedDesc);

        finishActivity(0);
    }
}
