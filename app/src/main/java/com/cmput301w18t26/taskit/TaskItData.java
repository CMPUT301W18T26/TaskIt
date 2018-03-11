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
 * It will push/pull from the filesystem
 *
 */

// Todo: cite https://www.geeksforgeeks.org/singleton-class-java/ in wiki
public class TaskItData {


    private static TaskItData single_instance = null;

    private TaskList tasks;
    private UserList users;
    private BidList bids;
    private static User currentuser;
    private TaskItFile fs;
    public static TaskItSync sync;

//    private static Context context;

    public static User getCurrentuser() {
        return currentuser;
    }

    public static void setCurrentuser(User currentuser) {
        currentuser = currentuser;
        sync.setCurrentUser(currentuser.getUsername());
    }
    
    public User getUserByUsername(String username) {
        return users.getUserByUsername(username);
    }

    private TaskItData() {
        this.users = new UserList();
        this.tasks = new TaskList();
        this.bids = new BidList();
        try {
            this.fs = TaskItFile.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.sync = new TaskItSync();

    }

    public static TaskItData getInstance() {
        if (single_instance == null) {
            single_instance = new TaskItData();
        }
        return single_instance;
    }

    public void refresh() {
        fs.loadAllFromFile(users, tasks, bids);
    }

    /**
     * USER METHODS
     */
    public void addUser(User user) {
        user.setUUID(UUID.randomUUID().toString());
        user.setTimestamp(new Date());
        users.addUser(user);
        // Add user to filesystem
        fs.addUserFile(user);
        // Request sync?
    }

    public void deleteUser(User user) {
        users.deleteUser(user);
        // Move user to trash (filesystem)
        fs.deleteUserFile(user);
        // Request sync?
    }

    public void updateUser(User user) {
        user.setTimestamp(new Date());
        fs.addUserFile(user);
    }

    public UserList getUsers() {
        return users;
    }

    /**
     * TASK METHODS
     */
    public void addTask(Task task) {
        task.setUUID(UUID.randomUUID().toString());
        task.setTimestamp(new Date());
        tasks.addTask(task);
        // Add the task to filesystem
        fs.addTaskFile(task);
        // Request sync?
    }

    public void deleteTask(Task task) {
        tasks.deleteTask(task);
        // Move task to trash (filesystem)
        fs.deleteTaskFile(task);
        // Request sync?
    }

    public void updateTask(Task task) {
        task.setTimestamp(new Date());
        fs.addTaskFile(task);
    }

    public TaskList getTasks() {
        return tasks;
    }

    /**
     * BID METHODS
     */
    public void addBid(Bid bid) {
        bid.setUUID(UUID.randomUUID().toString());
        bid.setTimestamp(new Date());
        bids.addBid(bid);
        // Add the bid to filesystem
        fs.addBidFile(bid);
        // Request sync?
    }

    public void deleteBid(Bid bid) {
        bids.deleteBid(bid);
        // Move bid to trash (filesystem)
        fs.deleteBidFile(bid);
        // Request sync?
    }

    public void updateBid(Bid bid) {
        bid.setTimestamp(new Date());
        fs.addBidFile(bid);
    }


    public BidList getBids() {
        return bids;
    }


    // TODO: search functions. Will likely need more than these. Any suggestions?
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
    public BidList taskBids(Task task){
        return new BidList();
    }

    /**
     * Get Tasks with status.
     *
     * @param status
     * @return
     */
    public TaskList tasksWithStatus(String status){
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
        sync.sync();
        users.clear();
        tasks.clear();
        bids.clear();
        fs.loadAllFromFile(users, tasks, bids);
    }

}
