package com.cmput301w18t26.taskit;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by kevingordon on 2018-02-26.
 */

public class UserList {
    private ArrayList<User> users = new ArrayList<User>();

    public ArrayList<User> getTasks() {return users;}

    public void addUser(User user) {
        users.add(user);
    }

    public boolean hasUser(User user) {
        if (getIndex(user) > -1) {
            return true;
        } else {
            return false;
        }
    }

    public int getIndex(User user) {
        for (int i=0; i<users.size(); i++) {
            if (getUser(i).getUUID().equals(user.getUUID())) {
                return i;
            }
        }
        return -1;
    }

    public int getIndexByUsername(String username) {
        for (int i=0; i<users.size(); i++) {
            if (getUser(i).getUsername().equals(username)) {
                return i;
            }
        }
        return -1;
    }

    public int getIndex(String uuid) {
        for (int i=0; i<users.size(); i++) {
            if (getUser(i).getUUID().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    public User getUser(int index) {
        return users.get(index);
    }

    public User getUser(User user) {
        return users.get(getIndex(user));
    }

    public User getUser(String uuid) {return users.get(getIndex(uuid));}

    public User getUserByUsername(String username) {return users.get(getIndexByUsername(username));}

    public void deleteUser(User user) {
        int index = getIndex(user);

        if (index > -1) {
            users.remove(index);
        }
    }

    public int getUserCount() {
        return users.size();
    }

    public void addAll(Collection<User> l) {
        users.addAll(l);
    }
}
