/*
 * Copyright 2018, Team 26 CMPUT 301. University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under the terms and coditions fo the Code of Student Behaviour at the University of Alberta.
 */

package com.cmput301w18t26.taskit.ModelTests;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.test.ActivityInstrumentationTestCase2;

import com.cmput301w18t26.taskit.Bid;
import com.cmput301w18t26.taskit.BidActivity;
import com.cmput301w18t26.taskit.LoginActivity;
import com.cmput301w18t26.taskit.Photo;
import com.cmput301w18t26.taskit.User;

import java.util.Date;

/**
 * Created by kevingordon on 2018-02-26.
 */

public class PhotoTest extends ActivityInstrumentationTestCase2 {
    public PhotoTest() {
        super(LoginActivity.class);
    }

    // test setting and getting date from Bid object
    public void testSetGetTimeStamp() {
        Photo photo = new Photo();
        Date date = new Date();
        photo.setTimestamp(date);
        assertEquals(date, photo.getTimestamp());
    }

    // get user associated with Bid
    public void testSetGetUUID() {
        Photo photo = new Photo();
        User user = new User();
        photo.setUUID(user.getUUID());
        assertEquals(user.getUUID(), photo.getUUID());
    }

    // test getting the bid amount, should return a double value > 0
    public void testSetGetOwner() {
        Photo photo = new Photo();
        User user = new User();
        photo.setOwner(user.getOwner());
        assertEquals(user.getOwner(), photo.getOwner());
    }

    // test method for returning the status of the Bid
    public void testSetGetPhoto() {
        Photo photo = new Photo();
        assertFalse(photo.getPhoto() != null);
        byte[] ba = {0,0,1,6,4,5,6,3,3,6,7,4,3,2,6,3,};
        Bitmap b = BitmapFactory.decodeByteArray(ba, 0, ba.length);
        photo.setPhoto(b);
        assertTrue(photo.getPhoto() != null);
    }
}
