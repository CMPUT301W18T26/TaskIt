package com.cmput301w18t26.taskit;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents a list of photos.
 * @author UAlberta-Cmput301-Team26 crew
 * @see Photo
 */
public class PhotoList {

    /**
     * List of photos.
     */
    private ArrayList<Photo> photos = new ArrayList<Photo>();

    /**
     * Some services (adapters in particular) need a list type.
     * @return the bare arraylist of photos.
     */
    public ArrayList<Photo> getPhotos() {return photos;}

    public void addPhoto(Photo photo) {
        photos.add(photo);
    }

    public boolean hasPhoto(Photo photo) {
        if (getIndex(photo) > -1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Find photos in photolist using their UUIDs
     * @param photo the photo to check for membership.
     * @return index of photo if found, -1 o.w.
     */
    public int getIndex(Photo photo) {
        for (int i=0; i<photos.size(); i++) {
            if (getPhoto(i).getUUID().equals(photo.getUUID())) {
                return i;
            }
        }
        return -1;
    }

    public Photo getPhoto(int index) {
        return photos.get(index);
    }

    public Photo getPhoto(Photo photo) {
        return photos.get(getIndex(photo));
    }

    public void deletePhoto(Photo photo) {
        int index = getIndex(photo);
        if (index > -1) {
            photos.remove(index);
        }
    }

    public int getPhotoCount() {
        return photos.size();
    }

    public void addAll(Collection<Photo> l) {
        photos.addAll(l);
    }

    public void addAll(PhotoList l) {
        photos.addAll(l.getPhotos());
    }

    public void clear() {
        photos.clear();
    }

}
