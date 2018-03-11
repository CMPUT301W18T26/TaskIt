package com.cmput301w18t26.taskit;

import android.app.Activity;
import android.content.Intent;
//import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by kevingordon on 2018-02-26.
 */

public class LoginActivity extends AppCompatActivity {

    protected static final String TYPE = "type";
    private TaskItData db;
    private EditText username;
    private TextView invalidUsername;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.login);
        username = (EditText) findViewById(R.id.username);
        Log.d("login activity", username.getText().toString());
        invalidUsername = (TextView) findViewById(R.id.invalid_username);

        super.onCreate(savedInstanceState);
        TaskItFile.setContext(this);
        db = TaskItData.getInstance();
        db.refresh();


        setTitle("Login");

        Button loginButton = (Button) findViewById(R.id.login);
        Button registerButton = (Button) findViewById(R.id.register);


        loginButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String userInput = username.getText().toString();
                if (db.userExists(userInput)) {
                    db.setCurrentuser(db.getUserByUsername(userInput));
                    Intent homeIntent = new Intent(getApplicationContext(),HomeActivity.class);
                    startActivity(homeIntent);
                    setResult(RESULT_OK);
                    db.sync();
                } else {
                    invalidUsername.setVisibility(View.VISIBLE);
                }

            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent registerIntent = new Intent(getApplicationContext(),UserActivity.class);
                registerIntent.putExtra(TYPE, "Register");
                startActivity(registerIntent);
                setResult(RESULT_OK);
            }
        });

    }


}
