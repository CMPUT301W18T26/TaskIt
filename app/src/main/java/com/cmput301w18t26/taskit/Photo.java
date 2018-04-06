package com.cmput301w18t26.taskit;

import android.graphics.Bitmap;


import java.util.Date;

/**
 * Represents a single Photo.
 * @author UAlberta-Cmput301-Team26 crew
 * @see PhotoList
 */
public class Photo {

    private Bitmap photo;

    /**
     * The UUID of the task this bid was placed on.
     */
    private String parentTask;

    /**
     * Metadata for sync. UUID for task.
     */
    private String UUID;

    /**
     * Metadata for sync. Timestamp task created/updated.
     */
    private Date timestamp;

    /**
     * Task owner username.
     */
    private String owner;

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

    /**
     * Check if a given task is the parent of this bid.
     * Compare using unique UUID.
     * @param t the task to check if is parent.
     * @return true if given task is the parent task, false o.w.
     */
    public boolean isParentTask(Task t) {
        return parentTask.equals(t.getUUID());
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
