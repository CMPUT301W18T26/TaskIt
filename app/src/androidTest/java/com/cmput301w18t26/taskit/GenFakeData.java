package com.cmput301w18t26.taskit;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import java.io.File;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by kevingordon on 2018-03-08.
 */

public class GenFakeData extends ActivityInstrumentationTestCase2 {

        public GenFakeData(){
            super(HomeActivity.class);
        }

        public void testGen() {
            Context c = getInstrumentation().getTargetContext().getApplicationContext();
            // Typically from an activity a call would be more like:
            //   TaskItData db = TaskItData.getInstance(this);
            TaskItFile.setContext(c);
            TaskItFile.deleteAllFromFile();
            TaskItData db = TaskItData.getInstance();

            int randomNum;

            // Gen some users
            User user1 = new MockUser("Colin");
            User user2 = new MockUser("Kevin");
            User user3 = new MockUser("Sharon");
            User user4 = new MockUser("Julian");
            User user5 = new MockUser("Brady");
            User user6 = new MockUser("Michael");

            db.addUser(user1);
            db.addUser(user2);
            db.addUser(user3);
            db.addUser(user4);
            db.addUser(user5);
            db.addUser(user6);



            // Add Tasks to users
            UserList users = db.getUsers();
            String[] taskTitles = { "Fix the sink",
                                    "Get groceries",
                                    "Pickup dry cleaning",
                                    "Write an essay",
                                    "Babysitting",
                                    "Mow the lawn",
                                    "Clean the attic",
                                    "Hide the body"};
            User u;
            // for each user
            for (int i=0; i<users.getUserCount(); i++) {
                u = users.getUser(i);
                Log.d("GENDATA", u.getOwner());
                // Pick how many tasks to add for this user
                randomNum = ThreadLocalRandom.current().nextInt(1, 5 );
                for (int j=0; j<randomNum; j++) {
                    // pick a random task title
                    randomNum = ThreadLocalRandom.current().nextInt(0, taskTitles.length);
                    Task t = new MockTask(u.getOwner(), taskTitles[randomNum]);
                    db.addTask(t);
                }
            }

            // Add Bids to tasks
            TaskList tasks = db.getTasks();
            Task t;
            // For each task
            for (int i=0; i<tasks.getTaskCount(); i++) {
                t = tasks.getTask(i);
                // Pick how many bids to add to this task
                randomNum = ThreadLocalRandom.current().nextInt(1, 5 );
                for (int j=0; j<randomNum; j++) {
                    // Pick a random user to bid
                    randomNum = ThreadLocalRandom.current().nextInt(0, users.getUserCount());
                    u = users.getUser(randomNum);
                    Bid b = new MockBid(u.getOwner());
                    db.addBid(b);
                }
            }
            assertTrue(true);
        }


}
