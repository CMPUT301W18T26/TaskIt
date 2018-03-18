package com.cmput301w18t26.taskit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;

/**
 * Created by kevingordon on 2018-02-26.
 */

public class BidActivity extends AppCompatActivity {

    protected static final String TYPE = "type";
    private TaskItData db;
    private EditText bidText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bid);
        final Intent intent = getIntent();
        //String type = intent.getStringExtra(HomeActivity.TYPE);
        db = TaskItData.getInstance();

        final User user = db.getCurrentUser();

        Button bidButton = (Button) findViewById(R.id.bidButton);
        bidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //System.out.print
                final Task task = db.getTask(intent.getStringExtra("UUID"));
                bidText = (EditText) findViewById(R.id.bidText);
                double bidInput = Double.parseDouble(bidText.getText().toString());
                String uuid = task.getUUID();
                Bid bid = new Bid();
                bid.setParentTask(uuid);
                bid.setAmount(bidInput);
                bid.setOwner(user.getUsername());
                bid.setDate(new Date());
                Log.d("parenttask", bid.getParentTask());
                Double actualamount = bid.getAmount();
                String great = actualamount.toString();
                Log.d("amount",great);
                Log.d("user", bid.getOwner());
                db.addBid(bid);
                task.setStatus("Bidded");
                finish();

            }
        });



    }

    @Override
    protected void onStart(){
        super.onStart();

    }
}
