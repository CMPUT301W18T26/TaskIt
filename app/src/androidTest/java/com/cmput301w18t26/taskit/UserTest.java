package com.cmput301w18t26.taskit;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by kevingordon on 2018-02-26.
 */

public class UserTest extends ActivityInstrumentationTestCase2 {
    public UserTest() {
        super(UserActivity.class);
    }

    public void testSetGetName() {
        User user = new User();
        String name = "AliceBob";
        user.setName(name);
        assertEquals(name, user.getName());
    }

    public void testSetGetEmail() {
        User user = new User();
        String email = "AliceBob@charlie.com";
        user.setEmail(email);
        assertEquals(email, user.getEmail());
    }

    public void testSetGetPhone() {
        User user = new User();
        int phone = 1234567890;
        user.setPhone(phone);
        assertEquals(phone, user.getPhone());
    }

    public void testSetGetUserName() {
        User user = new User();
        String username = "AliceBob";
        user.setUsername(username);
        assertEquals(username, user.getUsername());
    }

    public void testSetGetRanks() {
        User user = new User();
        int[] ranks = {1,2,3,4,5};
        user.setRanks(ranks);
        assertEquals(ranks, user.getRanks());
    }

    public void testSetGetTasks() {
        User user = new User();
        TaskList tasks = new TaskList();
        user.setTasks(tasks);
        assertEquals(tasks, user.getTasks());
    }

    public void testSetGetBids() {
        User user = new User();
        BidList bids = new BidList();
        user.setBids(bids);
        assertEquals(bids, user.getBids());
    }

}
