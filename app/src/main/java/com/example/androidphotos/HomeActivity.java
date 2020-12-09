package com.example.androidphotos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    private Button createAlbumButton;
    private Button searchButton;

    private View.OnClickListener createButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent myIntent = new Intent(HomeActivity.this, CreateRenameAlbumActivity.class);
            HomeActivity.this.startActivity(myIntent);
        }
    };

    private View.OnClickListener searchButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent myIntent = new Intent(HomeActivity.this, SearchActivity.class);
            HomeActivity.this.startActivity(myIntent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        createAlbumButton = (Button) findViewById(R.id.createNewAlbumButton);
        searchButton = (Button) findViewById(R.id.searchForPhotosButton);

        createAlbumButton.setOnClickListener(createButtonListener);
        searchButton.setOnClickListener(searchButtonListener);
    }
}
