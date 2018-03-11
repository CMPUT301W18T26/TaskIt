package com.cmput301w18t26.taskit;

import java.util.Date;

/**
 * Created by kevingordon on 2018-02-26.
 */

public class Bid {
    private Date date;
    private String user;
    private double amount;
    private String status;
    private String owner;
    // metadata for server/sync
    private String UUID;
    private Date timestamp;

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

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
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
    public boolean isOwner(User u) {
        return owner.equals(u.getUsername());
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
