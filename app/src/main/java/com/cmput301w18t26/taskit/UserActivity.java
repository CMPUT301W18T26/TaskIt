package com.cmput301w18t26.taskit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by kevingordon on 2018-02-26.
 */

public class UserActivity extends AppCompatActivity {

    protected static final String TYPE = "type";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String type = intent.getStringExtra(HomeActivity.TYPE);
        setTitle(type);
        if (type.equals("Register") || type.equals("Update")) {
            setContentView(R.layout.edituser);
            Button actionButton = (Button) findViewById(R.id.register);
            actionButton.setText(type);
            actionButton.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    Intent homeIntent = new Intent(getApplicationContext(),HomeActivity.class);
                    startActivity(homeIntent);
                    setResult(RESULT_OK);
                }
            });
        } else {
            setContentView(R.layout.viewuser);
            Button editButton = (Button) findViewById(R.id.edit);
            if (!type.equals("My Profile")) {
                editButton.setVisibility(View.GONE);
            }

            editButton.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    Intent updateIntent = new Intent(getApplicationContext(),UserActivity.class);
                    updateIntent.putExtra(TYPE, "Update");
                    startActivity(updateIntent);
                    setResult(RESULT_OK);
                }
            });
        }

    }
}
