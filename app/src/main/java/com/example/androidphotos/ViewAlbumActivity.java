package com.example.androidphotos;

import com.example.androidphotos.model.UserData.*;
import com.example.androidphotos.util.ImageGridAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import androidx.appcompat.app.AppCompatActivity;

//TODO: convert from using image file paths to image URI's instead.

public class ViewAlbumActivity extends AppCompatActivity {
    private Button addPhotoButton;
    private Button backButton;
    private GridView photosGrid;

    private Album album = AndroidPhotos.getUserData()
            .getAlbum(getIntent().getStringExtra("ALBUM_NAME"));

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
        photosGrid = (GridView) findViewById(R.id.photosGrid);

        photosGrid.setAdapter(new ImageGridAdapter(this, getPaths(album)));
        addPhotoButton.setOnClickListener(addListener);
        backButton.setOnClickListener(backListener);
    }

    //will be removed after merge.
    private String[] getPaths(Album album) {
        String[] paths = new String[album.getPhotos().size()];
        int i = 0;
        for(Photo photo : album.getPhotos()) {
            paths[i] = photo.getPath();
            i++;
        }
        return paths;
    }
}
