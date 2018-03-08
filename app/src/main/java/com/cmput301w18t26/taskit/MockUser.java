package com.cmput301w18t26.taskit;
import java.util.UUID;

/**
 * Created by kevingordon on 2018-03-04.
 */

public class MockUser extends User {
    public MockUser() {
        setUUID(UUID.randomUUID().toString());
        setName("Alice Bob");
        setEmail("AliceBob@charlie.com");
        setPhone(1234567890);
        setUsername("alicebob");
        int[] ranks = {1, 2, 3, 4, 5};
        setRanks(ranks);
    }
    public MockUser(String username) {
        setUUID(UUID.randomUUID().toString());
        setName(username);
        setEmail(username+"@charlie.com");
        setPhone(1234567890);
        setUsername(username);
        int[] ranks = {1, 2, 3, 4, 5};
        setRanks(ranks);
    }
}
