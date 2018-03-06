package com.cmput301w18t26.taskit;

/**
 * Created by kevingordon on 2018-03-06.
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

/**
 * Handles file io for TaskIt
 */
public class TaskItFile {
    private static TaskItFile single_instance = null;

    private static final String userDirName = "user";
    private static final String taskDirName = "task";
    private static final String bidDirName = "bids";

    public static final int USER = 1;
    public static final int TASK = 2;
    public static final int BID = 3;
    public static final int TRASH = 4;

    private static Context context;

    public static void setContext(Context c) {
        context = c;
    }


    public static TaskItFile getInstance() throws NoContextException {
        if (context == null) {
            throw new NoContextException();
        } else {
            single_instance = new TaskItFile();
        }
        return single_instance;
    }

    private Object loadFromFile(String filename, Type type) {
        Object o = new Object();
        try {
            FileInputStream fis = new FileInputStream(filename);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();
            // Taken from
            // https://stackoverflow.com/
            // questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            // 2018-02-03
            o = gson.fromJson(in, type);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return o;
    }

    public void loadAllFromFile(UserList u, TaskList t, BidList b) {
        String dirName;
        File dir;
        String[] filenames;
        String filename;

        // user handling
        dirName = getDirname(USER);
        dir = new File(dirName);
        filenames = dir.list();

        Type userType = new TypeToken<User>() {
        }.getType();
        for (int i = 0; i < filenames.length; i++) {
            filename = dirName + "/" + filenames[i];
            u.addUser((User) loadFromFile(filename, userType));
        }
        // task handling
        dirName = getDirname(TASK);
        dir = new File(dirName);
        filenames = dir.list();

        Type taskType = new TypeToken<Task>() {
        }.getType();
        for (int i = 0; i < filenames.length; i++) {
            filename = dirName + "/" + filenames[i];
            t.addTask((Task) loadFromFile(filename, taskType));
        }
        // bid handling
        dirName = getDirname(BID);
        dir = new File(dirName);
        filenames = dir.list();

        Type bidType = new TypeToken<Bid>() {
        }.getType();
        for (int i = 0; i < filenames.length; i++) {
            filename = dirName + "/" + filenames[i];
            b.addBid((Bid) loadFromFile(filename, bidType));
        }
    }

        // get the string of the directory belonging to a model collection
        // create the directory if needed
    private static String getDirname(int model) {
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

    public static String getUserFilename(User user, boolean trash) {
        String userFile;
        if (trash) {
            userFile = getDirname(TRASH) + "/" + user.getUsername();
        } else {
            userFile = getDirname(USER) + "/" + user.getUsername();
        }
        return userFile;
    }
    public static String getTaskFilename(Task task, boolean trash) {
        String taskFile;
        if (trash) {
            taskFile = getDirname(TRASH) + "/" + String.valueOf(task.getID());
        } else {
            taskFile = getDirname(TASK) + "/" + String.valueOf(task.getID());
        }
        return taskFile;
    }
    public static String getBidFilename(Bid bid, boolean trash) {
        String bidFile;
        if (trash) {
            bidFile = getDirname(TRASH) + "/" + String.valueOf(bid.getID());
        } else {
            bidFile = getDirname(BID) + "/" + String.valueOf(bid.getID());
        }
        return bidFile;
    }

    // TODO: cite the following in wiki
    // https://stackoverflow.com/questions/1889188/how-to-create-files-hierarchy-in-androids-data-data-pkg-files-directory
    public void addUserFile(User user) {
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

    public void deleteUserFile(User user) {
        String filename = getUserFilename(user, false);
        String trashFilename = getUserFilename(user, true);
        File file = new File(filename);
        File trashFile = new File(trashFilename);
        file.renameTo(trashFile);
    }

    // https://stackoverflow.com/questions/1889188/how-to-create-files-hierarchy-in-androids-data-data-pkg-files-directory
    public void addTaskFile(Task task) {
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

    public void deleteTaskFile(Task task) {
        String filename = getTaskFilename(task, false);
        String trashFilename = getTaskFilename(task, true);
        File file = new File(filename);
        File trashFile = new File(trashFilename);
        file.renameTo(trashFile);
    }
    // https://stackoverflow.com/questions/1889188/how-to-create-files-hierarchy-in-androids-data-data-pkg-files-directory
    public void addBidFile(Bid bid) {
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
    public void deleteBidFile(Bid bid) {
        String filename = getBidFilename(bid, false);
        String trashFilename = getBidFilename(bid, true);
        File file = new File(filename);
        File trashFile = new File(trashFilename);
        file.renameTo(trashFile);
    }

}

