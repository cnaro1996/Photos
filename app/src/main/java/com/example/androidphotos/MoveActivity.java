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

import java.util.List;

public class MoveActivity extends AppCompatActivity {
    private ListView albumList;
    private Button backButton;
    private UserData user;

    private View.OnClickListener backListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent myIntent = new Intent(MoveActivity.this, DisplayPhotoActivity.class);
            if(null != currentAlbum && null != currentPhoto) {
                myIntent.putExtra("ALBUM_NAME", currentAlbum.getName());
                myIntent.putExtra("PHOTO_URI", currentPhoto.getUriString());
            }
            MoveActivity.this.startActivity(myIntent);
        }
    };

    private AdapterView.OnItemClickListener listViewListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if(null != currentAlbum && null != currentPhoto) {
                Album toMove = (Album) parent.getAdapter().getItem(position);
                currentAlbum.removePhoto(currentPhoto);
                toMove.addPhoto(currentPhoto);
                AndroidPhotos.setUserData(user);

                Intent intent = new Intent(MoveActivity.this, ViewAlbumActivity.class);
                String name = parent.getAdapter().getItem(position).toString();
                intent.putExtra("ALBUM_NAME", name);
                MoveActivity.this.startActivity(intent);
            }
        }
    };

    private Album currentAlbum;
    private Photo currentPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.move_photo);
        setTitle("Move to Another Album");
        user = AndroidPhotos.getUserData();
        currentAlbum = user.getAlbum(getIntent().getStringExtra("ALBUM_NAME"));
        currentPhoto = null;
        for(Photo p : currentAlbum.getPhotos()){
            if(p.getUriString().equals(getIntent().getStringExtra("PHOTO_URI"))) {
                currentPhoto = p;
            }
        }

        albumList = (ListView) findViewById(R.id.albums_list);
        backButton = (Button) findViewById(R.id.backButton);
        albumList.setOnItemClickListener(listViewListener);
        backButton.setOnClickListener(backListener);
        initList();
    }

    private void initList(){
        List<Album> albums = user.getAlbums();
        ArrayAdapter<Album> arrayAdapter = new ArrayAdapter<Album>(
                this, android.R.layout.simple_list_item_1, albums);
        albumList.setAdapter(arrayAdapter);
    }
}
