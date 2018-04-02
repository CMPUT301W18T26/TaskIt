package com.cmput301w18t26.taskit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Created by kevingordon on 2018-02-26.
 */

/**
 * Creates a new user or modifies user details.
 * Also allows user to view their profile once registered depending on the intent passed in
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
        //setTitle(type);
        setTitle(Html.fromHtml("<font color=#ffffff>" + type + "</font>"));
        db = TaskItData.getInstance();

        /**
         * Registers a new user, takes in the user inputted information
         * Allows user to register if the username doesn't already exist in database
         * Creates new User object
         */
        if (type.equals("Register")) {
            setContentView(R.layout.registeruser);
            usernameEdit = (EditText) findViewById(R.id.username1);
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
                        setResult(RESULT_OK);
                        finish();
                    }
                }
            });
            cancelButton.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    finish();
                    setResult(RESULT_OK);
                }
            });

            /**
             * Retrieves user details to update, and sets the text displayed to current details
             * so user can modify them.
              */
        } else if (type.equals("Update")) {
            setContentView(R.layout.edituser);
            Button actionButton = (Button) findViewById(R.id.confirmuser);
            Button cancelButton = (Button) findViewById(R.id.cancel);
            usernameText = (TextView) findViewById(R.id.username1);
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
                    setResult(RESULT_OK);
                    finish();
                }
            });
            cancelButton.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    setResult(RESULT_OK);
                    finish();
                }
            });
        } else {
            setContentView(R.layout.viewuser);
            Button editButton = (Button) findViewById(R.id.edit);
            usernameText = (TextView) findViewById(R.id.update_username);
            nameText = (TextView) findViewById(R.id.update_name);
            emailText = (TextView) findViewById(R.id.update_email);
            phoneText = (TextView) findViewById(R.id.update_phone);
            RatingBar userRating = (RatingBar) findViewById(R.id.userrating);
            TextView reviewCount = (TextView) findViewById(R.id.reviewcount);

            reviewCount.setText(Integer.toString(db.getCurrentUser().getRatings().size()));
            userRating.setRating(db.getCurrentUser().getRatingsAverage());

            userRating.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        Intent reviewDescriptionIntent = new Intent(getApplicationContext(), ReviewDescriptionActivity.class);
                        startActivity(reviewDescriptionIntent);
                        setResult(RESULT_OK);
                    }
                    return true;
                }
            });

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
                    Intent updateIntent = new Intent(getApplicationContext(), UserActivity.class);
                    updateIntent.putExtra(TYPE, "Update");
                    startActivity(updateIntent);
                    setResult(RESULT_OK);
                }
            });
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        String type = intent.getStringExtra(HomeActivity.TYPE);
        if (type.equals("My Profile")) {
            usernameText.setText(db.getCurrentUser().getUsername());
            nameText.setText(db.getCurrentUser().getName());
            emailText.setText(db.getCurrentUser().getEmail());
            phoneText.setText(String.valueOf(db.getCurrentUser().getPhone()));
        }
    }
}
