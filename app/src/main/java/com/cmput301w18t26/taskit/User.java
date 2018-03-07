package com.cmput301w18t26.taskit;

import java.util.Date;

/**
 * Created by kevingordon on 2018-02-26.
 */

public class User {
    private String name;
    private String email;
    private int phone;
    private String username;
    private int[] ranks;
    private TaskList tasks;
    private BidList bids;
    private Date date;
    private String owner;
    // metadata for server/sync
    private String UUID;
    private Date timestamp;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public int getPhone() {
        return phone;
    }

    public void setUsername(String username) {
        this.username = username;
        this.owner = username;
    }

    public String getUsername() {
        return username;
    }

    public void setRanks(int[] ranks) {
        this.ranks = ranks;
    }

    public int[] getRanks() {
        return ranks;
    }

    public void setTasks(TaskList tasks) {
        this.tasks = tasks;
    }

    public TaskList getTasks() {
        return tasks;
    }

    public void setBids(BidList bids) {
        this.bids = bids;
    }

    public BidList getBids() {
        return bids;
    }

    public double getRank() {
        // Return -1 if no ranks
        double sum = 0;
        if(ranks.length>0) {
            for (int i=0; i<ranks.length; i++) {
                sum += ranks[i];
            }
            return sum / ranks.length;
        }
        return -1;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
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
    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
