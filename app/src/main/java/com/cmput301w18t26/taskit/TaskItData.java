package com.cmput301w18t26.taskit;

/**
 * Created by kevingordon on 2018-03-03.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;


/**
 * TaskItData will hold all the data for the entire application.
 *
 * It will push/pull from the filesystem
 *
 */
// Todo: cite https://www.geeksforgeeks.org/singleton-class-java/ in wiki
public class TaskItData {
    private final String userDirName = "user";
    private final String taskDirName = "task";
    private final String bidDirName = "bids";

    public static final int USER = 1;
    public static final int TASK = 2;
    public static final int BID = 3;
    public static final int TRASH = 4;

    private static TaskItData single_instance = null;

    private TaskList tasks;
    private UserList users;
    private BidList bids;

    private Context context;

    public TaskItData(Context context) {
        this.context = context;
        this.users = new UserList();
        this.tasks = new TaskList();
        this.bids = new BidList();
    }

    public static TaskItData getInstance(Context context) {
        if (single_instance == null) {
            single_instance = new TaskItData(context);
        }
        return single_instance;
    }

    /**
     * USER METHODS
     */
    public void addUser(User user) {
        this.users.addUser(user);
        // Add user to filesystem
        addUserFile(user);
        // Request sync?
    }

    public void deleteUser(User user) {
        this.users.deleteUser(user);
        // Move user to trash (filesystem)
        deleteUserFile(user);
        // Request sync?
    }


    /**
     * TASK METHODS
     */
    public void addTask(Task task) {
        this.tasks.addTask(task);
        // Add the task to filesystem
        addTaskFile(task);
        // Request sync?
    }

    public void deleteTask(Task task) {
        this.tasks.deleteTask(task);
        // Move task to trash (filesystem)
        deleteTaskFile(task);
        // Request sync?
    }


    /**
     * BID METHODS
     */
    public void addBid(Bid bid) {
        this.bids.addBid(bid);
        // Add the bid to filesystem
        addBidFile(bid);
        // Request sync?
    }

    public void deleteBid(Bid bid) {
        this.bids.deleteBid(bid);
        // Move bid to trash (filesystem)
        deleteBidFile(bid);
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
    public BidList taskBids(Task task){
        return new BidList();
    };



    // get the string of the directory belonging to a model collection
    // create the directory if needed
    public String getDirname(int model) {
        String dirPath = context.getFilesDir().toString();

        if (model == USER) {
             dirPath +=  "/" + userDirName;
        } else if (model == TASK) {
            dirPath += "/" + taskDirName;
        } else if (model == BID) {
            dirPath += "/" + bidDirName;
        }
        File file = new File(dirPath);
        file.mkdirs();
        return dirPath;
    }

    public String getUserFilename(User user, boolean trash) {
        String userFile;
        if (trash) {
            userFile = getDirname(TRASH) + user.getUsername();
        } else {
            userFile = getDirname(USER) + user.getUsername();
        }
        return userFile;
    }
    public String getTaskFilename(Task task, boolean trash) {
        String taskFile;
        if (trash) {
            taskFile = getDirname(TRASH) + String.valueOf(task.getID());
        } else {
            taskFile = getDirname(TASK) + String.valueOf(task.getID());
        }
        return taskFile;
    }
    public String getBidFilename(Bid bid, boolean trash) {
        String bidFile;
        if (trash) {
            bidFile = getDirname(TRASH) + String.valueOf(bid.getID());
        } else {
            bidFile = getDirname(BID) + String.valueOf(bid.getID());
        }
        return bidFile;
    }

    // TODO: cite the following in wiki
    // https://stackoverflow.com/questions/1889188/how-to-create-files-hierarchy-in-androids-data-data-pkg-files-directory
    private void addUserFile(User user) {
        String userFile = getUserFilename(user, false);

        try {
            OutputStream fos = new FileOutputStream(userFile, false);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(user, out);
            out.flush();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteUserFile(User user) {
        String filename = getUserFilename(user, false);
        String trashFilename = getUserFilename(user, true);
        File file = new File(filename);
        File trashFile = new File(trashFilename);
        file.renameTo(trashFile);
    }

    // https://stackoverflow.com/questions/1889188/how-to-create-files-hierarchy-in-androids-data-data-pkg-files-directory
    private void addTaskFile(Task task) {
        String taskFile = getTaskFilename(task, false);
        try {
            OutputStream fos = new FileOutputStream(taskFile, false);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(task, out);
            out.flush();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteTaskFile(Task task) {
        String filename = getTaskFilename(task, false);
        String trashFilename = getTaskFilename(task, true);
        File file = new File(filename);
        File trashFile = new File(trashFilename);
        file.renameTo(trashFile);
    }
    // https://stackoverflow.com/questions/1889188/how-to-create-files-hierarchy-in-androids-data-data-pkg-files-directory
    private void addBidFile(Bid bid) {
        String bidFile = getBidFilename(bid, false);

        try {
            OutputStream fos = new FileOutputStream(bidFile, false);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(bid, out);
            out.flush();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void deleteBidFile(Bid bid) {
        String filename = getBidFilename(bid, false);
        String trashFilename = getBidFilename(bid, true);
        File file = new File(filename);
        File trashFile = new File(trashFilename);
        file.renameTo(trashFile);
    }



}
