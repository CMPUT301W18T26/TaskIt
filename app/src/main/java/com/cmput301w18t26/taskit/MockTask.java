package com.cmput301w18t26.taskit;

import java.util.Date;

/**
 * Created by kevingordon on 2018-03-04.
 */

public class MockTask extends Task {
    public MockTask() {
        setID(0);
        setDate(new Date());
        setUser("AliceBob");
        setDescription("A very lovely mock task");
        setLocation("Quad");
        setBids(new BidList());
        setStatus("Requested");
        setTitle("Super great Task right here!");
    }
}
