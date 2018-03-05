package com.cmput301w18t26.taskit;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by kevingordon on 2018-02-26.
 */

public class UserList {
    private ArrayList<User> users = new ArrayList<User>();
    public void addUser(User user) {
        users.add(user);
    }

    public boolean hasUser(User user) {
        for (int i=0; i<users.size(); i++) {
            if (getUser(i).getUsername().equals(user.getUsername())) {
                return true;
            }
        }
        return false;
    }

    public User getUser(int index) {
        return users.get(index);
    }

    public void deleteUser(User user) {
        users.remove(user);
    }

    public int getUserCount() {
        return users.size();
    }

    public int size() {
        return users.size();
    }
}
