package com.cmput301w18t26.taskit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by kevingordon on 2018-02-26.
 */

public class UserActivity extends AppCompatActivity {

    private TaskItData db;

    protected static final String TYPE = "type";

    private EditText usernameEdit;
    private EditText emailEdit;
    private EditText phoneEdit;
    private TextView usernameText;
    private TextView nameText;
    private TextView emailText;
    private TextView phoneText;
    private TextView invalidUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String type = intent.getStringExtra(HomeActivity.TYPE);
        setTitle(type);
        db = TaskItData.getInstance();
        db.refresh();
        if (type.equals("Register")) {
            setContentView(R.layout.registeruser);
            usernameEdit = (EditText) findViewById(R.id.username);
            emailEdit = (EditText) findViewById(R.id.email);
            phoneEdit = (EditText) findViewById(R.id.phone);
            invalidUsername = (TextView) findViewById(R.id.invalid_username);
            Button actionButton = (Button) findViewById(R.id.confirmuser);
            Button cancelButton = (Button) findViewById(R.id.cancel);
            actionButton.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    String usernameInput = usernameEdit.getText().toString();
                    String emailInput = emailEdit.getText().toString();
                    int phoneInput = Integer.parseInt(phoneEdit.getText().toString());
                    if (db.userExists(usernameInput)) {
                        invalidUsername.setText("Username already exists");
                        invalidUsername.setVisibility(View.VISIBLE);
                    } else {
                        User user = new User();
                        user.setUsername(usernameInput);
                        user.setEmail(emailInput);
                        user.setPhone(phoneInput);
                        db.addUser(user);
                        db.setCurrentuser(db.getUserByUsername(usernameInput));
                        Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(homeIntent);
                        setResult(RESULT_OK);
                    }
                }
            });
            cancelButton.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(loginIntent);
                    setResult(RESULT_OK);
                }
            });
        } else if (type.equals("Update")) {
            setContentView(R.layout.edituser);
            Button actionButton = (Button) findViewById(R.id.confirmuser);
            actionButton.setText(type);
            actionButton.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(homeIntent);
                    setResult(RESULT_OK);
                }
            });
        } else {
            setContentView(R.layout.viewuser);
            Button editButton = (Button) findViewById(R.id.edit);
            usernameText = (TextView) findViewById(R.id.update_username);
            nameText = (TextView) findViewById(R.id.update_name);
            emailText = (TextView) findViewById(R.id.update_email);
            phoneText = (TextView) findViewById(R.id.update_phone);


            if (type.equals("My Profile")) {
                usernameText.setText(db.getCurrentuser().getUsername());
                nameText.setText(db.getCurrentuser().getName());
                emailText.setText(db.getCurrentuser().getEmail());
                //phoneText.setText(db.getCurrentuser().getPhone());
            } else {
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
