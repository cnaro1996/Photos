package com.example.androidphotos;

import com.example.androidphotos.model.UserData;
import com.example.androidphotos.model.UserData.*;
import com.example.androidphotos.util.ImageGridAdapter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class ViewAlbumActivity extends AppCompatActivity {
    private Button addPhotoButton;
    private Button backButton;
    private ImageButton deleteAlbumButton;
    private Button renameAlbumButton;
    private Button viewSlideShowButton;
    private GridView photosGrid;

    private UserData user;
    private Album album;

    private AdapterView.OnItemClickListener photosGridListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(ViewAlbumActivity.this, DisplayPhotoActivity.class);
            intent.setData((Uri) parent.getAdapter().getItem(position));
            AndroidPhotos.setPrevState(ViewAlbumActivity.this);
            ViewAlbumActivity.this.startActivity(intent);
        }
    };

    private View.OnClickListener slideShowListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent myIntent = new Intent(ViewAlbumActivity.this, SlideshowActivity.class);
            myIntent.putExtra("ALBUM_NAME", album.getName());
            AndroidPhotos.setPrevState(ViewAlbumActivity.this);
            ViewAlbumActivity.this.startActivity(myIntent);
        }
    };

    private View.OnClickListener renameListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent myIntent = new Intent(ViewAlbumActivity.this, CreateRenameAlbumActivity.class);
            myIntent.putExtra("ALBUM_NAME", album.getName());
            myIntent.putExtra("TYPE", "Rename");
            AndroidPhotos.setPrevState(ViewAlbumActivity.this);
            ViewAlbumActivity.this.startActivity(myIntent);
        }
    };

    private View.OnClickListener deleteListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            user.removeAlbum(album.getName());
            AndroidPhotos.setUserData(user);
            Intent myIntent = new Intent(ViewAlbumActivity.this, HomeActivity.class);
            AndroidPhotos.setPrevState(ViewAlbumActivity.this);
            ViewAlbumActivity.this.startActivity(myIntent);
        }
    };

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
            myIntent.putExtra("ALBUM_NAME", album.getName());
            AndroidPhotos.setPrevState(ViewAlbumActivity.this);
            ViewAlbumActivity.this.startActivity(myIntent);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidPhotos.setCurrentState(ViewAlbumActivity.this);
        setContentView(R.layout.view_album);
        user = AndroidPhotos.getUserData();
        album = user.getAlbum(getIntent().getStringExtra("ALBUM_NAME"));

        renameAlbumButton = (Button) findViewById(R.id.renameAlbumButton);
        deleteAlbumButton = (ImageButton) findViewById(R.id.deleteAlbumButton);
        addPhotoButton = (Button) findViewById(R.id.addPhotoButton);
        viewSlideShowButton = (Button) findViewById(R.id.slideshowButton);
        backButton = (Button) findViewById(R.id.albumBackButton);
        photosGrid = (GridView) findViewById(R.id.photosGrid);

        renameAlbumButton.setOnClickListener(renameListener);
        deleteAlbumButton.setOnClickListener(deleteListener);
        addPhotoButton.setOnClickListener(addListener);
        viewSlideShowButton.setOnClickListener(slideShowListener);
        backButton.setOnClickListener(backListener);
        photosGrid.setAdapter(new ImageGridAdapter(ViewAlbumActivity.this, UserData.getURIs(album)));
    }
}
