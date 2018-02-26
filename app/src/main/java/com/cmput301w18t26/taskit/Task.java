package com.cmput301w18t26.taskit;

import java.util.Date;

/**
 * Created by kevingordon on 2018-02-26.
 */

public class Task {
    private int ID;
    private Date date;
    private String user;
    private String description;
    private String location;
    // private image[] photos;
    private BidList bids;
    private String status;
    private String Title;


    public long getID() {return ID;}
}
