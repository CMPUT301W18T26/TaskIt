package com.cmput301w18t26.taskit;

import android.test.ActivityInstrumentationTestCase2;

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

    public void testAddBid() {
        BidList bids = new BidList();
        Bid bid = new Bid();
        bids.addBid(bid);

        assertTrue(bids.hasBid(bid));
    }

    public void testHasBid() {
        BidList bids = new BidList();
        Bid bid = new Bid();
        assertFalse(bids.hasBid(bid));
        bids.addBid(bid);
        assertTrue(bids.hasBid(bid));
    }

    public void testGetBid() {
        BidList bids = new BidList();
        Bid bid = new Bid();
        bids.addBid(bid);
        Bid returnedBid = bids.getBid(0);
        assertEquals(bid.getID(), returnedBid.getID());
    }


    public void testDeleteBid() {
        BidList bids = new BidList();
        Bid bid = new Bid();
        bids.addBid(bid);
        bids.deleteBid(bid);
        assertFalse(bids.hasBid(bid));
    }

    public void testGetBidCount() {
        BidList bids = new BidList();
        assertEquals(0, bids.getBidCount());
        Bid bid1 = new Bid();
        bids.addBid(bid1);
        assertEquals(1, bids.getBidCount());
        Bid bid2 = new Bid();
        bids.addBid(bid2);
        assertEquals(2, bids.getBidCount());
        Bid bid3 = new Bid();
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