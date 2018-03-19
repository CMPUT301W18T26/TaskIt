package com.cmput301w18t26.taskit;

/**
 * Created by kevingordon on 2018-03-03.
 */

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.UUID;


/**
 * TaskItData will hold all the data for the entire application.
 *
 * - push/pull from the filesystem
 * - sync the server and the filesystem
 * - get filtered lists (refactor into filter class, call these)
 * - add/update/delete {tasks, users, bids}
 * -
 */

// Todo: cite https://www.geeksforgeeks.org/singleton-class-java/ in wiki
public class TaskItData {


    private static TaskItData single_instance = null;

    //  add/update/delete
    private TaskList tasks;
    private UserList users;
    private BidList bids;

    // the user currently logged in
    private User currentUser;

    // File i/o
    private TaskItFile fs;

    // Synchronize server and filesystem with sync
    public static TaskItSync sync;

    // Initialize a db
    private TaskItData() {
        this.users = new UserList();
        this.tasks = new TaskList();
        this.bids = new BidList();

        try {
            // This will fail if no context is set in the TaskItFile singleton
            this.fs = TaskItFile.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.sync = new TaskItSync();

        // Todo: but ok for now; o.w. some methods check against a null object
        User defaultUser = new User();
        defaultUser.setUsername("admin");
        setCurrentUser(defaultUser);

    }

    // A singleton
    public static TaskItData getInstance() {
        if (single_instance == null) {
            single_instance = new TaskItData();
        }
        return single_instance;
    }

    // Manage the current user
    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
        sync.setCurrentUser(currentUser.getUsername());
    }

    public User getUserByUsername(String username) {
        return users.getUserByUsername(username);
    }




    // This will append data from the filesystem to the {user, tasks, bids}Lists
    // Can result in duplicate data, usually clear the list prior
    // ... consider refactor or delete
    public void refresh() {
        fs.loadAllFromFile(users, tasks, bids);
    }

    /**
     * USER METHODS
     */
    public void addUser(User user) {
        // Add metadata for sync and retrieval
        user.setUUID(UUID.randomUUID().toString());
        user.setTimestamp(new Date());

        users.addUser(user);

        // Add user to filesystem
        fs.addUserFile(user);

        sync.sync();
    }

    public void deleteUser(User user) {
        users.deleteUser(user);

        // Delete from filesystem
        fs.deleteUserFile(user);

        sync.sync();
    }

    public void updateUser(User user) {
        // Update metadata
        user.setTimestamp(new Date());

        // Update by re-adding to the filesystem
        fs.addUserFile(user);

        sync.sync();
    }

    // Sometimes you need the raw list.
    // would be nice to have it immutable for consistency...
    public UserList getUsers() {
        return users;
    }

    /**
     * TASK METHODS
     */
    public void addTask(Task task) {
        // Add metadata for sync and retrieval
        task.setUUID(UUID.randomUUID().toString());
        task.setTimestamp(new Date());

        tasks.addTask(task);

        // Add the task to filesystem
        fs.addTaskFile(task);

        sync.sync();
    }

    public void deleteTask(Task task) {
        // Cascade delete all bids for this task
        for (Bid b: bids.getBids()) {
            if (b.isParentTask(task)) {
                deleteBid(b);
            }
        }

        tasks.deleteTask(task);

        // Delete from filesystem
        fs.deleteTaskFile(task);

        sync.sync();
    }

    public void updateTask(Task task) {
        // Update metadata
        task.setTimestamp(new Date());

        // Update by re-adding to the filesystem
        fs.addTaskFile(task);

        sync.sync();
    }

    // Sometimes you need the raw list.
    // would be nice to have it immutable for consistency...
    public TaskList getTasks() {
        return tasks;
    }

    /**
     * BID METHODS
     */
    public void addBid(Bid bid) {
        // Add metadata for sync and retrieval
        bid.setUUID(UUID.randomUUID().toString());
        bid.setTimestamp(new Date());

        bids.addBid(bid);

        // Add the bid to filesystem
        fs.addBidFile(bid);

        sync.sync();
    }

    public void deleteBid(Bid bid) {
        bids.deleteBid(bid);

        // Delete from filesystem
        fs.deleteBidFile(bid);

        sync.sync();
    }


    public void updateBid(Bid bid) {
        // Update metadata
        bid.setTimestamp(new Date());

        // Update by re-adding to the filesystem
        fs.addBidFile(bid);

        sync.sync();
    }


    // Sometimes you need the raw list.
    // would be nice to have it immutable for consistency...
    public BidList getBids() {
        return bids;
    }

