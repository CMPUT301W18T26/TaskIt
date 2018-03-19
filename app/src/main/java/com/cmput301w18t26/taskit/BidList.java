package com.cmput301w18t26.taskit;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by kevingordon on 2018-02-26.
 */

/**
 * Represents a list of bids.
 * @author UAlberta-Cmput301-Team26 crew
 * @see Bid
 */
public class BidList {

    /**
     * List of bids.
     */
    private ArrayList<Bid> bids = new ArrayList<Bid>();

    /**
     * Some services (adapters in particular) need a list type.
     * @return the bare arraylist of bids.
     */
    public ArrayList<Bid> getBids() {return bids;}

    public void addBid(Bid bid) {
        bids.add(bid);
    }

    public boolean hasBid(Bid bid) {
        if (getIndex(bid) > -1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Find bids in bidlist using their UUIDs
     * @param bid the bid to check for membership.
     * @return index of bid if found, -1 o.w.
     */
    public int getIndex(Bid bid) {
        for (int i=0; i<bids.size(); i++) {
            if (getBid(i).getUUID().equals(bid.getUUID())) {
                return i;
            }
        }
        return -1;
    }

    public Bid getBid(int index) {
        return bids.get(index);
    }

    public Bid getBid(Bid bid) {
        return bids.get(getIndex(bid));
    }

    public void deleteBid(Bid bid) {
        int index = getIndex(bid);
        if (index > -1) {
            bids.remove(index);
        }
    }

    public int getBidCount() {
        return bids.size();
    }

    public void addAll(Collection<Bid> l) {
        bids.addAll(l);
    }

    public void addAll(BidList l) {
        bids.addAll(l.getBids());
    }

    public void clear() {
        bids.clear();
    }
}
