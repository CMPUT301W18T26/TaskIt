package com.cmput301w18t26.taskit;

import java.util.Date;

/**
 * Created by kevingordon on 2018-02-26.
 */

public class Bid {
    private Date date;
    private double amount;
    private String status;
    private String owner;
    private String parentTask;
    // metadata for server/sync
    private String UUID;
    private Date timestamp;

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
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

    public boolean isParentTask(Task t) {
        return parentTask.equals(t.getUUID());
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

    public String getParentTask() {
        return parentTask;
    }

    public void setParentTask(String parentTask) {
        this.parentTask = parentTask;
    }

    public void setParentTask(Task parentTask) {
        this.parentTask = parentTask.getUUID();
    }
}
