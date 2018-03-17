package com.cmput301w18t26.taskit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

/**
 * Created by hains on 3/3/18.
 */

public class BidListActivity extends AppCompatActivity {

    protected static final String TYPE = "type";


    private ListView bidlistview;
    private BidList bidList; //= new BidList();
    private Task taskselected = new Task();
    private BidListAdapter adapter;
    private TaskItData db;
    TaskList tasks;

    private ListView bidlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bidlist);
        bidlistview = (ListView) findViewById(R.id.bidlist);
        db = TaskItData.getInstance();
        Intent intent = getIntent();
        String type = intent.getStringExtra(HomeActivity.TYPE);
        final Task task = db.getTask(intent.getStringExtra("UUID"));
        BidList bids = task.getBids();
        Bid bid1 = bids.getBid(0);
        String biddedtask = bid1.getParentTask();

        Log.d("title", task.getTitle());
        Log.d("desc", biddedtask);

        setTitle("Bids");

        bidList = db.taskBids(task);
        adapter = new BidListAdapter(BidListActivity.this, bidList);

        bidlistview.setAdapter(adapter);
        bidlistview.setOnItemClickListener(new ListClickHandler());

        bidlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
            Bid bid = (Bid) adapter.getItem(i);
        }
        });


    }


    public class ListClickHandler implements AdapterView.OnItemClickListener {

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
            Bid bid = (Bid)adapter.getItemAtPosition(position);
            Intent intent = new Intent(BidListActivity.this, BidActivity.class);
            String UUID = bid.getUUID();
            intent.putExtra("UUID", UUID);

            startActivity(intent);

        }
    }
}
