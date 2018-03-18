package com.cmput301w18t26.taskit;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kevingordon on 2018-02-26.
 */

public class Task {
    private Date date;
    private String user;
    private String description;
    private String location;
    // private image[] photos;
    private BidList bids;
    private String status;
    private String title;
    private String owner;

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }
    public void setAssignee(User assignee) {
        this.assignee = assignee.getUsername();
    }

    public boolean isAssignee(String s) {
        return this.assignee!=null && this.assignee.equals(s);
    }
    public boolean isAssignee(User u) {
        return isAssignee(u.getUsername());
    }

    private String assignee;
    // metadata for server/sync
    private String UUID;
    private Date timestamp;
    public static String[] statuses = {"Bidded","Requested","Accepted","Done"};
    public static String[] changeableStatuses = {"Requested","Accepted","Done"};

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
    public void setOwner(User u) {owner = u.getOwner();}
    public String getOwner() {
        return owner;
    }
    public boolean isOwner(String s) {
        return owner.equals(s);
    }
    public boolean isOwner(User u) {
        return owner.equals(u.getOwner());
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getDateString(){
        return new SimpleDateFormat("dd/MM/yyyy").format(date);
    }
}
