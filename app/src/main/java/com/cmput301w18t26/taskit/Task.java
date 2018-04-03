package com.cmput301w18t26.taskit;

import android.location.Location;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kevingordon on 2018-02-26.
 */

/**
 * Represents a single task.
 * @author UAlberta-Cmput301-Team26 crew
 * @see TaskList
 */
public class Task {
    private Date date;

    /**
     * Username of the task requester
     */
    private String user;

    /**
     * Description of this task
     */
    private String description;

    /**
     * Location of this task.
     */
    private Location location;
    // private image[] photos;

    /**
     * Task status. {"Bidded","Requested","Assigned","Done"}
     */
    private String status;

    /**
     * Task title
     */
    private String title;

    /**
     * Task owner username.
     */
    private String owner;

    /**
     * Username of user task is assigned to.
     */
    private String assignee;

    /**
     * Metadata for sync. UUID for task.
     */
    private String UUID;

    /**
     * Metadata for sync. Timestamp task created/updated.
     */
    private Date timestamp;

    public static String[] statuses = {"Bidded","Requested","Assigned","Done"};
    public static String[] changeableStatuses = {"My Requests","I have bids","I've assigned","It's done"};

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

    public void setLocation(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
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
        this.owner = o;
    }

    public void setOwner(User u) {this.owner = u.getOwner();}

    public String getOwner() {
        return this.owner;
    }

    public boolean isOwner(String s) {
        return this.owner.equals(s);
    }

    public boolean isOwner(User u) {
        return this.owner.equals(u.getOwner());
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
        return hasAssignee() && this.assignee.equals(s);
    }

    public boolean isAssignee(User u) {
        return isAssignee(u.getUsername());
    }

    public boolean hasAssignee() {
        return this.assignee!=null;
    }

    public String locationString() {
        String s = "";
        s += "Lat: " + Double.toString(location.getLatitude());
        s += ", ";
        s += "Lon: " + Double.toString(location.getLongitude());
        return s;
    }

    public boolean hasLocation() {
        return location!=null;
    }

    public String toString() {
        String s = "";

        s += title;
        s += owner;
        s += date.toString();
        s += description;

        return s;
    }
}
