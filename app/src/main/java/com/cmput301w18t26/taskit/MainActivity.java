/*
 * Copyright 2018, Team 26 CMPUT 301. University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under the terms and coditions fo the Code of Student Behaviour at the University of Alberta.
 */

package com.cmput301w18t26.taskit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by haozhengqin on 2018/3/10.
 */

public class MainActivity extends AppCompatActivity{
//    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
//        super.onCreate(saveInstanceState);
//        setContentView(R.layout.activity_main);
//        ListView lv = (ListView)findViewById(R.id.ListViewCountry);
//        ArrayList<String> arrayCountry =new ArrayList<>();
//        arrayCountry.addAll(Arrays.asList(getResources().getStringArray(R.array.array_country)));
//
//        adapter = new ArrayAdapter<>(
//                MainActivity.this,
//                android.R.layout.simple_list_item_1,
//                arrayCountry);
//        lv.setAdapter(adapter);
    }
//
//    @Override
//
//    public boolean onCreateOptionMenu(Menu menu){
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_search,menu);
//        MenuItem item = menu.findItem(R.id.menu_search);
//        SearchView searchView = (SearchView)item.getActionView();
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//
//                adapter.getFilter().filter(newText);
//
//                return false;
//            }
//        });
//        return super.onCreateOptionsMenu(menu);
//
//
//
//
//
//
//    }
}
