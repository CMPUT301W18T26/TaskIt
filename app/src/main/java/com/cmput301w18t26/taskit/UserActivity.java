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
    private EditText nameEdit;
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

        if (type.equals("Register")) {
            setContentView(R.layout.registeruser);
            usernameEdit = (EditText) findViewById(R.id.username);
            emailEdit = (EditText) findViewById(R.id.email);
            phoneEdit = (EditText) findViewById(R.id.phone);
            nameEdit = (EditText) findViewById(R.id.name);
            invalidUsername = (TextView) findViewById(R.id.invalid_username);
            Button actionButton = (Button) findViewById(R.id.confirmuser);
            Button cancelButton = (Button) findViewById(R.id.cancel);
            actionButton.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    String usernameInput = usernameEdit.getText().toString();
                    String nameInput = nameEdit.getText().toString();
                    String emailInput = emailEdit.getText().toString();
                    long phoneInput = Long.parseLong(phoneEdit.getText().toString());
                    if (db.userExists(usernameInput)) {
                        invalidUsername.setText("Username already exists");
                        invalidUsername.setVisibility(View.VISIBLE);
                    } else {
                        User user = new User();
                        user.setUsername(usernameInput);
                        user.setName(nameInput);
                        user.setEmail(emailInput);
                        user.setPhone(phoneInput);
                        db.setCurrentUser(user);
                        db.addUser(user);
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
            Button cancelButton = (Button) findViewById(R.id.cancel);
            usernameText = (TextView) findViewById(R.id.username);
            usernameText.setText(db.getCurrentUser().getUsername());
            emailEdit = (EditText) findViewById(R.id.email);
            emailEdit.setText(db.getCurrentUser().getEmail());
            phoneEdit = (EditText) findViewById(R.id.phone);
            phoneEdit.setText(String.valueOf(db.getCurrentUser().getPhone()));
            nameEdit = (EditText) findViewById(R.id.name);
            nameEdit.setText(db.getCurrentUser().getName());
            actionButton.setText(type);
            actionButton.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    db.getCurrentUser().setName(nameEdit.getText().toString());
                    db.getCurrentUser().setEmail(emailEdit.getText().toString());
                    db.getCurrentUser().setPhone(Long.parseLong(phoneEdit.getText().toString()));
                    db.updateUser(db.getCurrentUser());
                    Intent updateIntent = new Intent(getApplicationContext(),UserActivity.class);
                    updateIntent.putExtra(TYPE, "My Profile");
                    startActivity(updateIntent);
                    setResult(RESULT_OK);
                }
            });
            cancelButton.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    Intent updateIntent = new Intent(getApplicationContext(),UserActivity.class);
                    updateIntent.putExtra(TYPE, "My Profile");
                    startActivity(updateIntent);
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
                usernameText.setText(db.getCurrentUser().getUsername());
                nameText.setText(db.getCurrentUser().getName());
                emailText.setText(db.getCurrentUser().getEmail());
                phoneText.setText(String.valueOf(db.getCurrentUser().getPhone()));
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
