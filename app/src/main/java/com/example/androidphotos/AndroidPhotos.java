package com.example.androidphotos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.androidphotos.model.UserData;

import java.io.IOException;

public class AndroidPhotos extends AppCompatActivity {

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            userData = UserData.readData();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Intent myIntent = new Intent(AndroidPhotos.this, HomeActivity.class);
        AndroidPhotos.setPrevState(this);
        AndroidPhotos.this.startActivity(myIntent);
    }

    public static UserData getUserData() {
        return userData;
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
}