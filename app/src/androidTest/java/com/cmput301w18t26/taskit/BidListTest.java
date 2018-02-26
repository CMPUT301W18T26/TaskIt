package com.cmput301w18t26.taskit;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by kevingordon on 2018-02-26.
 */

public class BidListTest extends ActivityInstrumentationTestCase2 {
    public BidListTest() {
        super(BidActivity.class);
    }

    public void testAddBid() {
        BidList bids = new BidList();
        Bid bid = new Bid();
        bids.add(bid);

        assertTrue(bids.hasBid(bid));
    }

    public void testHasBid() {
        BidList bids = new BidList();
        Bid bid = new Bid();
        assertFalse(bids.hasBid(bid));
        bids.add(bid);
        assertTrue(bids.hasBid(bid));
    }

    public void testGetBid() {
        BidList bids = new BidList();
        Bid bid = new Bid();
        bids.add(bid);
        Bid returnedBid = bids.getBid(0);
        assertEquals(bid.getID(), returnedBid.getID());
    }


    public void testDeleteBid() {
        BidList bids = new BidList();
        Bid bid = new Bid();
        bids.add(bid);
        bids.deleteBid(bid);
        assertFalse(bids.hasBid(bid));
    }
}