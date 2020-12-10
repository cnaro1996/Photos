package com.example.androidphotos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidphotos.model.UserData;
import com.example.androidphotos.model.UserData.*;

import java.io.IOException;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private Button createAlbumButton;
    private Button searchButton;
    private ListView albumList;

    private View.OnClickListener createButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent myIntent = new Intent(HomeActivity.this, CreateRenameAlbumActivity.class);
            AndroidPhotos.setPrevState(HomeActivity.this);
            HomeActivity.this.startActivity(myIntent);
        }
    };

    private View.OnClickListener searchButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent myIntent = new Intent(HomeActivity.this, SearchActivity.class);
            AndroidPhotos.setPrevState(HomeActivity.this);
            HomeActivity.this.startActivity(myIntent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidPhotos.setCurrentState(this);
        setContentView(R.layout.home);

        createAlbumButton = (Button) findViewById(R.id.createNewAlbumButton);
        searchButton = (Button) findViewById(R.id.searchForPhotosButton);
        albumList = (ListView) findViewById(R.id.albums_list);

        initList();

        createAlbumButton.setOnClickListener(createButtonListener);
        searchButton.setOnClickListener(searchButtonListener);
    }

    private void initList(){
        List<Album> albums = AndroidPhotos.getUserData().getAlbums();
        ArrayAdapter<Album> arrayAdapter = new ArrayAdapter<Album>(
                this, android.R.layout.simple_list_item_1, albums);
        albumList.setAdapter(arrayAdapter);
    }

}
