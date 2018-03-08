package com.cmput301w18t26.taskit;

import java.util.Date;
import java.util.UUID;
/**
 * Created by kevingordon on 2018-03-04.
 */

public class MockTask extends Task {
    public MockTask() {
        setUUID(UUID.randomUUID().toString());
        setDate(new Date());
        setUser("AliceBob");
        setDescription("A very lovely mock task");
        setLocation("Quad");
        setBids(new BidList());
        setStatus("Requested");
        setTitle("Super great Task right here!");
    }

    public MockTask(String owner, String title) {
        setUUID(UUID.randomUUID().toString());
        setDate(new Date());
        setUser(owner);
        setDescription("A very lovely mock task");
        setLocation("Edmonton");
        setBids(new BidList());
        setStatus("Requested");
        setTitle(title);
        setOwner(owner);
    }
}
