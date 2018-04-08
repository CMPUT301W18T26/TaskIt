/*
 * Copyright 2018, Team 26 CMPUT 301. University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under the terms and coditions fo the Code of Student Behaviour at the University of Alberta.
 */

package com.cmput301w18t26.taskit;

/**
 * Created by colin on 2018-03-07.
 */

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TaskAdapter extends ArrayAdapter<Task> {

    protected static final String TYPE = "type";
    private TaskItData db;
    private final Activity context;
    private boolean showAssignee;

    /**
     * Constructor for subscription adapter
     *
     * @param context activity
     * @param tasks current subscription
     */

    public TaskAdapter(Activity context, TaskList tasks) {
        super(context, R.layout.list_single, tasks.getTasks());
        this.context = context;
        db = TaskItData.getInstance();
    }

    /**
     * Overridden method to set values for name, date, and charge in the textview
     *
     * @param position position of current subscription
     * @param convertView the current virw (textview)
     * @param parent parent of textview
     * @return
     */

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_single, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt_title);
        TextView txtStatus = (TextView) rowView.findViewById(R.id.txt_status);
        final TextView txtUsername= (TextView) rowView.findViewById(R.id.txt_username);
        TextView txtBid= (TextView) rowView.findViewById(R.id.bid);
        TextView txtMyBid= (TextView) rowView.findViewById(R.id.mybid);



        // Replace text with my own
        txtTitle.setText(getItem(position).getTitle());
        txtStatus.setText(db.getTaskStatus(getItem(position)));
        if (showAssignee) {
            txtUsername.setText(getItem(position).getAssignee());
        } else {
            txtUsername.setText(getItem(position).getOwner());
        }
        txtUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profileIntent = new Intent(context, UserActivity.class);
                profileIntent.putExtra(TYPE, "Other User");
                profileIntent.putExtra("User", txtUsername.getText().toString());
                context.startActivity(profileIntent);
            }
        });



        double lowestBid = db.getLowestBid(getItem(position));
        if (lowestBid != -1) {
            txtBid.setText("Lowest Bid: " + String.format("%.2f", lowestBid));
        } else {
            txtBid.setText("Lowest Bid: None");
        }
        double lowestBidForUser = db.getLowestBidForUser(getItem(position), db.getCurrentUser());
        if (lowestBidForUser != Double.POSITIVE_INFINITY) {
            txtMyBid.setText("My Bid: " + String.format("%.2f", lowestBidForUser));
        }
        return rowView;
    }

    public void setShowAssignee(boolean showAssignee) {
        this.showAssignee = showAssignee;
    }
}
