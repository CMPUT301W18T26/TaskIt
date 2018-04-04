package com.cmput301w18t26.taskit;

/**
 * Created by kevingordon on 2018-03-03.
 */

import android.content.Context;
import android.location.Location;

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

// Todo: cite https://www.geeksforgeeks.org/singleton-class-java/ in wiki

/**
 * Holds the data for the entire application.
 * Issues commands to sync and filesystem.
 * Filters data with queries.
 *
 * - push/pull from the filesystem
 * - sync the server and the filesystem
 * - get filtered lists (refactor into filter class, call these)
 * - add/update/delete {tasks, users, bids}
 *
 * @author UAlberta-Cmput301-Team26 crew
 * @see TaskItFile
 * @see TaskItServer
 * @see TaskItSync
 */
public class TaskItData {

    /**
     * TaskItData will be a singleton
     */
    private static TaskItData single_instance = null;

    /**
     * The tasks for this application
     */
    private TaskList tasks;

    /**
     * The users for this application
     */
    private UserList users;

    /**
     * The bids for this application
     */
    private BidList bids;

    /**
     * The user that is currently logged in to the application.
     */
    private User currentUser;

    /**
     * For filesystem I/O.
     */
    private TaskItFile fs;

    /**
     * For synchronization of memory, filesystem, and server
     */
    public static TaskItSync sync;

    /**
     * Initialize the database
     */
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

    /**
     * Create singleton if not already created.
     * @return singleton instance of this class.
     */
    public static TaskItData getInstance() {
        if (single_instance == null) {
            single_instance = new TaskItData();
        }
        return single_instance;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
        sync.setCurrentUser(currentUser.getUsername());
    }

    // This will append data from the filesystem to the {user, tasks, bids}Lists
    // Can result in duplicate data, usually clear the list prior
    // ... consider refactor or delete
    // public void refresh() {
    //    fs.loadAllFromFile(users, tasks, bids);
    // }

    /**
     *       ----  Data add/delete/update methods  ----
     *
     * The following methods add metadata to the provided objects.
     * The objects are added to their corresponding lists.
     * The objects are added/removed from the filesystem.
     * A sync call is made to update the server of changes.
     *
     */

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
        // Cascade delete all bids for this task
        TaskList deleteThese = new TaskList();

        for (Task t: tasks.getTasks()) {
            if (t.isOwner(currentUser)) {
                deleteThese.addTask(t);
            }
        }

        for (Task t: deleteThese.getTasks()) {
            deleteTask(t);
        }

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
        BidList deleteThese = new BidList();

        User currUser = getCurrentUser();

        for (Bid b: bids.getBids()) {
            if (b.isParentTask(task)) {
                deleteThese.addBid(b);
            }
        }

        for (Bid b: deleteThese.getBids()) {
            setCurrentUser(getUserByUsername(b.getOwner()));
            deleteBid(b);
        }

        setCurrentUser(currUser);

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
        TaskList t = new TaskList();
        t.addAll(tasks);
        return t;
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


