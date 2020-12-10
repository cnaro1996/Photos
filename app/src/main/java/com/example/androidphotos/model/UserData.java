package com.example.androidphotos.model;

import android.content.Context;
import android.net.Uri;

import com.example.androidphotos.util.Pair;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserData implements Serializable {
    public static class Photo implements Serializable {

        /**
         * The version id used to verify previously stored serialized
         * data matches the current class
         */
        private static final long serialVersionUID = 1L;

        private String name;

        private Uri imageData;
        /**
         * The list of key/value pairs (tags)
         * associated with the picture
         */
        private List<Pair<String, String>> tags;

        public Photo(Uri imageData) {
            this.imageData = imageData;
            this.tags = new ArrayList<Pair<String, String>>();
            this.name = "";
        }

        /**
         * Get the uri of the image
         *
         * @return the uri of the image
         */
        public Uri getImageData() {
            return this.imageData;
        }

        /**
         * Set the uri of the image
         *
         * @param imageData the uri to be set
         */
        public void setImageData(Uri imageData) {
            this.imageData = imageData;
        }

        /**
         * Get the tags associated with the picture
         *
         * @return a list of the tags associated with the picture
         */
        public List<Pair<String, String>> getTags() {
            return this.tags;
        }

        /**
         * Add a tag to be associated with the picture
         *
         * @param tag a key/value pair to be added as tag
         */
        public void addTag(Pair<String, String> tag) {
            this.tags.add(tag);
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof Photo)) {
                return false;
            }

            Photo photo = (Photo) o;
            return photo.imageData.equals(this.imageData);
        }
    }

    public class Album implements Serializable {

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

        public Album(String name) {
            this.name = name;
            this.photos = new ArrayList<Photo>();
        }

        /**
         * Get the name of the album
         *
         * @return the name of the album
         */
        public String getName() {
            return this.name;
        }

        /**
         * Set the name of the album
         *
         * @param name the name to be set
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * Get the photos in the album
         *
         * @return the list of photos in the album
         */
        public List<Photo> getPhotos() {
            return this.photos;
        }

        /**
         * Add a photo to the album
         *
         * @param photo the photo to be added
         * @return if the add was successful
         */
        public boolean addPhoto(Photo photo) {
            if (!photos.contains(photo)) {
                photos.add(photo);
                return true;
            }

            return false;
        }

        /**
         * Remove a photo from the album
         *
         * @param photo the photo to be deleted
         */
        public void removePhoto(Photo photo) {
            photos.remove(photo);
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof Album)) {
                return false;
            }

            Album album = (Album) o;
            return album.name.equals(this.name);
        }

        @Override
        public String toString() {
            return this.name;
        }
    }

        /**
         * The version id used to verify previously stored serialized
         * data matches the current class
         */
        private static final long serialVersionUID = 1L;

        private static Context context;

        /**
         * The path to the user's saved, serialized
         * data file
         */
        private static String serializedDataFile = "user.dat";

        /**
         * The albums this user has
         */
        private List<Album> albums;

        /**
         * The user-generated tags this user has created
         */
        private List<String> userTags;

        public UserData() {
            this.albums = new ArrayList<Album>();
            this.userTags = new ArrayList<String>();
            this.userTags.add("Location");
            this.userTags.add("Person");
        }

        /**
         * Reads data from the machine to load previously
         * saved user data
         *
         */
        public static UserData readData() {
            UserData data = null;
            if(null != context) {
                try{
                    FileInputStream fis = context.openFileInput(serializedDataFile);
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    data = (UserData) ois.readObject();
                    ois.close();
                    fis.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    data = new UserData();
                }
            }

            return data;
        }

        /**
         * Writes the data for all users to the machine to save for
         * the next time the program is run
         *
         */
        public static void writeData(UserData usr){
            if(null != context) {
                try {
                    FileOutputStream fos = context.openFileOutput(serializedDataFile, Context.MODE_PRIVATE);
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(usr);
                    oos.close();
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }

        /**
         * Create an album
         *
         */
        public boolean createAlbum(String name) {
            Album add = new Album(name);
            if(!this.albums.contains(add)) {
                albums.add(add);
                return true;
            }

            return false;
        }

        /**
         * Get the file path to the user's serialized
         * data file
         *
         * @return the file path to the user's serialized
         * data file
         */
        public static String getSerializedDataFile() {
            return serializedDataFile;
        }

        public static Context getContext() {
            return context;
        }

        public static void setContext(Context context) {
            UserData.context = context;
        }

        /**
         * Get the user's albums
         *
         * @return a list of the user's albums
         */
        public List<Album> getAlbums() {
            return this.albums;
        }

        /**
        * Fetches the album with @param name.
        * @param name The name of the album to be fetched
        * @return The album requested, or null if not found.
        */
        public Album getAlbum(String name) {
            Album match = null;
            for(Album album : albums) {
                if(album.toString().equals(name)){
                    match = album;
                    break;
                }
            }
            return match;
        }
    }


