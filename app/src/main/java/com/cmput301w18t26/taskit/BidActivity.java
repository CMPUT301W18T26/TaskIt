package com.cmput301w18t26.taskit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
        String type = intent.getStringExtra(HomeActivity.TYPE);
        db = TaskItData.getInstance();
        final User user = db.getCurrentuser();

        Button bidButton = (Button) findViewById(R.id.bidButton);
        bidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bidText = (EditText) findViewById(R.id.bidText);
                double bidInput = Double.parseDouble(bidText.getText().toString());

                Bid bid = new Bid();
                bid.setAmount(bidInput);
                bid.setUser(user.getUsername());
                db.addBid(bid);
            }
        });



    }

    @Override
    protected void onStart(){
        super.onStart();

    }
}
