package com.cmput301w18t26.taskit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.AdapterView;
import android.widget.ListView;
/**
 * Created by kevingordon on 2018-02-26.
 */

public class ListActivity extends AppCompatActivity {

    protected static final String TYPE = "type";

    private ListView bidlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listtask);
        Intent intent = getIntent();
        String type = intent.getStringExtra(HomeActivity.TYPE);
        setTitle(type);

        Button newTaskButton = (Button) findViewById(R.id.newtask);
        if (!type.equals("Requested Tasks")) {
            newTaskButton.setVisibility(View.GONE);
        } else {
            //code that apply's to requested task list
        }
        newTaskButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent newTaskIntent = new Intent(getApplicationContext(),TaskActivity.class);
                newTaskIntent.putExtra(TYPE, "New Task");
                startActivity(newTaskIntent);
                setResult(RESULT_OK);
            }
        });


        bidlist = (ListView) findViewById(R.id.tasklist);

        bidlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
            }
        });


    }

}
