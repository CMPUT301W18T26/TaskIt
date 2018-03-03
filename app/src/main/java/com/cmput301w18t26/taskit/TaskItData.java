package com.cmput301w18t26.taskit;

/**
 * Created by kevingordon on 2018-03-03.
 */

import java.util.ArrayList;

/**
 * TaskItData will hold all the data for the entire application.
 *
 * It will push/pull from the filesystem
 *
 */
public class TaskItData {
    private TaskList tasks;
    private UserList users;
    private BidList bids;

    public void addUser(User user) {
        // Add the user to users
        // Add user to filesystem
        // Request sync?
    }

    // Q: should we delete by object? or like.. an ID?
    public void deleteUser(User user) {
        // Delete user from users
        // Move user to trash (filesystem)
        // Request sync?
    }

    public void addTask(Task task) {
        // Add the task to tasks
        // Add the task to filesystem
        // Request sync?
    }

    // Q: should we delete by object? or like.. an ID?
    public void deleteTask(Task task) {
        // Delete task from tasks
        // Move task to trash (filesystem)
        // Request sync?
    }

    public void addBid(Bid bid) {
        // Add the bid to bids
        // Add the bid to filesystem
        // Request sync?
    }

    // Q: should we delete by object? or like.. an ID?
    public void deleteBid(Bid bid) {
        // Delete bid from bids
        // Move bid to trash (filesystem)
        // Request sync?
    }


    /**
     * Get a list of tasks for a specific user.
     *
     * @param user
     * @return
     */
    public TaskList userTasks(User user){
        return new TaskList();
    };

    /**
     * Get a list of bids for a specific task.
     *
     * @param task
     * @return
     */
    public TaskList taskBids(Task task){
        return new TaskList();
    };




}
