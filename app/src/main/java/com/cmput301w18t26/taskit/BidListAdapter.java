/*
 * Copyright 2018, Team 26 CMPUT 301. University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under the terms and coditions fo the Code of Student Behaviour at the University of Alberta.
 */

package com.cmput301w18t26.taskit;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Brady on 2018-03-10.
 */

public class BidListAdapter extends ArrayAdapter<Bid> {
    protected static final String TYPE = "type";
    private final Activity context;

    /**
     * Constructor for subscription adapter
     *
     * @param context activity
     * @param bids current subscription
     */

    public BidListAdapter(Activity context, BidList bids) {
        super(context, R.layout.list_single, bids.getBids()); //TODO list_single? check that
        this.context = context;
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
        final TextView txtName = (TextView) rowView.findViewById(R.id.txt_title);
        TextView txtDate = (TextView) rowView.findViewById(R.id.mybid);
        TextView txtCharge= (TextView) rowView.findViewById(R.id.txt_username);

        // Replace text with my own
        txtName.setText(getItem(position).getOwner());
        txtName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profileIntent = new Intent(context, UserActivity.class);
                profileIntent.putExtra(TYPE, "Other User");
                profileIntent.putExtra("User", txtName.getText().toString());
                context.startActivity(profileIntent);
            }
        });
        txtDate.setText(getItem(position).getDate().toString()); // TODO choose better fields to display
        txtCharge.setText(String.format("%.2f", (getItem(position).getAmount())));

        return rowView;
    }
}