    /**
     *   Filter functions
     *   Todo: refactor?
     */

    /**
     * Get a list of tasks for a specific user.
     *
     * @param user
     * @return
     */
    public TaskList userTasks(User user){
        TaskList filtered = new TaskList();
        for (Task t: tasks.getTasks()) {
            if (t.isOwner(user)) {
                filtered.addTask(t);
            }
        }
        return filtered;
    };

    /**
     * Get a list of bids for a specific task.
     *
     * @param task
     * @return
     */
    public BidList taskBids(Task task){
        BidList filtered = new BidList();
        for (Bid b: bids.getBids()) {
            if (b.isParentTask(task)) {
                filtered.addBid(b);
            }
        }
        return filtered;
    }

    /**
     * Get a list of bids for tasks I have bid on.
     *  call userTasks with current user? or leave for caller?
     *
     */
    public BidList userBids(User user){
        BidList filtered = new BidList();
        for (Bid b: bids.getBids()) {
            if (b.isOwner(user)) {
                filtered.addBid(b);
            }
        }
        return filtered;
    }

    /**
     * Get Tasks with status.
     *
     * @param status
     * @return
     */
    public TaskList tasksWithStatus(String status){
        TaskList filtered = new TaskList();
        for (Task t: tasks.getTasks()) {
            if (status.equals(t.getStatus())) {
                filtered.addTask(t);
            }
        }
        return filtered;
    }


    /**
     * Get tasks with status for specific user
     *
     * @param user, status
     * @return
     */
    public TaskList userTasksWithStatus(User user, String status){
        TaskList filtered = new TaskList();
        for (Task t: tasks.getTasks()) {
            if (t.isOwner(user) && status.equals(t.getStatus())) {
                filtered.addTask(t);
            }
        }
        return filtered;
    }

    /**
     * Tasks that have been assigned to given user.
     *
     * @param user, status
     * @return
     */
    public TaskList userAssignedTasks(User user){
        TaskList filtered = new TaskList();
        for (Task t: tasks.getTasks()) {
            if (t.isAssignee(user)) {
                filtered.addTask(t);
            }
        }
        return filtered;
    }

    /**
     * Tasks that I have bidded on
     *
     * @param user
     * @return
     */
    public TaskList tasksWithUserBids(User user){
        TaskList filtered = new TaskList();
        Task t;
        for (Bid b: bids.getBids()) {
            if (b.isOwner(user)) {
                t = tasks.getTask(b.getParentTask());
                if (!filtered.hasTask(t)) {
                    filtered.addTask(t);
                }
            }
        }
        return filtered;
    }




    public double getLowestBid(Task task){
        double lowestBid = -1;
        for (Bid b: bids.getBids()) {
            if (b.isParentTask(task) && lowestBid == -1) {
                lowestBid = b.getAmount();
            } else if (b.isParentTask(task)) {
                if (b.getAmount() < lowestBid) {
                    lowestBid = b.getAmount();
                }
            }
        }
        return lowestBid;
    }

    public double getLowestBidForUser(Task task, User user){
        double lowestBid = Double.POSITIVE_INFINITY;
        for (Bid b: bids.getBids()) {
            if (b.isParentTask(task) && b.isOwner(user)) {
                if (b.getAmount() < lowestBid) {
                    lowestBid = b.getAmount();
                }
            }
        }
        return lowestBid;
    }

    // Since deprecating each task owning a bidlist,
    // this is how to get bid counts
    public int getTaskBidCount(Task task){
        int count = 0;
        for (Bid b: bids.getBids()) {
            if (b.isParentTask(task)) {
                count ++;
            }
        }
        return count;
    }

    public TaskList keywordSearch(String keywords){
        // break keywords into words

        // For each task in tasks
        //   TODO: task string representation
        //   lookInHere = task.toString();
        //
        //   for each keyword:
        //      look for the keyword in lookInHere
        //      if found:
        //        add it to a new list
        //      else:
        //        continue
        // Task t = new Task();
        // t.toString()
        return new TaskList();
    }



    public boolean userExists(String username) {
        if (users.getIndexByUsername(username) > -1) {
            return true;
        } else {
            return false;
        }
    }

    public Task getTask(String uuid) {
        return tasks.getTask(uuid);
    }

    public void sync() {
        // Todo: wrap these in a sort of timeout for offline functionality
        sync.sync();
        users.clear();
        tasks.clear();
        bids.clear();
        fs.loadAllFromFile(users, tasks, bids);
    }

}
