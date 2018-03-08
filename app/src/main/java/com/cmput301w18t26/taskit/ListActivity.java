package com.cmput301w18t26.taskit;

import android.content.Intent;
import android.os.Bundle;
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

/**
 * Created by kevingordon on 2018-02-26.
 */

public class ListActivity extends AppCompatActivity {

    protected static final String TITLE = "title";
    protected static final String LOCATION = "location";
    protected static final String OWNER = "owner";
    protected static final String DESCRIPTION = "description";
    protected static final String STATUS = "status";
    protected static final String DATE = "date";
    protected static final String TYPE = "type";
    protected static final String POSITION = "position";

    private ListView listOfTasks;
    private ArrayList<Task> taskList = new ArrayList<Task>();
    private Task task1 = new Task();
    private TaskAdapter adapter;
    private TaskItData db;
    TaskList tasks;

    private ListView bidlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listtask);
        listOfTasks = (ListView) findViewById(R.id.listOfTasks);
        db = TaskItData.getInstance();
        db.refresh();
        Intent intent = getIntent();
        String type = intent.getStringExtra(HomeActivity.TYPE);
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


    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        taskList = db.getTasks().getTasks();
        task1.setTitle("Water Flower");
        task1.setLocation("Earth");
        task1.setOwner("Kietherson");
        task1.setDescription("Water a flower.");
        task1.setStatus("open");
        Date myDate = new Date(2014, 02, 11);
        task1.setDate(myDate);
        taskList.add(task1);
        Log.w("taskList", taskList.toString());
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
            String title = task.getTitle();
            String location = task.getLocation();
            String owner = task.getOwner();
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            String date = df.format(task.getDate());
            String description = task.getDescription();
            String status = task.getStatus();


            Intent intent = new Intent(ListActivity.this, TaskActivity.class);
            intent.putExtra(TYPE, "Task");
            intent.putExtra(TITLE, title);
            intent.putExtra(LOCATION, location);
            intent.putExtra(OWNER, owner);
            intent.putExtra(DATE, date);
            intent.putExtra(DESCRIPTION, description);
            intent.putExtra(STATUS, status);
            intent.putExtra(POSITION, position);

            startActivity(intent);

        }
    }

}
