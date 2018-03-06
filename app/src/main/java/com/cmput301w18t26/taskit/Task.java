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
    private String title;
    private String owner;
    private String UUID;

    public long getID() {return ID;}

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setBids(BidList bids) {
        this.bids = bids;
    }

    public BidList getBids() {
        return bids;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setOwner(String o) {
        owner = o;
    }
    public String getOwner() {
        return owner;
    }
    public boolean isOwner(String s) {
        return owner.equals(s);
    }

    public String getUUID() {
        return UUID;
    }
}
