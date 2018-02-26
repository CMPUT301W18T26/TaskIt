package com.cmput301w18t26.taskit;

import android.test.ActivityInstrumentationTestCase2;

import java.util.Date;

/**
 * Created by kevingordon on 2018-02-26.
 */

public class BidTest extends ActivityInstrumentationTestCase2 {
    public BidTest() {
        super(BidActivity.class);
    }

    public void testSetGetID() {
        Bid bid = new Bid();
        bid.setID(0);
        assertEquals(0, bid.getID());
    }
    public void testSetGetDate() {
        Bid bid = new Bid();
        Date date = new Date();
        bid.setDate(date);
        assertEquals(date, bid.getDate());
    }
    public void testSetGetUser() {
        Bid bid = new Bid();
        String user = "AliceBob";
        bid.setUser(user);
        assertEquals(user, bid.getUser());
    }
    public void testSetGetAmount() {
        Bid bid = new Bid();
        double amount = 3.50;
        bid.setAmount(amount);
        assertEquals(amount, bid.getAmount());
    }
    public void testSetGetStatus() {
        Bid bid = new Bid();
        String status = "BID";
        bid.setStatus(status);
        assertEquals(status, bid.getStatus());
    }
}
