/*
 * Copyright 2018, Team 26 CMPUT 301. University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under the terms and coditions fo the Code of Student Behaviour at the University of Alberta.
 */

package com.cmput301w18t26.taskit;

import java.util.Date;

/**
 * Created by kevingordon on 2018-02-26.
 */

/**
 * Represents a single bid.
 * @author UAlberta-Cmput301-Team26 crew
 * @see BidList
 */
public class Bid {
    private Date date;
    /**
     * The dollar amount a provider has bid to perform a task
     */
    private double amount;

    /**
     * The bid can be accepted.
     */
    private String status = "Pending";

    /**
     * Username of the provider who created the bid.
     */
    private String owner;

    /**
     * The UUID of the task this bid was placed on.
     */
    private String parentTask;

    /**
     * Metadata for sync. The UUID of this bid.
     */
    private String UUID;
    /**
     * Metadata for sync. The timestamp when bid was created/updated.
     */
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

    /**
     * Check if a given user is the owner of this bid.
     * Compare using unique username.
     * @param u the user to check for bid ownership.
     * @return true if given user is the bid owner, false o.w.
     */
    public boolean isOwner(User u) {
        return owner.equals(u.getUsername());
    }

    /**
     * Check if a given task is the parent of this bid.
     * Compare using unique UUID.
     * @param t the task to check if is parent.
     * @return true if given task is the parent task, false o.w.
     */
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
