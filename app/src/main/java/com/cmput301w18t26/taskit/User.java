package com.cmput301w18t26.taskit;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by kevingordon on 2018-02-26.
 */

/**
 * Represents a single User.
 * @author UAlberta-Cmput301-Team26 crew
 * @see TaskList
 */
public class User {
    /**
     * User's name.
     */
    private String name;

    /**
     * User's email address.
     */
    private String email;

    /**
     * User's phone number.
     */
    private long phone;

    /**
     * User's unique username.
     */
    private String username;

    /**
     * Array of rankings for this user.
     */
    private int[] ranks;

    private Date date;

    /**
     * Owner of this user.
     */
    private String owner;

    /**
     * Used for sync. UUID of this user
     */
    private String UUID;

    /**
     * Used for sync. Timestamp user created/edited
     */
    private Date timestamp;

    private ArrayList<Float> ratings = new ArrayList<>();

    private ArrayList<String> ratingDescriptions = new ArrayList<>();

    public ArrayList<String> getRatingDescriptions() {
        return ratingDescriptions;
    }

    public void addRatingDescription(String ratingDescription) {
        ratingDescriptions.add(ratingDescription);
    }

    public float getRatingsAverage() {
        float sum = 0;
        if(!ratings.isEmpty()) {
            for (Float rating : ratings) {
                sum += rating;
            }
            return sum / ratings.size();
        }
        return sum;
    }

    public void addRating(float rating) {
        ratings.add(rating);
    }

    public ArrayList<Float> getRatings() { return ratings; }

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

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public long getPhone() {
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

    /**
     * Ranks are array of integers, compute the average.
     *
     * @return the average of user's rankings
     */
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
}
