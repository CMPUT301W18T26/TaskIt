/*
 * Copyright 2018, Team 26 CMPUT 301. University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under the terms and coditions fo the Code of Student Behaviour at the University of Alberta.
 */

package com.cmput301w18t26.taskit;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by kevingordon on 2018-02-26.
 */

/**
 * Represents a list of users.
 * @author UAlberta-Cmput301-Team26 crew
 * @see User
 */
public class UserList {

    /**
     * List of users.
     */
    private ArrayList<User> users = new ArrayList<User>();

    /**
     * Some services (adapters in particular) need a list type.
     * @return the bare arraylist of users
     */
    public ArrayList<User> getUsers() {return users;}

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

    /**
     * Find users in userlist using their UUIDs
     * @param user the user to check for membership
     * @return index of user if found, -1 o.w.
     */
    public int getIndex(User user) {
        for (int i=0; i<users.size(); i++) {
            if (getUser(i).getUUID().equals(user.getUUID())) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Find users in userlist using their usernames
     * @param username of the user to check for membership
     * @return index of user if found, -1 o.w.
     */
    public int getIndexByUsername(String username) {
        for (int i=0; i<users.size(); i++) {
            if (getUser(i).getUsername().equals(username)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Find users in userlist using their UUIDs
     * @param uuid of the user to check for membership
     * @return index of user if found, -1 o.w.
     */
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

    public void addAll(UserList l) {users.addAll(l.getUsers());}

    public void clear() {
        users.clear();
    }
}
