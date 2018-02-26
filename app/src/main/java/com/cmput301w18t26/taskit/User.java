package com.cmput301w18t26.taskit;

/**
 * Created by kevingordon on 2018-02-26.
 */

public class User {
    private int ID;
    private String Name;
    private String email;
    private int phone;
    private String username;
    private int[] ranks;
    private TaskList tasks;
    private BidList bids;

    public long getID() {return ID;}
}
