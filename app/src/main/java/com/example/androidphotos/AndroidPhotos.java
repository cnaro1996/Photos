package com.example.androidphotos;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.androidphotos.model.UserData;

import java.io.File;
import java.io.IOException;

public class AndroidPhotos extends Application implements Application.ActivityLifecycleCallbacks {

    /**
     * The user data object
     */
    private static UserData userData;

    /**
     * The current state (activity) of the program
     */
    private static AppCompatActivity currentState;

    /**
     * The previous state (activity) of the program
     */
    private static AppCompatActivity prevState;

    private static Context context;

    private int activityRefs = 0;


    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(this);
        context = this.getApplicationContext();
        UserData.setContext(context);
        userData = UserData.readData();
    }

    public static UserData getUserData() {
        return userData;
    }

    public static void setUserData(UserData usr) {
        userData = usr;
        UserData.writeData(usr);
    }

    /**
     * Get the current state of the program
     *
     * @return the current state of the program
     */
    public static AppCompatActivity getCurrentState() {
        return currentState;
    }

    /**
     * Set the current state of the program
     *
     * @param activity the new current state to be set
     */
    public static void setCurrentState(AppCompatActivity activity) {
        currentState = activity;
    }

    /**
     * Get the previous state of the program
     *
     * @return the previous state of the program
     */
    public static AppCompatActivity getPrevState() {
        return prevState;
    }

    /**
     * Set the previous state of the program
     *
     * @param activity the new previous state to be set
     */
    public static void setPrevState(AppCompatActivity activity) {
        prevState = activity;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
        if(++activityRefs == 1 && !activity.isChangingConfigurations()) {
            //app is coming to the foreground
            userData = UserData.readData();
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        if(--activityRefs == 0 && !activity.isChangingConfigurations()) {
            //app is entering background
            UserData.writeData(userData);
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

}