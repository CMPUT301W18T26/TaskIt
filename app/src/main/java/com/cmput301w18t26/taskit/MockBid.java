package com.cmput301w18t26.taskit;

import java.util.Date;

/**
 * Created by kevingordon on 2018-03-04.
 */

public class MockBid extends Bid {
    public MockBid() {
        setID(0);
        setDate(new Date());
        setUser("AliceBob");
        setAmount(3.50);
        setStatus("BID");
    }
}
