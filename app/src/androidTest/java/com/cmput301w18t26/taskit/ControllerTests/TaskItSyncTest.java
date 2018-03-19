package com.cmput301w18t26.taskit.ControllerTests;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;

import com.cmput301w18t26.taskit.HomeActivity;
import com.cmput301w18t26.taskit.MockUser;
import com.cmput301w18t26.taskit.TaskItData;
import com.cmput301w18t26.taskit.TaskItFile;
import com.cmput301w18t26.taskit.TaskItServer;
import com.cmput301w18t26.taskit.User;

import java.util.concurrent.TimeUnit;

/**
 * Created by kevingordon on 2018-03-10.
 */

public class TaskItSyncTest extends ActivityInstrumentationTestCase2 {
    /**
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
     * */

    public TaskItSyncTest(){
        super(HomeActivity.class);
    }

    public void testSyncOwnerLocalNotRemote() {
        Context c = getInstrumentation().getTargetContext().getApplicationContext();
        TaskItFile.setContext(c);
        TaskItFile.deleteAllFromFile();

//        TaskItServer.teardownServerJob teardownServer = new TaskItServer.teardownServerJob();
//        teardownServer.execute();
//
//        TaskItServer.setupServerJob setupServer = new TaskItServer.setupServerJob();
//        setupServer.execute();

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        TaskItData db = TaskItData.getInstance();

        String username = "foo";


        User u = new MockUser();

        u.setUsername(username);
        db.setCurrentUser(u);

        db.addUser(u);

        db.sync();

        // Now check the server -> foo should be there

    }

    public void testSyncOwnerRemoteNotLocal() {
        Context c = getInstrumentation().getTargetContext().getApplicationContext();
        TaskItFile.setContext(c);
        TaskItFile.deleteAllFromFile();

//        TaskItServer.teardownServerJob teardownServer = new TaskItServer.teardownServerJob();
//        teardownServer.execute();

//        TaskItServer.setupServerJob setupServer = new TaskItServer.setupServerJob();
//        setupServer.execute();

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        TaskItData db = TaskItData.getInstance();

        String username = "foo";


        User u = new MockUser();

        u.setUsername(username);
        db.setCurrentUser(u);

        db.addUser(u);

        db.sync();

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        db.deleteUser(u);

        db.sync();

        // Now check the server -> nothing should be there

    }



    public void testSyncNotOwnerRemoteNotLocal() {
        Context c = getInstrumentation().getTargetContext().getApplicationContext();
        TaskItFile.setContext(c);
        TaskItFile.deleteAllFromFile();

//        TaskItServer.teardownServerJob teardownServer = new TaskItServer.teardownServerJob();
//        teardownServer.execute();
//
//        TaskItServer.setupServerJob setupServer = new TaskItServer.setupServerJob();
//        setupServer.execute();

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        TaskItData db = TaskItData.getInstance();

        String username1 = "foo";
        String username2 = "bar";


        User u1 = new MockUser();
        User u2 = new MockUser();

        u1.setUsername(username1);
        u2.setUsername(username2);

        db.setCurrentUser(u1);

        db.addUser(u1);

        db.sync();

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        db.deleteUser(u1);

        db.setCurrentUser(u2);

        db.sync();

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertTrue(db.getUsers().hasUser(u1));

    }

    public void testSyncNotOwnerLocalNotRemote() {
        Context c = getInstrumentation().getTargetContext().getApplicationContext();
        TaskItFile.setContext(c);
        TaskItFile.deleteAllFromFile();

//        TaskItServer.teardownServerJob teardownServer = new TaskItServer.teardownServerJob();
//        teardownServer.execute();
//
//        TaskItServer.setupServerJob setupServer = new TaskItServer.setupServerJob();
//        setupServer.execute();

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        TaskItData db = TaskItData.getInstance();

        String username1 = "foo";
        String username2 = "bar";


        User u1 = new MockUser();
        User u2 = new MockUser();

        u1.setUsername(username1);
        u2.setUsername(username2);

        db.setCurrentUser(u1);

        db.addUser(u1);

        db.sync();

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        db.setCurrentUser(u2);
        TaskItServer.deleteUserJob deleteUser = new TaskItServer.deleteUserJob();
        deleteUser.execute(u1);

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        db.sync();

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertFalse(db.getUsers().hasUser(u1));

    }

}
