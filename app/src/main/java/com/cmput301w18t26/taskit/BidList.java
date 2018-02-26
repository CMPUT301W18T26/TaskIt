package com.cmput301w18t26.taskit;

import java.util.ArrayList;

/**
 * Created by kevingordon on 2018-02-26.
 */

public class BidList {
    private ArrayList<Bid> bids = new ArrayList<Bid>();
    public void addBid(Bid bid) {
    }

    public boolean hasBid(Bid bid) {
        return true;
    }

    public Bid getBid(int index) {
        return bids.get(index);
    }

    public void deleteBid(Bid bid) {
        bids.remove(bid);
    }

    public int getBidCount() {
        return 0;
    }
}
