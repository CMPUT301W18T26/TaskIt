package com.cmput301w18t26.taskit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by kevingordon on 2018-02-26.
 */

public class TaskActivity extends AppCompatActivity {

    protected static final String TYPE = "type";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String type = intent.getStringExtra(HomeActivity.TYPE);
        if (type.equals("New Task")) {
            setContentView(R.layout.edittask);
            Button createTaskButton = (Button) findViewById(R.id.createtask);
            createTaskButton.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    Intent createTaskIntent = new Intent(getApplicationContext(),ListActivity.class);
                    createTaskIntent.putExtra(TYPE, "Requested Tasks");
                    startActivity(createTaskIntent);
                    setResult(RESULT_OK);
                }
            });
        } else {
            setContentView(R.layout.viewtask);
        }
        setTitle(type);
    }
}
