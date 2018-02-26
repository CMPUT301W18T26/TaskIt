package com.cmput301w18t26.taskit;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by kevingordon on 2018-02-26.
 */

public class UserListTest extends ActivityInstrumentationTestCase2 {
    public UserListTest() {
        super(UserActivity.class);
    }

    public void testTest() {
        assertTrue(true);
    }

    public void testAddUser() {
        UserList users = new UserList();
        User user = new User();
        users.addUser(user);

        assertTrue(users.hasUser(user));
    }

    public void testHasUser() {
        UserList users = new UserList();
        User user = new User();
        assertFalse(users.hasUser(user));
        users.addUser(user);
        assertTrue(users.hasUser(user));
    }

    public void testGetUser() {
        UserList users = new UserList();
        User user = new User();
        users.addUser(user);
        User returnedUser = users.getUser(0);
        assertEquals(user.getID(), returnedUser.getID());
    }


    public void testDelete() {
        UserList users = new UserList();
        User user = new User();
        users.addUser(user);
        users.deleteUser(user);
        assertFalse(users.hasUser(user));
    }

    public void testGetUserCount() {
        UserList users = new UserList();
        assertEquals(0, users.getUserCount());
        User user1 = new User();
        users.addUser(user1);
        assertEquals(1, users.getUserCount());
        User user2 = new User();
        users.addUser(user2);
        assertEquals(2, users.getUserCount());
        User user3 = new User();
        users.addUser(user3);
        assertEquals(3, users.getUserCount());
        users.deleteUser(user3);
        assertEquals(2, users.getUserCount());
        users.deleteUser(user2);
        assertEquals(1, users.getUserCount());
        users.deleteUser(user1);
        assertEquals(0, users.getUserCount());
    }
}
