package com.cmput301w18t26.taskit;

/**
 * Created by kevingordon on 2018-03-04.
 */

public class MockUser extends User {
    public MockUser(String username) {
        setName("Alice Bob");
        setEmail("AliceBob@charlie.com");
        setPhone(1234567890);
        setUsername(username);
        int[] ranks = {1, 2, 3, 4, 5};
        setRanks(ranks);
    }
}
