package com.cmput301w18t26.taskit;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabWidget;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by kevingordon on 2018-02-26.
 */

/**
 * sets the homepage view once the user logs in, contains method to refresh the page when the page
 * is swiped down.
 * Contains methods to filter different types of lists depending on the intent passed in
 * contains method to handle clicking on item in list
 */
public class ListActivity extends AppCompatActivity {

    protected static final String TYPE = "type";
    protected static String[] changeableStatuses2 = {"My Tasks","I've bidded","I've been assigned","I've done"};
    private ListView listOfTasks;
    private ListView listOfTasks2;
    private TaskList taskList = new TaskList();
    private Task task1 = new Task();
    private TaskAdapter adapter;
    private TaskItData db;
    private String filter;
    ArrayAdapter<String> adapter2;
    Spinner spinner;
    SwipeRefreshLayout swiperefresh;
    TaskList tasks;
    TabHost host;

    private ListView bidlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listtask);
        listOfTasks = (ListView) findViewById(R.id.listOfTasks);
        listOfTasks2 = (ListView) findViewById(R.id.listOfTasks2);
        SearchView searchView = (SearchView) findViewById(R.id.searchView);
        spinner = (Spinner) findViewById(R.id.viewtaskspinner);
        TabWidget tabwidget = (TabWidget) findViewById(android.R.id.tabs);
        host = (TabHost)findViewById(R.id.tab_host);
        db = TaskItData.getInstance();
        Intent intent = getIntent();
        String type = intent.getStringExtra(HomeActivity.TYPE);
        filter = intent.getStringExtra(HomeActivity.FILTER);
        setTitle(type);


        //https://stackoverflow.com/questions/11622539/how-do-i-use-tabhost-for-android


        //bidlist = (ListView) findViewById(R.id.tasklist);

        //bidlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //@Override
            //public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
            //}
        //});


        //Setting up spinner
        if(!filter.equals("allTasks")){
            searchView.setVisibility(View.GONE);
            setupTabs();
        }else{
            spinner.setVisibility(View.GONE);
            tabwidget.setVisibility(View.GONE);
            listOfTasks.setVisibility(View.VISIBLE);
        }


        // Sets the dropdown menu, puts default position as the current task status
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, Task.changeableStatuses);

        adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, changeableStatuses2);

        spinner.setAdapter(adapter);

        // Todo: cite http://www.viralandroid.com/2015/09/simple-android-tabhost-and-tabwidget-example.html


        /*
         * Sets up a SwipeRefreshLayout.OnRefreshListener that is invoked when the user
         * performs a swipe-to-refresh gesture.
         */

        ImageButton refreshbutton = (ImageButton) findViewById(R.id.refreshimage);
        refreshbutton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Log.i("ListActivity", "onRefresh called from SwipeRefreshLayout");

                // This method performs the actual data-refresh operation.
                // The method calls setRefreshing(false) when it's finished.
                listRefresh();
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                updateArrayAdapter(db.getTasks());
                return false;
            }
        });
        
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("ListActivity", "Query1: " + query);
                if (query.length()==0) {
                    Log.d("ListActivity", "It's empty, so I'll get all tasks!");
                    updateArrayAdapter(db.getTasks());
                } else {
                    updateArrayAdapter(db.keywordSearch(query));
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("ListActivity", "Query2: " + newText);
                if (newText.length()==0) {
                    Log.d("ListActivity", "It's empty, so I'll get all tasks!");
                    updateArrayAdapter(db.getTasks());
                } else {
                    updateArrayAdapter(db.keywordSearch(newText));
                }
                return false;
            }
        });

    }

    private void listRefresh() {
        db.sync();
        Log.i("ListActivity", "Sync complete");
        //swiperefresh.setRefreshing(false);
        adapter.notifyDataSetChanged();
    }

    private void updateArrayAdapter(TaskList t) {
        Log.d("ListActivity", "updateArrayAdapter called with "+Integer.toString(t.getTaskCount())+" tasks...");

        adapter.clear();
        adapter.addAll(t.getTasks());
        adapter.notifyDataSetChanged();
    }

    private void setupTabs(){
        host.setup();

        TabHost.TabSpec spec = host.newTabSpec("My Requests");
        spec.setContent(R.id.tab1);
        spec.setIndicator("My Requests");
        host.addTab(spec);

        spec = host.newTabSpec("My Tasks");
        spec.setContent(R.id.tab2);
        spec.setIndicator("My Tasks");
        host.addTab(spec);

        host.setOnTabChangedListener(new TabHost.OnTabChangeListener(){
            @Override
            public void onTabChanged(String tabId) {
                int selectedTab = host.getCurrentTab();
                if (selectedTab == 0){
                    Log.d("a","First Tab");
                    spinner.setAdapter(adapter);
                    spinner.setSelection(0);
                }
                if (selectedTab == 1){
                    Log.d("a","Second Tab");
                    spinner.setAdapter(adapter2);
                    spinner.setSelection(0);

                }
            }
        });

    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        boolean showAssignee = false;

        switch (filter) {
            case "myOwnedInProgress":
                taskList = db.userTasksWithStatus(db.getCurrentUser(), "Assigned");
                showAssignee = true;
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
        adapter.setShowAssignee(showAssignee);

        listOfTasks.setAdapter(adapter);
        listOfTasks2.setAdapter(adapter);
        listOfTasks.setOnItemClickListener(new ListClickHandler());
        listOfTasks2.setOnItemClickListener(new ListClickHandler());
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
