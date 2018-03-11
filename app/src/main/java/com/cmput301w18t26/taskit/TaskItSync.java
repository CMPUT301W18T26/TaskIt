package com.cmput301w18t26.taskit;

/**
 * Created by kevingordon on 2018-03-06.
 */

import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
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

 */
public class TaskItSync {
    // GROSS
    private String currentUser;

    private UserList localUsers;
    private UserList remoteUsers;

    private TaskList localTasks;
    private TaskList remoteTasks;

    private BidList localBids;
    private BidList remoteBids;

    private TaskItFile fs;
    public TaskItServer server;

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


    public void sync() {
        localUsers.clear();
        localTasks.clear();
        localBids.clear();
        remoteUsers.clear();
        remoteTasks.clear();
        remoteBids.clear();

        fs.loadAllFromFile(localUsers, localTasks, localBids);
        server.loadAllFromServer(remoteUsers, remoteTasks, remoteBids);

        Log.d("TaskItSync", "Current user: "+ currentUser);
        Log.d("TaskItSync", "syncing... fs count: "+String.valueOf(localUsers.getUserCount()));
        Log.d("TaskItSync", "syncing... svr count: "+String.valueOf(remoteUsers.getUserCount()));
        syncUsers();
        syncTasks();
        syncBids();
    }

    private void syncUsers() {
        User currUser;
        Log.d("TaskItSync", "Looping over local");
        for (int i=0;i<localUsers.getUserCount(); i++) {
            currUser = localUsers.getUser(i);
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
        for (int i=0;i<remoteUsers.getUserCount(); i++) {
            currUser = remoteUsers.getUser(i);
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

    private void syncTasks() {
        Task currTask;
        for (int i=0;i<localTasks.getTaskCount(); i++) {
            currTask = localTasks.getTask(i);
            if (currTask.isOwner(currentUser)) { // Owner's file
                if (!remoteTasks.hasTask(currTask)) { // Local, not remote
                    server.addTask(currTask);
                } else { // In both
                    Task rTask = remoteTasks.getTask(currTask);
                    Task lTask = localTasks.getTask(currTask);
                    if (rTask.getTimestamp().after(lTask.getTimestamp())) { // remote is current
                        fs.deleteTaskFile(lTask);
                        fs.addTaskFile(rTask);
                    } else { // local is current
                        server.delTask(rTask);
                        server.addTask(lTask);
                    }
                    // remove from remote list (so as not to duplicate work)
                    remoteTasks.deleteTask(currTask);
                }
            } else { // Others file
                if (!remoteTasks.hasTask(currTask)) { // Local, not remote
                    fs.deleteTaskFile(currTask);
                } else { // In both
                    Task rTask = remoteTasks.getTask(currTask);
                    Task lTask = localTasks.getTask(currTask);
                    if (rTask.getTimestamp().after(lTask.getTimestamp())) { // remote is current
                        fs.deleteTaskFile(lTask);
                        fs.addTaskFile(rTask);
                    } else { // local is current
                        server.delTask(rTask);
                        server.addTask(lTask);
                    }
                    // remove from remote list (so as not to duplicate work)
                    remoteTasks.deleteTask(currTask);
                }
            }

        }

        for (int i=0;i<remoteTasks.getTaskCount(); i++) {
            currTask = remoteTasks.getTask(i);
            if (currTask.isOwner(currentUser)) { // Owner's file
                if (!localTasks.hasTask(currTask)) { // Remote, not local
                    server.delTask(currTask);
                }
            } else { // Others file
                if (!localTasks.hasTask(currTask)) { // Remote, not local
                    fs.addTaskFile(currTask);
                }
            }
        }
    }

    private void syncBids() {
        Bid currBid;
        for (int i=0;i<localBids.getBidCount(); i++) {
            currBid = localBids.getBid(i);
            if (currBid.isOwner(currentUser)) { // Owner's file
                if (!remoteBids.hasBid(currBid)) { // Local, not remote
                    server.addBid(currBid);
                } else { // In both
                    Bid rBid = remoteBids.getBid(currBid);
                    Bid lBid = localBids.getBid(currBid);
                    if (rBid.getTimestamp().after(lBid.getTimestamp())) { // remote is current
                        fs.deleteBidFile(lBid);
                        fs.addBidFile(rBid);
                    } else { // local is current
                        server.delBid(rBid);
                        server.addBid(lBid);
                    }
                    // remove from remote list (so as not to duplicate work)
                    remoteBids.deleteBid(currBid);
                }
            } else { // Others file
                if (!remoteBids.hasBid(currBid)) { // Local, not remote
                    fs.deleteBidFile(currBid);
                } else { // In both
                    Bid rBid = remoteBids.getBid(currBid);
                    Bid lBid = localBids.getBid(currBid);
                    if (rBid.getTimestamp().after(lBid.getTimestamp())) { // remote is current
                        fs.deleteBidFile(lBid);
                        fs.addBidFile(rBid);
                    } else { // local is current
                        server.delBid(rBid);
                        server.addBid(lBid);
                    }
                    // remove from remote list (so as not to duplicate work)
                    remoteBids.deleteBid(currBid);
                }
            }

        }

        for (int i=0;i<remoteBids.getBidCount(); i++) {
            currBid = remoteBids.getBid(i);
            if (currBid.isOwner(currentUser)) { // Owner's file
                if (!localBids.hasBid(currBid)) { // Remote, not local
                    server.delBid(currBid);
                }
            } else { // Others file
                if (!localBids.hasBid(currBid)) { // Remote, not local
                    fs.addBidFile(currBid);
                }
            }
        }
    }


}
