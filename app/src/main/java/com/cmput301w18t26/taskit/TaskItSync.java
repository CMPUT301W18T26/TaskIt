package com.cmput301w18t26.taskit;

/**
 * Created by kevingordon on 2018-03-06.
 */

import android.net.ConnectivityManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 *
 * Handles synchronization of filesystem and server
 *
 * Get the data from the filesystem (local)
 * Get the data from the server (remote)
 *
 * Compare.
 *
 * Major cases:
 * - Objects I do own (my user, task, bid)
 *   - local, not remote > push to remote
 *   - remote, not local > delete from remote
 *   - both > choose newest > update accordingly
 * - Objects I do not own
 *   - local, not remote > delete from local
 *   - remote, not local > add to local
 *   - both > choose newest > update accordingly
 *
 * @author UAlberta-Cmput301-Team26 crew
 * @see TaskItFile
 * @see TaskItData
 * @see TaskItServer
 */
public class TaskItSync {

    /**
     * A current user, for determining sync cases
     */
    private String currentUser = "";

    /**
     * We'll need a place to store temporary local and remote data
     */
    private UserList localUsers;
    private UserList remoteUsers;

    private TaskList localTasks;
    private TaskList remoteTasks;

    private BidList localBids;
    private BidList remoteBids;

    public long newBids = 0;

    /**
     * We'll need to be able to do filesystem i/o
     */
    private TaskItFile fs;

    /**
     * We'll need to be able to do server i/o
     */
    public TaskItServer server;

