package com.cmput301w18t26.taskit;

/**
 * Created by kevingordon on 2018-03-06.
 */

import java.util.ArrayList;

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
    private String appUser = "AliceBob";

    private UserList localUsers;
    private UserList remoteUsers;

    private TaskList localTasks;
    private TaskList remoteTasks;

    private BidList localBids;
    private BidList remoteBids;

    private TaskItFile fs;
    private TaskItServer server;

    public void sync() {
        fs.loadAllFromFile(localUsers, localTasks, localBids);
        server.loadAllFromServer(remoteUsers, remoteTasks, remoteBids);

        syncUsers();
        syncTasks();
        syncBids();
    }

    private void syncUsers() {
        User currUser;
        for (int i=0;i<localUsers.getUserCount(); i++) {
            currUser = localUsers.getUser(i);
            if (currUser.isOwner(appUser)) { // Owner's file
                if (!remoteUsers.hasUser(currUser)) { // Local, not remote
                    server.addUser(currUser);
                } else { // In both
                    // Todo: Choose newest, update accordingly
                    // remove from remote list (so as not to duplicate work)
                    remoteUsers.deleteUser(currUser);
                }
            } else { // Others file
                if (!remoteUsers.hasUser(currUser)) { // Local, not remote
                    fs.deleteUserFile(currUser);
                } else { // In both
                    // Todo: Choose newest, update accordingly
                    // remove from remote list (so as not to duplicate work)
                    remoteUsers.deleteUser(currUser);
                }
            }

        }

        for (int i=0;i<remoteUsers.getUserCount(); i++) {
            currUser = remoteUsers.getUser(i);
            if (currUser.isOwner(appUser)) { // Owner's file
                if (!localUsers.hasUser(currUser)) { // Remote, not local
                    server.delUser(currUser);
                } else { // In both
                    // Todo: Choose newest, update accordingly
                }
            } else { // Others file
                if (!localUsers.hasUser(currUser)) { // Remote, not local
                    fs.addUserFile(currUser);
                } else { // In both
                    // Todo: Choose newest, update accordingly
                    // remove from remote list (so as not to duplicate work)
                    remoteUsers.deleteUser(currUser);
                }
            }
        }
    }

    private void syncTasks() {
        Task currTask;
        for (int i=0;i<localTasks.getTaskCount(); i++) {
            currTask = localTasks.getTask(i);
            if (currTask.isOwner(appUser)) { // Owner's file
                if (!remoteTasks.hasTask(currTask)) { // Local, not remote
                    server.addTask(currTask);
                } else { // In both
                    // Todo: Choose newest, update accordingly
                    // remove from remote list (so as not to duplicate work)
                    remoteTasks.deleteTask(currTask);
                }
            } else { // Others file
                if (!remoteTasks.hasTask(currTask)) { // Local, not remote
                    fs.deleteTaskFile(currTask);
                } else { // In both
                    // Todo: Choose newest, update accordingly
                    // remove from remote list (so as not to duplicate work)
                    remoteTasks.deleteTask(currTask);
                }
            }

        }

        for (int i=0;i<remoteTasks.getTaskCount(); i++) {
            currTask = remoteTasks.getTask(i);
            if (currTask.isOwner(appUser)) { // Owner's file
                if (!localTasks.hasTask(currTask)) { // Remote, not local
                    server.delTask(currTask);
                } else { // In both
                    // Todo: Choose newest, update accordingly
                }
            } else { // Others file
                if (!localTasks.hasTask(currTask)) { // Remote, not local
                    fs.addTaskFile(currTask);
                } else { // In both
                    // Todo: Choose newest, update accordingly
                    // remove from remote list (so as not to duplicate work)
                    remoteTasks.deleteTask(currTask);
                }
            }
        }
    }

    private void syncBids() {
        Bid currBid;
        for (int i=0;i<localBids.getBidCount(); i++) {
            currBid = localBids.getBid(i);
            if (currBid.isOwner(appUser)) { // Owner's file
                if (!remoteBids.hasBid(currBid)) { // Local, not remote
                    server.addBid(currBid);
                } else { // In both
                    // Todo: Choose newest, update accordingly
                    // remove from remote list (so as not to duplicate work)
                    remoteBids.deleteBid(currBid);
                }
            } else { // Others file
                if (!remoteBids.hasBid(currBid)) { // Local, not remote
                    fs.deleteBidFile(currBid);
                } else { // In both
                    // Todo: Choose newest, update accordingly
                    // remove from remote list (so as not to duplicate work)
                    remoteBids.deleteBid(currBid);
                }
            }

        }

        for (int i=0;i<remoteBids.getBidCount(); i++) {
            currBid = remoteBids.getBid(i);
            if (currBid.isOwner(appUser)) { // Owner's file
                if (!localBids.hasBid(currBid)) { // Remote, not local
                    server.delBid(currBid);
                } else { // In both
                    // Todo: Choose newest, update accordingly
                }
            } else { // Others file
                if (!localBids.hasBid(currBid)) { // Remote, not local
                    fs.addBidFile(currBid);
                } else { // In both
                    // Todo: Choose newest, update accordingly
                    // remove from remote list (so as not to duplicate work)
                    remoteBids.deleteBid(currBid);
                }
            }
        }
    }


}