    public void updateBid (Bid bid) {
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
     *       ----    Filter methods    -----
     * The following methods query the database,
     * returning subsets of the data.
     */

    /**
     * Get a list of tasks for a given user.
     *
     * @param user the user whose tasks we wish returned.
     * @return list of tasks for the given user.
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
     * Get a list of bids for a given task.
     *
     * @param task the task whose bids we wish returned.
     * @return list of bids for the given task.
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

    // public BidList userBids(User user){
    //    BidList filtered = new BidList();
    //     for (Bid b: bids.getBids()) {
    //         if (b.isOwner(user)) {
    //             filtered.addBid(b);
    //         }
    //     }
    //     return filtered;
    // }

    /**
     * Get Tasks with a given status.
     *
     * @param status the status of the tasks we wish returned
     * @return list of tasks with the given status
     */
    public TaskList tasksWithStatus(String status){
        TaskList filtered = new TaskList();
        for (Task t: tasks.getTasks()) {
            if (status.equals(getTaskStatus(t))) {
                filtered.addTask(t);
            }
        }
        return filtered;
    }


    /**
     * Get tasks with given status for given user
     *
     * @param user user whose tasks we we returned
     * @param status status of the user's task we wish returned
     * @return list of tasks with given owner and status
     */
    public TaskList userTasksWithStatus(User user, String status){
        TaskList filtered = new TaskList();
        for (Task t: tasks.getTasks()) {
            if (t.isOwner(user) && status.equals(getTaskStatus(t))) {
                filtered.addTask(t);
            }
        }
        return filtered;
    }

    /**
     * Tasks that have been assigned to given user.
     *
     * @param user user to whom the tasks have been assigned
     * @return list of tasks where the given user is the assignee
     */
    public TaskList userAssignedTasks(User user){
        TaskList filtered = new TaskList();
        for (Task t: tasks.getTasks()) {
            if (t.isAssignee(user) && !t.getStatus().equals("Done")) {
                filtered.addTask(t);
            }
        }
        return filtered;
    }
    /**
     * Tasks that have been assigned to given user.
     *
     * @param user user to whom the tasks have been assigned
     * @return list of tasks where the given user is the assignee
     */
    public TaskList userDoneTasks(User user){
        TaskList filtered = new TaskList();
        for (Task t: tasks.getTasks()) {
            if (t.isAssignee(user) && t.getStatus().equals("Done")) {
                filtered.addTask(t);
            }
        }
        return filtered;
    }

    /**
     * Tasks that a given user has bidded on
     *
     * @param user user that has bidded on tasks
     * @param strictlyBidded whether the task filters out assigned/done tasks
     * @return list of tasks that the given user has bid on
     */
    public TaskList tasksWithUserBids(User user, boolean strictlyBidded){
        TaskList filtered = new TaskList();
        Task t;
        for (Bid b: bids.getBids()) {
            if (b.isOwner(user)) {
                t = tasks.getTask(b.getParentTask());
                if (strictlyBidded
                    && (t.getStatus().equals("Assigned") || t.getStatus().equals("Done"))) {
                    continue;
                }
                if (!filtered.hasTask(t)) {
                    filtered.addTask(t);
                }
            }
        }
        return filtered;
    }


    /**
     * Compute the lowest bid for this given task
     * @param task task to compute the lowest bid of
     * @return the lowest bid for the given task
     */
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

    /**
     * Compute the lowest bid by a given user for the given task
     * @param task the task to compute the lowest bid of
     * @param user the user whose bids will be checked
     * @return the lowest bid by the given user for the given task
     */
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

    /**
     * compute the number of bids for a given task
     * @param task task whose bids we wish to count
     * @return the number of bids for the given task
     */
    public int getTaskBidCount(Task task){
        int count = 0;
        for (Bid b: bids.getBids()) {
            if (b.isParentTask(task)) {
                count ++;
            }
        }
        return count;
    }


    public String getTaskStatus(Task t) {
        String status;
        status = "Requested";
        if (t.getStatus().equals("Done")) {
            status = "Done";
        } else if (t.hasAssignee()) {
            status = "Assigned";
        } else {
            for (Bid b : bids.getBids()) {
                if (b.isParentTask(t)) {
                    status = "Bidded";
                    break;
                }
            }
        }
        return status;
    }


    /**
     * In progress
     */
    public TaskList keywordSearch(String keywords){
        if (keywords.length()==0) {
            return getTasks();
        }
        TaskList filtered = new TaskList();

        // break keywords into words
        String[] kws = keywords.split(" ");
        String taskString;
        // For each task in tasks
        for (Task t: tasks.getTasks()) {
            taskString = t.toString();
            for (String s: kws) {
                if (taskString.toLowerCase().indexOf(s.toLowerCase()) != -1 &&
                    !filtered.hasTask(t)) {
                    filtered.addTask(t);
                }
            }
        }

        return filtered;
    }

    /**
     * Find and return all the tasks within 5 km of location parameter
     * @param location the location to compare task location to
     * @return TaskList containing all the tasks with locations within 5 km of location
     */
    public TaskList tasksWithin5K(Location location) {
        TaskList filtered = new TaskList();
        float distance;

        for (Task task: tasks.getTasks()) {
            if (!task.hasLocation()
                    || task.getStatus().equals("Assigned")
                    || task.getStatus().equals("Done")) {
                continue;
            }
            distance = location.distanceTo(task.getLocation());
//            Location.distanceBetween(location.getLatitude(), location.getLongitude(),
//                    taskLocation.getLatitude(), taskLocation.getLongitude(), results);
            //TODO replace "Assigned" and "Bidded" with string constants from Task class
            if (distance < 5000) {
                filtered.addTask(task);
            }
        }
        return filtered;
    }

    /**
     * Get the user corresponding to the given username
     * @param username the username of the user we wish to retrieve
     * @return
     */
    public User getUserByUsername(String username) {
        return users.getUserByUsername(username);
    }

    /**
     * Check if a user exists with the given username
     * @param username the username of the user we wish to check existence
     * @return true if a user with the given username exists, false o.w.
     */
    public boolean userExists(String username) {
        if (users.getIndexByUsername(username) > -1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean taskExists(String uuid) {
        if (tasks.getIndex(uuid) > -1) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * Get a task with the given UUID
     * @param uuid the uuid of the task to retrieve
     * @return the task with the given UUID
     */
    public Task getTask(String uuid) {
        return tasks.getTask(uuid);
    }

    /**
     * Sync the application data with the server.
     * First sync the filesystem and the server.
     * Next clear our in-memory application data.
     * Finally, load the newly synced filesystem data into memory.
     */
    public void sync() {
        // Todo: wrap these in a sort of timeout for offline functionality
        sync.sync();
        users.clear();
        tasks.clear();
        bids.clear();
        fs.loadAllFromFile(users, tasks, bids);
    }

}
