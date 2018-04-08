/*
 * Copyright 2018, Team 26 CMPUT 301. University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under the terms and coditions fo the Code of Student Behaviour at the University of Alberta.
 */

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
public class ListActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    protected static final String TYPE = "type";
    protected static String[] changeableStatuses2 = {"My Tasks","I've bidded on","I've been assigned","I've done"};
    private ListView listOfTasks;
    private ListView listOfTasks2;
    private TaskList taskList = new TaskList();
    private Task task1 = new Task();
    private TaskAdapter adapter;
    private TaskItData db;
    private String filter;
    private String dropDownFilter;
    boolean showAssignee = false;
    String query = "";
    ArrayAdapter<String> dropdownAdapter1;
    ArrayAdapter<String> dropdownAdapter2;
    Spinner spinner;
    SwipeRefreshLayout swiperefresh;
    SwipeRefreshLayout swiperefresh2;

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


        // Todo: cite https://stackoverflow.com/questions/11622539/how-do-i-use-tabhost-for-android


        //bidlist = (ListView) findViewById(R.id.tasklist);

        //bidlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //@Override
            //public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
            //}
        //});


        //Setting up spinner
        if(filter.equals("myOwnedTasks")){
            searchView.setVisibility(View.GONE);
            setupTabs();
            setupDropdowns();
        }
        if(filter.equals("allTasks")){
            spinner.setVisibility(View.GONE);
            tabwidget.setVisibility(View.GONE);
            listOfTasks.setVisibility(View.GONE);
            listOfTasks2.setVisibility(View.VISIBLE);
        }


        // Sets the dropdown menu, puts default position as the current task status
        dropdownAdapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, Task.changeableStatuses);

        dropdownAdapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, changeableStatuses2);

        spinner.setAdapter(dropdownAdapter1);
        spinner.setSelection(0);
        dropDownFilter = spinner.getItemAtPosition(0).toString();

        // Todo: cite http://www.viralandroid.com/2015/09/simple-android-tabhost-and-tabwidget-example.html


        /*
         * Sets up a SwipeRefreshLayout.OnRefreshListener that is invoked when the user
         * performs a swipe-to-refresh gesture.
         */
        swiperefresh = findViewById(R.id.swiperefresh);
        swiperefresh.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Log.i("ListActivity", "onRefresh1 called from SwipeRefreshLayout");

                        // This method performs the actual data-refresh operation.
                        // The method calls setRefreshing(false) when it's finished.
                        listRefresh();
                    }
                }
        );
        swiperefresh2 = findViewById(R.id.swiperefresh2);
        swiperefresh2.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Log.i("ListActivity", "onRefresh2 called from SwipeRefreshLayout");

                        // This method performs the actual data-refresh operation.
                        // The method calls setRefreshing(false) when it's finished.
                        listRefresh();
                    }
                }
        );


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
                getFreshTaskList();
                updateArrayAdapter();
                return false;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String queryText) {
                Log.d("ListActivity", "You submitted this search...");
                return true;
            }
            @Override
            public boolean onQueryTextChange(String queryText) {
                query = queryText;
                getFreshTaskList();
                updateArrayAdapter();
                return true;
            }
        });

    }

    private void listRefresh() {
        db.sync();
        getFreshTaskList();
        updateArrayAdapter();
        Log.i("ListActivity", "Sync complete");
        swiperefresh.setRefreshing(false);
        swiperefresh2.setRefreshing(false);
        adapter.notifyDataSetChanged();
    }

    private void updateArrayAdapter() {
        adapter.clear();
        adapter.setShowAssignee(showAssignee);
        adapter.addAll(taskList.getTasks());
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
                    Log.d("ListActivity","First Tab");
                    spinner.setAdapter(dropdownAdapter1);
                    spinner.setSelection(0);
                    dropDownFilter = spinner.getItemAtPosition(0).toString();
                }
                if (selectedTab == 1){
                    Log.d("ListActivity","Second Tab");
                    spinner.setAdapter(dropdownAdapter2);
                    spinner.setSelection(0);
                    dropDownFilter = spinner.getItemAtPosition(0).toString();
                }
            }
        });

    }

    private void setupDropdowns() {

        spinner.setOnItemSelectedListener(this);

    }

    // for spinner aka dropdowns
    public void onItemSelected(AdapterView<?> parent, View view,
                                   int pos, long id) {
            // An item was selected. You can retrieve the selected item using
            // parent.getItemAtPosition(pos)
        dropDownFilter = parent.getItemAtPosition(pos).toString();
        getFreshTaskList();
        updateArrayAdapter();
    }
    // for spinner aka dropdowns
    public void onNothingSelected(AdapterView<?> parent) {
            // Another interface callback
    }


    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        getFreshTaskList();

        adapter = new TaskAdapter(ListActivity.this, taskList);
        adapter.setShowAssignee(showAssignee);

        listOfTasks.setAdapter(adapter);
        listOfTasks2.setAdapter(adapter);
        listOfTasks.setOnItemClickListener(new ListClickHandler());
        listOfTasks2.setOnItemClickListener(new ListClickHandler());
    }

    @Override
    protected void onResume() {
        super.onResume();
        getFreshTaskList();
        updateArrayAdapter();
    }
    public void getFreshTaskList() {
        Log.d("ListActivity","FreshTasjList with filters: "+filter+", "+dropDownFilter);
        switch (filter) {
            case "myOwnedTasks":
                switch (dropDownFilter) {
                    case "My Requests":
                        taskList = db.userTasks(db.getCurrentUser());
                        showAssignee = false;
                        break;
                    case "with bids":
                        taskList = db.userTasksWithStatus(db.getCurrentUser(), "Bidded");
                        showAssignee = false;
                        break;
                    case "I've assigned":
                        taskList = db.userTasksWithStatus(db.getCurrentUser(), "Assigned");
                        showAssignee = true;
                        break;
                    case "Done":
                        taskList = db.userTasksWithStatus(db.getCurrentUser(), "Done");
                        showAssignee = true;
                        break;
                    case "My Tasks":
                        taskList = db.tasksWithUserBids(db.getCurrentUser(), false);
                        showAssignee = false;
                        break;
                    case "I've bidded on":
                        taskList = db.tasksWithUserBids(db.getCurrentUser(), true);
                        showAssignee = false;
                        break;
                    case "I've been assigned":
                        taskList = db.userAssignedTasks(db.getCurrentUser());
                        showAssignee = false;
                        break;
                    case "I've done":
                        taskList = db.userDoneTasks(db.getCurrentUser());
                        showAssignee = false;
                        break;
                }
                break;
            case "allTasks":
                taskList = db.keywordSearch(query);
                break;
        }
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
