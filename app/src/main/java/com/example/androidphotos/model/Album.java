package com.example.androidphotos.model;

import com.example.androidphotos.util.Pair;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Album implements Serializable {

    public static class Photo implements Serializable {

        /**
         * The version id used to verify previously stored serialized
         * data matches the current class
         */
        private static final long serialVersionUID = 1L;

        /**
         * The file path to the image
         */
        private String path;

        /**
         * The picture's caption
         */
        private String caption;

        /**
         * The date the picture was last modified
         */
        private LocalDate lastModified;

        /**
         * The list of key/value pairs (tags)
         * associated with the picture
         */
        private List<Pair<String,String>> tags; //IN PROGRESS

        public Photo(String path) {
            this.path = path;
            this.caption = "";
            File pic = new File(path);
            Date date;
            if(pic.exists()) {
                date = new Date(pic.lastModified());
            } else {
                date = new Date();
            }
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            this.lastModified = LocalDate.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
            this.tags = new ArrayList<Pair<String,String>>();
        }

        /**
         * Get the file path of the image
         *
         * @return the file path of the image
         */
        public String getPath() {
            return this.path;
        }

        /**
         * Set the file path of the image
         *
         * @param the file path to be set
         */
        public void setPath(String path) {
            this.path = path;
        }


        /**
         * Get the date the picture was last modified
         *
         * @return the date the picture was last modified
         */
        public LocalDate getLastModified() {
            return this.lastModified;
        }

        /**
         * Get the picture's caption
         *
         * @return the picture's caption
         */
        public String getCaption() {
            return this.caption;
        }

        /**
         * Set the picture's caption
         *
         * @param caption the caption to be set
         */
        public void setCaption(String caption) {
            this.caption = caption;
        }

        /**
         * Get the tags associated with the picture
         *
         * @return a list of the tags associated with the picture
         */
        public List<Pair<String,String>> getTags() {
            return this.tags;
        }

        /**
         * Add a tag to be associated with the picture
         *
         * @param tag a key/value pair to be added as tag
         */
        public void addTag(Pair<String,String> tag) {
            this.tags.add(tag);
        }

        @Override
        public boolean equals(Object o) {
            if(o == this) { return true; }
            if(!(o instanceof Photo)) { return false; }

            Photo photo = (Photo) o;
            return photo.path.equals(this.path);
        }
    }

    /**
     * The version id used to verify previously stored serialized
     * data matches the current class
     */
    private static final long serialVersionUID = 1L;

    /**
     * The name of the album
     */
    private String name;

    /**
     * The list of photos in the album
     */
    private List<Photo> photos;

    /**
     * A string formatted with the number
     * of photos in the album and date range of the
     * earliest and latest photos
     */
    private String props;

    public Album (String name) {
        this.name = name;
        this.photos = new ArrayList<Photo>();
        this.props = "0 Photos";
    }

    /**
     * Get the name of the album
     * @return the name of the album
     */
    public String getName() {
        return this.name;
    }

    /**
     * Set the name of the album
     * @param name the name to be set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the photos in the album
     * @return the list of photos in the album
     */
    public List<Photo> getPhotos() {
        return this.photos;
    }

    /**
     * Add a photo to the album
     * @param photo the photo to be added
     * @return if the add was successful
     */
    public boolean addPhoto(Photo photo) {
        if(!photos.contains(photo)) {
            photos.add(photo);
            updateProps();
            return true;
        }

        return false;
    }

    /**
     * Remove a photo from the album
     * @param photo the photo to be deleted
     */
    public void removePhoto(Photo photo) {
        photos.remove(photo);
        updateProps();
    }

    /**
     * Updates the props string
     */
    public void updateProps() {
        this.props = this.photos.size() + " Photos from "
                + getEarliestPhotoDate() + " to " + getLatestPhotoDate();
    }

    /**
     * Get the props string
     *
     * @return a formatted string with
     * the album's properties
     */
    public String getProps() {
        return this.props;
    }

    private LocalDate getEarliestPhotoDate() {
        if(null == photos || photos.isEmpty()) return null;
        Photo earliest = photos.get(0);
        for(int i = 1; i < photos.size(); i++) {
            if(photos.get(i).lastModified.compareTo(earliest.lastModified) < 0) {
                earliest = photos.get(i);
            }
        }

        return earliest.getLastModified();
    }

    private LocalDate getLatestPhotoDate() {
        if(null == photos || photos.isEmpty()) return null;
        Photo latest = photos.get(0);
        for(int i = 1; i < photos.size(); i++) {
            if(photos.get(i).lastModified.compareTo(latest.lastModified) > 0) {
                latest = photos.get(i);
            }
        }

        return latest.getLastModified();
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) { return true; }
        if(!(o instanceof Album)) { return false; }

        Album album = (Album) o;
        return album.name.equals(this.name);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
