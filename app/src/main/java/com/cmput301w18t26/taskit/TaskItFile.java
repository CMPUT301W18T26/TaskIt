package com.cmput301w18t26.taskit;

/**
 * Created by kevingordon on 2018-03-06.
 */

import android.content.Context;
import android.util.Log;

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
 * Handles file I/O for the application
 *
 * - read/write {bids, tasks, users}
 *
 * @author UAlberta-Cmput301-Team26 crew
 * @see TaskItData
 * @see TaskItServer
 * @see TaskItSync
 */
public class TaskItFile {

    /**
     * TaskItFile will be a singleton
     */
    private static TaskItFile single_instance = null;

    /**
     * sub directory for user objects
     */
    private static final String userDirName = "user";

    /**
     * sub directory for task objects
     */
    private static final String taskDirName = "task";

    /**
     * sub directory for bid objects
     */
    private static final String bidDirName = "bids";

    /**
     * State variables
     */
    public static final int USER = 1;
    public static final int TASK = 2;
    public static final int BID = 3;
    public static final int PHOTO = 4;

    /**
     * Android requires a context to determine file path
     */
    private static Context context;

    public static void setContext(Context c) {
        context = c;
    }

    public Context getContext() {
        return context;
    }
    /**
     * Create singleton if not already created.
     * @return singleton instance of this class
     * @throws NoContextException if a context was not set prior to initialization.
     */
    public static TaskItFile getInstance() throws NoContextException {
        if (context == null) {
            throw new NoContextException();
        } else {
            single_instance = new TaskItFile();
        }
        return single_instance;
    }

    /**
     * Load a file of a given type with the given filename.
     * @param filename full filename of the object
     * @param type the object type
     * @return the object with the given filename
     */
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

    /**
     * Delete all files for this application
     */
    public static void deleteAllFromFile() {
        String dirName;
        File dir;
        String[] filenames;
        String filename;

        // user handling
        dirName = getDirname(USER);
        dir = new File(dirName);
        File[] files = dir.listFiles();


        for (int i = 0; i < files.length; i++) {
            files[i].delete();
        }
        // task handling
        dirName = getDirname(TASK);
        dir = new File(dirName);
        files = dir.listFiles();


        for (int i = 0; i < files.length; i++) {
            files[i].delete();
        }
        // bid handling
        dirName = getDirname(BID);
        dir = new File(dirName);
        files = dir.listFiles();

        for (int i = 0; i < files.length; i++) {
            files[i].delete();
        }
    }

    /**
     * Load all application files into the provided lists.
     * @param u userlist to load user objects into
     * @param t tasklist to load task objects into
     * @param b bidlist  to load bid  objects into
     */
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

    /**
     * Get the string of the directory belonging to a model collection.
     * Creates the directory if needed.
     * @param model model-class type to get directory of.
     * @return the directory name for a given model-class type.
     */
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

    /**
     * Given a user, return its full filename.
     * Use the user's UUID.
     * @param user user whose filename we wish returned
     * @return the filename for the given user.
     */
    public static String getUserFilename(User user) {
        String userFile;
        userFile = getDirname(USER) + "/" + user.getUUID();
        return userFile;
    }

    /**
     * Given a task, return its full filename.
     * Use the task's UUID.
     * @param task user whose filename we wish returned
     * @return the filename for the given task.
     */
    public static String getTaskFilename(Task task) {
        String taskFile;
        taskFile = getDirname(TASK) + "/" + task.getUUID();
        return taskFile;
    }

    /**
     * Given a bid, return its full filename.
     * Use the bid's UUID.
     * @param bid user whose filename we wish returned
     * @return the filename for the given bid.
     */
    public static String getBidFilename(Bid bid) {
        String bidFile;
        bidFile = getDirname(BID) + "/" + bid.getUUID();
        return bidFile;
    }

    /**
     * Given a photo, return its full filename.
     * Use the photo's UUID.
     * @param photo user whose filename we wish returned
     * @return the filename for the given photo.
     */
    public static String getPhotoFilename(Photo photo) {
        String photoFile;
        photoFile = getDirname(PHOTO) + "/" + photo.getUUID();
        return photoFile;
    }

    // TODO: cite the following in wiki
    // https://stackoverflow.com/questions/1889188/how-to-create-files-hierarchy-in-androids-data-data-pkg-files-directory

    /**
     * Write the given user object to file.
     * @param user the user to write to file.
     */
    public void addUserFile(User user) {
        String userFile = getUserFilename(user);

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

    /**
     * Delete the file associated with this user object.
     * @param user the user object whose file we wish to delete.
     */
    public void deleteUserFile(User user) {
        String filename = getUserFilename(user);
        File file = new File(filename);
        file.delete();
    }

    // https://stackoverflow.com/questions/1889188/how-to-create-files-hierarchy-in-androids-data-data-pkg-files-directory

    /**
     * Write the given task object to file.
     * @param task the task to write to file
     */
    public void addTaskFile(Task task) {
        String taskFile = getTaskFilename(task);
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

    /**
     * Delete the file associated with this task object.
     * @param task the task object whose file we wish to delete.
     */
    public void deleteTaskFile(Task task) {
        String filename = getTaskFilename(task);
        File file = new File(filename);
        file.delete();
    }

    // https://stackoverflow.com/questions/1889188/how-to-create-files-hierarchy-in-androids-data-data-pkg-files-directory
    /**
     * Write the given bid object to file
     * @param bid the bid to write to file
     */
    public void addBidFile(Bid bid) {
        String bidFile = getBidFilename(bid);

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

    /**
     * Delete the file associated with this bid.
     * @param bid the bid object whose file we wish to delete.
     */
    public void deleteBidFile(Bid bid) {
        String filename = getBidFilename(bid);
        File file = new File(filename);
        file.delete();
    }

    // https://stackoverflow.com/questions/1889188/how-to-create-files-hierarchy-in-androids-data-data-pkg-files-directory
    /**
     * Write the given photo object to file
     * @param photo the photo to write to file
     */
    public void addPhotoFile(Photo photo) {
        String photoFile = getPhotoFilename(photo);

        try {
            OutputStream fos = new FileOutputStream(photoFile, false);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(photo, out);
            out.flush();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete the file associated with this photo.
     * @param photo the photo object whose file we wish to delete.
     */
    public void deletePhotoFile(Photo photo) {
        String filename = getPhotoFilename(photo);
        File file = new File(filename);
        file.delete();
    }
}

