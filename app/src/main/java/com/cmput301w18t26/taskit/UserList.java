package com.cmput301w18t26.taskit;

import java.util.ArrayList;

/**
 * Created by kevingordon on 2018-02-26.
 */

public class UserList {
    private ArrayList<User> users = new ArrayList<User>();
    public void addUser(User user) {
    }

    public boolean hasUser(User user) {
        return true;
    }

    public User getUser(int index) {
        return users.get(index);
    }

    public void deleteUser(User user) {
        users.remove(user);
    }

    public int getUserCount() {
        return 0;
    }
}
