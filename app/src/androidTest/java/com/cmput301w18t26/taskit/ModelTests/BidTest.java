/*
 * Copyright 2018, Team 26 CMPUT 301. University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under the terms and coditions fo the Code of Student Behaviour at the University of Alberta.
 */

package com.cmput301w18t26.taskit.ModelTests;

import android.test.ActivityInstrumentationTestCase2;

import com.cmput301w18t26.taskit.Bid;
import com.cmput301w18t26.taskit.BidActivity;

import java.util.Date;

/**
 * Created by kevingordon on 2018-02-26.
 */

public class BidTest extends ActivityInstrumentationTestCase2 {
    public BidTest() {
        super(BidActivity.class);
    }

    // test setting and getting date from Bid object
    public void testSetGetDate() {
        Bid bid = new Bid();
        Date date = new Date();
        bid.setDate(date);
        assertEquals(date, bid.getDate());
    }

    // get user associated with Bid
    public void testSetGetUser() {
        Bid bid = new Bid();
        String user = "AliceBob";
        bid.setOwner(user);
        assertEquals(user, bid.getOwner());
    }

    // test getting the bid amount, should return a double value > 0
    public void testSetGetAmount() {
        Bid bid = new Bid();
        double amount = 3.50;
        bid.setAmount(amount);
        assertEquals(amount, bid.getAmount());
    }

    // test method for returning the status of the Bid
    public void testSetGetStatus() {
        Bid bid = new Bid();
        String status = "BID";
        bid.setStatus(status);
        assertEquals(status, bid.getStatus());
    }
}
