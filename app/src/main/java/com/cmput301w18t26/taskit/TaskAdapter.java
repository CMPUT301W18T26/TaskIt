package com.cmput301w18t26.taskit;

/**
 * Created by colin on 2018-03-07.
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class TaskAdapter extends ArrayAdapter<Task> {

    private final Activity context;

    /**
     * Constructor for subscription adapter
     *
     * @param context activity
     * @param tasks current subscription
     */

    public TaskAdapter(Activity context, TaskList tasks) {
        super(context, R.layout.list_single, tasks.getTasks());
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
        TextView txtName = (TextView) rowView.findViewById(R.id.txt_name);
        TextView txtDate = (TextView) rowView.findViewById(R.id.txt_date);
        TextView txtCharge= (TextView) rowView.findViewById(R.id.txt_charge);

        // Replace text with my own
        txtName.setText(getItem(position).getTitle());
        txtDate.setText(getItem(position).getLocation());
        txtCharge.setText(getItem(position).getOwner());

        return rowView;
    }

}
