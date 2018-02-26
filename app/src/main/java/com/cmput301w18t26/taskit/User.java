package com.cmput301w18t26.taskit;

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

    public int getRank() {
        // Return -1 if no ranks
        return 0;
    }
}