    /**
     * Initialize our sync class
     */
    public TaskItSync() {
        localUsers = new UserList();
        remoteUsers = new UserList();

        remoteTasks = new TaskList();
        localTasks = new TaskList();

        remoteBids = new BidList();
        localBids = new BidList();

        try {
            this.fs = TaskItFile.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.server = new TaskItServer();

    }

    public void setCurrentUser(String username) {
        currentUser = username;
    }

    /**
     * Synchronize all application data between filesystem and server
     *
     * Clear our data buffers.
     * Load data into buffers.
     * Call sync methods for specific data types.
     */
    public void sync() {
        try {
            if (server.isNetworkConnected(fs.getContext())) {
                Log.d("TaskItSync", "server connected");
                localUsers.clear();
                localTasks.clear();
                localBids.clear();
                remoteUsers.clear();
                remoteTasks.clear();
                remoteBids.clear();

                fs.loadAllFromFile(localUsers, localTasks, localBids);
                server.loadAllFromServer(remoteUsers, remoteTasks, remoteBids);

                Log.d("TaskItSync", "Current user: " + currentUser);
                Log.d("TaskItSync", "syncing... fs count: " + String.valueOf(localUsers.getUserCount()));
                Log.d("TaskItSync", "syncing... svr count: " + String.valueOf(remoteUsers.getUserCount()));
                syncUsers();
                syncTasks();
                syncBids();
            } else {
                Log.d("TaskItSync", "server not connected");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Given the data in the local/remote UserLists
     * adds/Removes objects from the filesystem and server
     * based on the cases mentioned at the head of this class file.
     */
    private void syncUsers() {
        Log.d("TaskItSync", "Looping over local");
        for (User currUser: localUsers.getUsers()) {
            Log.d("TaskItSync", "syncing " + currUser.getUsername());
            if (currUser.isOwner(currentUser)) { // Owner's file
                Log.d("TaskItSync", "owner's file...");
                if (!remoteUsers.hasUser(currUser)) { // Local, not remote
                    Log.d("TaskItSync", "in local, not remote...");
                    server.addUser(currUser);
                } else { // In both
                    Log.d("TaskItSync", "in both");
                    User rUser = remoteUsers.getUser(currUser);
                    User lUser = localUsers.getUser(currUser);
                    if (rUser.getTimestamp().after(lUser.getTimestamp())) { // remote is current
                        Log.d("TaskItSync", "remote is most current");
                        fs.deleteUserFile(lUser);
                        fs.addUserFile(rUser);
                    } else { // local is current
                        Log.d("TaskItSync", "local is current");
                        server.delUser(rUser);
                        server.addUser(lUser);
                    }
                    // remove from remote list (so as not to duplicate work)
                    remoteUsers.deleteUser(currUser);
                }
            } else { // Others file
                Log.d("TaskItSync", "other is owner");
                if (!remoteUsers.hasUser(currUser)) { // Local, not remote
                    Log.d("TaskItSync", "local not remote");
                    fs.deleteUserFile(currUser);
                } else { // In both
                    Log.d("TaskItSync", "in both");
                    User rUser = remoteUsers.getUser(currUser);
                    User lUser = localUsers.getUser(currUser);
                    if (rUser.getTimestamp().after(lUser.getTimestamp())) { // remote is current
                        Log.d("TaskItSync", "remote is most current");
                        fs.deleteUserFile(lUser);
                        fs.addUserFile(rUser);
                    } else { // local is current
                        Log.d("TaskItSync", "local is most current");
                        server.delUser(rUser);
                        server.addUser(lUser);
                    }
                    // remove from remote list (so as not to duplicate work)
                    remoteUsers.deleteUser(currUser);
                }
            }

        }

        Log.d("TaskItSync", "looping over remote");
        // Anything in both will have been processed by the above
        for (User currUser: remoteUsers.getUsers()) {
            Log.d("TaskItSync", currUser.getUsername());
            if (currUser.isOwner(currentUser)) { // Owner's file
                Log.d("TaskItSync", "is owner");
                if (!localUsers.hasUser(currUser)) { // Remote, not local
                    Log.d("TaskItSync", "remote not local");
                    server.delUser(currUser);
                }
            } else { // Others file
                Log.d("TaskItSync", "other is owner");
                if (!localUsers.hasUser(currUser)) { // Remote, not local
                    Log.d("TaskItSync", "remote not local");
                    fs.addUserFile(currUser);
                }
            }
        }
    }

    /**
     * Given the data in the local/remote TaskLists
     * adds/Removes objects from the filesystem and server
     * based on the cases mentioned at the head of this class file.
     */
    private void syncTasks() {
        Log.d("TaskItSync", "Current user: "+ currentUser);
        Log.d("TaskItSync", "Syncing tasks");
        for (Task currTask: localTasks.getTasks()) {
            Log.d("TaskItSync", "current task "+currTask.getUUID());
            if (currTask.isOwner(currentUser)) { // Owner's file
                if (!remoteTasks.hasTask(currTask)) { // Local, not remote
                    Log.d("TaskItSync", "adding to server");
                    server.addTask(currTask);
                } else { // In both
                    Task rTask = remoteTasks.getTask(currTask);
                    Task lTask = localTasks.getTask(currTask);
                    if (rTask.getTimestamp().after(lTask.getTimestamp())) { // remote is current
                        fs.deleteTaskFile(lTask);
                        fs.addTaskFile(rTask);
                        Log.d("TaskItSync", "update local");
                    } else { // local is current
                        server.delTask(rTask);
                        server.addTask(lTask);
                        Log.d("TaskItSync", "update remote");
                    }
                    // remove from remote list (so as not to duplicate work)
                    remoteTasks.deleteTask(currTask);
                }
            } else { // Others file
                if (!remoteTasks.hasTask(currTask)) { // Local, not remote
                    Log.d("TaskItSync", "removing from local");
                    fs.deleteTaskFile(currTask);
                } else { // In both
                    Task rTask = remoteTasks.getTask(currTask);
                    Task lTask = localTasks.getTask(currTask);
                    if (rTask.getTimestamp().after(lTask.getTimestamp())) { // remote is current
                        fs.deleteTaskFile(lTask);
                        fs.addTaskFile(rTask);
                        Log.d("TaskItSync", "update local");
                    } else { // local is current
                        server.delTask(rTask);
                        server.addTask(lTask);
                        Log.d("TaskItSync", "update remote");
                    }
                    // remove from remote list (so as not to duplicate work)
                    remoteTasks.deleteTask(currTask);
                }
            }

        }

        for (Task currTask: remoteTasks.getTasks()) {
            Log.d("TaskItSync", "current task "+currTask.getUUID());
            if (currTask.isOwner(currentUser)) { // Owner's file
                if (!localTasks.hasTask(currTask)) { // Remote, not local
                    server.delTask(currTask);
                    Log.d("TaskItSync", "delete from server");
                }
            } else { // Others file
                if (!localTasks.hasTask(currTask)) { // Remote, not local
                    fs.addTaskFile(currTask);
                    Log.d("TaskItSync", "add to local");
                }
            }
        }
    }

    /**
     * Given the data in the local/remote BidLists
     * adds/Removes objects from the filesystem and server
     * based on the cases mentioned at the head of this class file.
     */
    private void syncBids() {
        Bid currBid;
        Log.d("TaskItSync", "Current user: "+ currentUser);
        Log.d("TaskItSync", "Syncing bids");
        for (int i=0;i<localBids.getBidCount(); i++) {
            currBid = localBids.getBid(i);
            Log.d("TaskItSync", "current bid "+currBid.getUUID());
            if (currBid.isOwner(currentUser)) { // Owner's file
                if (!remoteBids.hasBid(currBid)) { // Local, not remote
                    server.addBid(currBid);
                    Log.d("TaskItSync", "adding bid to server");
                } else { // In both
                    Bid rBid = remoteBids.getBid(currBid);
                    Bid lBid = localBids.getBid(currBid);
                    if (rBid.getTimestamp().after(lBid.getTimestamp())) { // remote is current
                        fs.deleteBidFile(lBid);
                        fs.addBidFile(rBid);
                        Log.d("TaskItSync", "update local");
                    } else { // local is current
                        server.delBid(rBid);
                        server.addBid(lBid);
                        Log.d("TaskItSync", "update remote");
                    }
                    // remove from remote list (so as not to duplicate work)
                    remoteBids.deleteBid(currBid);
                }
            } else { // Others file
                if (!remoteBids.hasBid(currBid)) { // Local, not remote
                    fs.deleteBidFile(currBid);
                    Log.d("TaskItSync", "deleting bid from local");
                } else { // In both
                    Bid rBid = remoteBids.getBid(currBid);
                    Bid lBid = localBids.getBid(currBid);
                    if (rBid.getTimestamp().after(lBid.getTimestamp())) { // remote is current
                        fs.deleteBidFile(lBid);
                        fs.addBidFile(rBid);
                        Log.d("TaskItSync", "update local");
                    } else { // local is current
                        server.delBid(rBid);
                        server.addBid(lBid);
                        Log.d("TaskItSync", "update server");
                    }
                    // remove from remote list (so as not to duplicate work)
                    remoteBids.deleteBid(currBid);
                }
            }

        }

        for (int i=0;i<remoteBids.getBidCount(); i++) {
            currBid = remoteBids.getBid(i);
            Log.d("TaskItSync", "current bid "+currBid.getUUID());
            if (currBid.isOwner(currentUser)) { // Owner's file
                if (!localBids.hasBid(currBid)) { // Remote, not local
                    server.delBid(currBid);
                    Log.d("TaskItSync", "deleting bid from server");
                }
            } else { // Others file
                if (!localBids.hasBid(currBid)) { // Remote, not local
                    fs.addBidFile(currBid);
                    newBids += 1;
                    Log.d("TaskItSync", "adding bid to local");
                }
            }
        }
    }

    public long getNewBids() {
        return newBids;
    }
    public void setNewBids(long n) {
        newBids = n;
    }


}
