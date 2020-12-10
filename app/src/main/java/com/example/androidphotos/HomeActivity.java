package com.example.androidphotos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

    private AdapterView.OnItemClickListener listViewListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(HomeActivity.this, ViewAlbumActivity.class);
            String name = parent.getAdapter().getItem(position).toString();
            intent.putExtra("ALBUM_NAME", name);
            AndroidPhotos.setPrevState(HomeActivity.this);
            HomeActivity.this.startActivity(intent);
        }
    };

    private View.OnClickListener createButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent myIntent = new Intent(HomeActivity.this, CreateRenameAlbumActivity.class);
            myIntent.putExtra("TYPE", "Create");
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

        albumList.setOnItemClickListener(listViewListener);
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
