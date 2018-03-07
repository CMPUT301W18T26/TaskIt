package com.cmput301w18t26.taskit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * Created by kevingordon on 2018-02-26.
 */

public class ListActivity extends AppCompatActivity {

    private ListView bidlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listtask);
        Intent intent = getIntent();
        String type = intent.getStringExtra(HomeActivity.TYPE);
        setTitle(type);

        bidlist = (ListView) findViewById(R.id.tasklist);

        bidlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
            }
        });

    }
}
