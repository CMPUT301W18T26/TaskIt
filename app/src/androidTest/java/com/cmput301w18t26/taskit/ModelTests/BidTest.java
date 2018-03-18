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

    public void testSetGetDate() {
        Bid bid = new Bid();
        Date date = new Date();
        bid.setDate(date);
        assertEquals(date, bid.getDate());
    }
    public void testSetGetUser() {
        Bid bid = new Bid();
        String user = "AliceBob";
        bid.setOwner(user);
        assertEquals(user, bid.getOwner());
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
