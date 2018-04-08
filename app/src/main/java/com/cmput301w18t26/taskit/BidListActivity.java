/*
 * Copyright 2018, Team 26 CMPUT 301. University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under the terms and coditions fo the Code of Student Behaviour at the University of Alberta.
 */

package com.cmput301w18t26.taskit;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by hains on 3/3/18.
 */

/**
 * Shows the list of bids for the task clicked on
 * Creates dialog that allows the user to accept or decline a bid
 * If the bid is declined, it is not deleted from the database
 */
public class BidListActivity extends AppCompatActivity {

    protected static final String TYPE = "type";


    private ListView bidlistview;
    private BidList bidList; //= new BidList();
    private Task taskselected = new Task();
    private BidListAdapter adapter;
    private TaskItData db;
    private TextView nobids;
    private String intentTaskUUID;
    TaskList tasks;

    private ListView bidlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bidlist);
        bidlistview = (ListView) findViewById(R.id.bidlist);
        nobids = (TextView) findViewById(R.id.nobidstext);
        db = TaskItData.getInstance();
        Intent intent = getIntent();
        String type = intent.getStringExtra(HomeActivity.TYPE);
        intentTaskUUID = intent.getStringExtra("UUID");
        final Task task = db.getTask(intent.getStringExtra("UUID"));

        bidList = db.taskBids(task);

        setTitle("Bids");

        //Checks if there are any bids for this task
        if (db.getTaskBidCount(task) == 0 ){
            nobids.setVisibility(View.VISIBLE);
        }

        adapter = new BidListAdapter(BidListActivity.this, bidList);

        //Set the page so if the owner is viewing the bids, they can accept or decline a bid
        bidlistview.setAdapter(adapter);
        if (task.isOwner(db.getCurrentUser())) {
            bidlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                    final Bid bid = (Bid) adapter.getItem(i);

                    View promptview = getLayoutInflater().inflate(R.layout.bid_prompt, null);
                    AlertDialog.Builder bidprompt = new AlertDialog.Builder(BidListActivity.this);
                    Button acceptBid = (Button) promptview.findViewById(R.id.acceptbid);
                    Button declineBid = (Button) promptview.findViewById(R.id.declinebid);

                    bidprompt.setView(promptview);
                    final AlertDialog dialog = bidprompt.create();
                    dialog.show();

                    acceptBid.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            task.setAssignee(bid.getOwner());
                            bid.setStatus("Accepted");
                            task.setStatus("Assigned");
                            dialog.dismiss();
                            db.updateTask(task);
                            finish();
                        }
                    });

                    declineBid.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            db.deleteBid(bid);
                            dialog.dismiss();
                            adapter.remove(bid);
                            adapter.notifyDataSetChanged();

                        }
                    });

                }
            });
        }


    }

}
