package com.example.androidphotos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class AndroidPhotos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent myIntent = new Intent(AndroidPhotos.this, HomeActivity.class);
        AndroidPhotos.this.startActivity(myIntent);
    }
}