/*
 * Copyright 2018, Team 26 CMPUT 301. University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under the terms and coditions fo the Code of Student Behaviour at the University of Alberta.
 */

package com.cmput301w18t26.taskit;


//package com.imageviewexample;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class PhotoViewActivity extends Activity implements OnClickListener {

    /** Called when the activity is first created. */

    int image_index = 0;
    TaskItData db;
    PhotoList pl;
    private int MAX_IMAGE_COUNT;
    private String UUID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photoviewer);

        db = TaskItData.getInstance();
        UUID = getIntent().getStringExtra("UUID");
        pl = db.getTaskPhotos(UUID);
        Log.d("PhotoViewActivity","There are " +Integer.toString(pl.getPhotoCount()) + " Photos");
        MAX_IMAGE_COUNT = pl.getPhotoCount();

        Button btnPrevious = (Button)findViewById(R.id.previous_btn);
        btnPrevious.setOnClickListener(this);
        Button btnNext = (Button)findViewById(R.id.next_btn);
        btnNext.setOnClickListener(this);

        if (MAX_IMAGE_COUNT==1) {
            btnNext.setVisibility(View.GONE);
            btnPrevious.setVisibility(View.GONE);
        }

        showImage();

    }

    private void showImage() {

        ImageView imgView = (ImageView) findViewById(R.id.myimage);

        imgView.setImageBitmap(pl.getPhoto(image_index).getPhoto());


    }

    public void onClick(View v) {

        switch (v.getId()) {

            case (R.id.previous_btn):

                image_index--;

                if (image_index == -1) {
                    image_index = MAX_IMAGE_COUNT - 1;
                }

                showImage();

                break;

            case (R.id.next_btn):

                image_index++;

                if (image_index == MAX_IMAGE_COUNT) {
                    image_index = 0;
                }

                showImage();

                break;

        }

    }
}
