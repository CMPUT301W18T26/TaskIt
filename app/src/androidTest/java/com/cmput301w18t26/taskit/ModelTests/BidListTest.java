/*
 * Copyright 2018, Team 26 CMPUT 301. University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under the terms and coditions fo the Code of Student Behaviour at the University of Alberta.
 */

package com.cmput301w18t26.taskit.ModelTests;

import android.test.ActivityInstrumentationTestCase2;

import com.cmput301w18t26.taskit.Bid;
import com.cmput301w18t26.taskit.BidActivity;
import com.cmput301w18t26.taskit.BidList;
import com.cmput301w18t26.taskit.MockBid;

/**
 * Created by kevingordon on 2018-02-26.
 */

public class BidListTest extends ActivityInstrumentationTestCase2 {
    public BidListTest() {
        super(BidActivity.class);
    }

    public void testTest() {
        assertTrue(true);
    }

    //test adding a bid to BidList
    public void testAddBid() {
        BidList bids = new BidList();
        Bid bid = new MockBid();
        bids.addBid(bid);

        assertTrue(bids.hasBid(bid));
    }

    // test hasBid method, which returns a boolean
    public void testHasBid() {
        BidList bids = new BidList();
        Bid bid = new MockBid();
        assertFalse(bids.hasBid(bid));
        bids.addBid(bid);
        assertTrue(bids.hasBid(bid));
    }

    // test getBid method, should return Bid object associated with index or UUID
    public void testGetBid() {
        BidList bids = new BidList();
        Bid bid = new MockBid();
        bids.addBid(bid);
        Bid returnedBid = bids.getBid(0);
        assertEquals(bid.getUUID(), returnedBid.getUUID());
    }

    // tests deleting bid from BidList
    public void testDeleteBid() {
        BidList bids = new BidList();
        Bid bid = new MockBid();
        bids.addBid(bid);
        bids.deleteBid(bid);
        assertFalse(bids.hasBid(bid));
    }

    // count number of Bids in BidList object
    public void testGetBidCount() {
        BidList bids = new BidList();
        assertEquals(0, bids.getBidCount());
        Bid bid1 = new MockBid();
        bids.addBid(bid1);
        assertEquals(1, bids.getBidCount());
        Bid bid2 = new MockBid();
        bids.addBid(bid2);
        assertEquals(2, bids.getBidCount());
        Bid bid3 = new MockBid();
        bids.addBid(bid3);
        assertEquals(3, bids.getBidCount());
        bids.deleteBid(bid3);
        assertEquals(2, bids.getBidCount());
        bids.deleteBid(bid2);
        assertEquals(1, bids.getBidCount());
        bids.deleteBid(bid1);
        assertEquals(0, bids.getBidCount());
    }
}