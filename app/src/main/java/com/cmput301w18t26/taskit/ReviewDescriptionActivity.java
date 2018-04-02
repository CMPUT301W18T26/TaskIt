package com.cmput301w18t26.taskit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by colin on 2018-04-01.
 */

public class ReviewDescriptionActivity extends AppCompatActivity {

    private ListView listOfReviewDescriptions;
    private ArrayList<String> reviewList = new ArrayList<>();
    private ArrayList<String> emptyReview = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private TaskItData db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String type = "Review Descriptions";
        //setTitle(type);
        setTitle(Html.fromHtml("<font color=#ffffff>" + type + "</font>"));
        db = TaskItData.getInstance();
        setContentView(R.layout.review_descriptions);
        listOfReviewDescriptions = (ListView) findViewById(R.id.reviewlist);

    }
    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        reviewList = db.getCurrentUser().getRatingDescriptions();

        if (reviewList.size() == 0) {
            emptyReview.add("There are currently no written reviews for this user.");
            adapter = new ArrayAdapter<String>(this, R.layout.review_item, emptyReview);
        } else {
            adapter = new ArrayAdapter<String>(this, R.layout.review_item, reviewList);
        }

        listOfReviewDescriptions.setAdapter(adapter);
    }
}

