package com.example.androidphotos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ViewAlbumActivity extends AppCompatActivity {
    private Button addPhotoButton;
    private Button backButton;

    private View.OnClickListener backListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent myIntent = new Intent(ViewAlbumActivity.this, HomeActivity.class);
            AndroidPhotos.setPrevState(ViewAlbumActivity.this);
            ViewAlbumActivity.this.startActivity(myIntent);
        }
    };

    private View.OnClickListener addListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent myIntent = new Intent(ViewAlbumActivity.this, AddPhotoActivity.class);
            AndroidPhotos.setPrevState(ViewAlbumActivity.this);
            ViewAlbumActivity.this.startActivity(myIntent);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidPhotos.setCurrentState(ViewAlbumActivity.this);
        setContentView(R.layout.view_album);

        addPhotoButton = (Button) findViewById(R.id.addPhotoButton);
        backButton = (Button) findViewById(R.id.albumBackButton);

        addPhotoButton.setOnClickListener(addListener);
        backButton.setOnClickListener(backListener);
    }
}
