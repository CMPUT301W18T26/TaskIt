package com.cmput301w18t26.taskit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by kevingordon on 2018-02-26.
 */

public class ListActivity extends AppCompatActivity {

    protected static final String TYPE = "type";

    private ListView listOfTasks;
    private TaskList taskList = new TaskList();
    private Task task1 = new Task();
    private TaskAdapter adapter;
    private TaskItData db;
    private String filter;
    SwipeRefreshLayout swiperefresh;
    TaskList tasks;

    private ListView bidlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listtask);
        listOfTasks = (ListView) findViewById(R.id.listOfTasks);
        db = TaskItData.getInstance();
        Intent intent = getIntent();
        String type = intent.getStringExtra(HomeActivity.TYPE);
        filter = intent.getStringExtra(HomeActivity.FILTER);
        setTitle(type);

        Button newTaskButton = (Button) findViewById(R.id.newtask);
        if (!type.equals("Requested Tasks")) {
            newTaskButton.setVisibility(View.GONE);
        } else {
            //code that apply's to requested task list
        }
        newTaskButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent newTaskIntent = new Intent(getApplicationContext(),TaskActivity.class);
                newTaskIntent.putExtra(TYPE, "New Task");
                startActivity(newTaskIntent);
                setResult(RESULT_OK);
            }
        });


        //bidlist = (ListView) findViewById(R.id.tasklist);

        //bidlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //@Override
            //public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
            //}
        //});


        /*
         * Sets up a SwipeRefreshLayout.OnRefreshListener that is invoked when the user
         * performs a swipe-to-refresh gesture.
         */
        swiperefresh = findViewById(R.id.swiperefresh);
        swiperefresh.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Log.i("ListActivity", "onRefresh called from SwipeRefreshLayout");

                        // This method performs the actual data-refresh operation.
                        // The method calls setRefreshing(false) when it's finished.
                        listRefresh();
                    }
                }
        );


    }

    private void listRefresh() {
        db.sync();
        Log.i("ListActivity", "Sync complete");
        swiperefresh.setRefreshing(false);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

        switch (filter) {
            case "myOwnedInProgress":
                taskList = db.userTasksWithStatus(db.getCurrentUser(), "Accepted");
                break;
            case "myAssigned":
                taskList = db.userAssignedTasks(db.getCurrentUser());
                break;
            case "tasksWithMyBids":
                taskList = db.tasksWithUserBids(db.getCurrentUser());
                break;
            case "myTasksWithBids":
                taskList = db.userTasksWithStatus(db.getCurrentUser(), "Bidded");
                break;
            case "myOwnedTasks":
                taskList = db.userTasks(db.getCurrentUser());
                break;
            case "allTasks":
                taskList = db.getTasks();
                break;
        }

        adapter = new TaskAdapter(ListActivity.this, taskList);
        listOfTasks.setAdapter(adapter);
        listOfTasks.setOnItemClickListener(new ListClickHandler());
    }

    public class ListClickHandler implements OnItemClickListener{

        /**
         * Activate DisplayEditSubscription when list item is clicked
         *
         * @param adapter current adapter
         * @param view current view
         * @param position current position of subscription
         * @param arg3 excess argument
         */
        @Override
        public void onItemClick(AdapterView<?> adapter, View view, int position, long arg3) {
            // TODO Auto-generated method stub
            Task task = (Task)adapter.getItemAtPosition(position);
            Intent intent = new Intent(ListActivity.this, TaskActivity.class);
            String UUID = task.getUUID();
            intent.putExtra("UUID", UUID);
            intent.putExtra(TYPE, "Existing Task");
            startActivity(intent);

        }
    }



}
