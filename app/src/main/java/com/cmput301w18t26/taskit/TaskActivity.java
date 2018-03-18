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

import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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
    private TextView lowbidText;
    private EditText editDescText;
    private TaskItData db;
    private Task task;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = getIntent();
        String type = intent.getStringExtra(HomeActivity.TYPE);
        db = TaskItData.getInstance();
        if (type.equals("Edit")){
            task = db.getTask(intent.getStringExtra("UUID"));
            Log.d("contentview","should be modify now");
            setContentView(R.layout.add_modify_task);
            editTaskDetails(task);
            setResult(RESULT_OK);
        }
        else if (type.equals("New Task")) {
            setContentView(R.layout.edittask);
            Button createTaskButton = (Button) findViewById(R.id.createtask);
            createTaskButton.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    setTaskDetails();
                    finish();
                }
            });
        } else {
            setContentView(R.layout.viewtask);
            Button bid = (Button) findViewById(R.id.bidTask);
            Button deleteTaskButton = (Button) findViewById(R.id.deletetask);
            Button editTaskButton = (Button) findViewById(R.id.edittask);

            task = db.getTask(intent.getStringExtra("UUID"));
            getTaskDetails(task);
            User curruser = db.getCurrentUser();

            if (task.isOwner(curruser)){
                bid.setVisibility(View.GONE);
            } else {
                deleteTaskButton.setVisibility(View.GONE);
                editTaskButton.setVisibility(View.GONE);
            }

            editTaskButton.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v){
                    Intent editIntent = new Intent(getApplicationContext(), TaskActivity.class);
                    String UUID = task.getUUID();
                    editIntent.putExtra(TYPE, "Edit");
                    editIntent.putExtra("UUID", UUID);
                    startActivity(editIntent);
                    setResult(RESULT_OK);


                }
            });

            deleteTaskButton.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v){
                    db.deleteTask(task);
                    Log.d("delete","got to bidlist");
                    finish();
                }
            });

            Button viewBids = (Button) findViewById(R.id.viewBids);
            final Intent bidList = new Intent(getApplicationContext(),BidListActivity.class);
            viewBids.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v){
                    Task task = db.getTask(intent.getStringExtra("UUID"));
                    Intent intent = new Intent(TaskActivity.this, BidListActivity.class);
                    String UUID = task.getUUID();
                    intent.putExtra("UUID", UUID);
                    Log.d("view bids","got to bidlist");
                    startActivity(intent);
                    getTaskDetails(task);
                    setResult(RESULT_OK);

                }
            });

            Button addBidButton = (Button) findViewById(R.id.bidTask);
            final Intent bidActivity = new Intent(getApplicationContext(), BidActivity.class);
            addBidButton.setOnClickListener(new View.OnClickListener(){

                public void onClick(View v){
                    Intent intent = new Intent(TaskActivity.this, BidActivity.class);
                    String UUID = task.getUUID();
                    Log.d("added bid","got to added");
                    intent.putExtra("UUID", UUID);
                    startActivity(intent);
                    setResult(RESULT_OK);
                }

            });

        }
        setTitle(type);
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        intent = getIntent();
        task = db.getTask(intent.getStringExtra("UUID"));
        getTaskDetails(task);

    }

    private void setTaskDetails() {
        titleText = (TextView) findViewById(R.id.update_title);
        descriptionText = (TextView) findViewById(R.id.update_description);

        Task t = new Task();
        t.setTitle(titleText.getText().toString());
        t.setDescription(descriptionText.getText().toString());
        t.setDate(new Date());
        t.setLocation("Unknown");
        t.setStatus("Requested");
        t.setOwner(db.getCurrentUser().getOwner());
        db.addTask(t);
    }

    private void getTaskDetails(Task task) {

        titleText = (TextView) findViewById(R.id.tasktitle);
        descriptionText = (TextView) findViewById(R.id.taskdescription);
        statusText = (TextView) findViewById(R.id.taskstatus);
        locationText = (TextView) findViewById(R.id.tasklocation);
        ownerText = (TextView) findViewById(R.id.taskowner);
        dateText = (TextView) findViewById(R.id.taskdate);
        lowbidText = (TextView) findViewById(R.id.tasklowbid);

        titleText.setText(task.getTitle());
        descriptionText.setText(task.getDescription());
        statusText.setText(task.getStatus());
        locationText.setText(task.getLocation());
        ownerText.setText(task.getOwner());
        double lowestBid = db.getLowestBid(task);
        if (lowestBid == -1) {
            lowbidText.setText("None");
        } else {
            lowbidText.setText(String.valueOf(db.getLowestBid(task)));
        }

        dateText.setText(task.getDateString());

    }

    private void editTaskDetails (final Task task) {
        //setContentView(R.layout.add_modify_task);
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        String status = task.getStatus();

        // Sets the dropdown menu, puts default position as the current task status
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, Task.changeableStatuses);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        int spinnerpos = adapter.getPosition(status);
        spinner.setAdapter(adapter);
        spinner.setSelection(spinnerpos);

        editTitleText = (EditText) findViewById(R.id.editTitle);
        editDescText = (EditText) findViewById(R.id.editDescription);


        editTitleText.setText(task.getTitle(),TextView.BufferType.EDITABLE);
        editDescText.setText(task.getDescription(),TextView.BufferType.EDITABLE);


        Button confirmEdits = (Button) findViewById(R.id.confirmedit);
        confirmEdits.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v){
                modifyDetails (task,editTitleText,editDescText,spinner);
            }
        });

    }

    private void modifyDetails (Task task, EditText title,EditText desc, Spinner spinner){
        String newstatus = spinner.getSelectedItem().toString();
        String editedTitle = title.getText().toString();
        String editedDesc = desc.getText().toString();

        task.setTitle(editedTitle);
        task.setDescription(editedDesc);
        task.setStatus(newstatus);

        db.updateTask(task);
        finish();
        //setContentView(R.layout.viewtask);
        //getTaskDetails(task);
    }
}
