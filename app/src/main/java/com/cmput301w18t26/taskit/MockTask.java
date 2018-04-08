/*
 * Copyright 2018, Team 26 CMPUT 301. University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under the terms and coditions fo the Code of Student Behaviour at the University of Alberta.
 */

package com.cmput301w18t26.taskit;

import java.util.Date;
import java.util.UUID;
/**
 * Created by kevingordon on 2018-03-04.
 */

/**
 * This class is created to use for testing, creates a task not manually created by a user
 */
public class MockTask extends Task {
    public MockTask() {
        setUUID(UUID.randomUUID().toString());
        setDate(new Date());
        setUser("AliceBob");
        setDescription("A very lovely mock task");
        setStatus("Requested");
        setTitle("Super great Task right here!");
    }

    public MockTask(String owner) {
        setUUID(UUID.randomUUID().toString());
        setDate(new Date());
        setUser(owner);
        setDescription("A very lovely mock task");
        setStatus("Requested");
        setOwner(owner);
    }

    public MockTask(String owner, String title) {
        setUUID(UUID.randomUUID().toString());
        setDate(new Date());
        setUser(owner);
        setDescription("A very lovely mock task");
        setStatus("Requested");
        setTitle(title);
        setOwner(owner);
    }
}
