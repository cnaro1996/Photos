package com.example.androidphotos;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidphotos.model.UserData.*;
import com.example.androidphotos.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class DisplayPhotoActivity extends AppCompatActivity {
    private ImageView imgView;
    private Button backButton;
    private Button editButton;
    private Button moveButton;
    private ListView tagList;
    private TextView photoName;

    private View.OnClickListener backListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent myIntent = new Intent(DisplayPhotoActivity.this, ViewAlbumActivity.class);
            myIntent.putExtra("ALBUM_NAME", currentAlbum.getName());
            DisplayPhotoActivity.this.startActivity(myIntent);
        }
    };

    private View.OnClickListener editListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            Intent myIntent = new Intent(DisplayPhotoActivity.this, AddEditPhotoActivity.class);
            myIntent.putExtra("ALBUM_NAME", currentAlbum.getName());
            myIntent.putExtra("PHOTO_URI", currentPhoto.getUriString());
            myIntent.putExtra("TYPE", 1);
            DisplayPhotoActivity.this.startActivity(myIntent);
        }
    };

    private Album currentAlbum;
    private Photo currentPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_photo);
        currentAlbum = AndroidPhotos.getUserData().getAlbum(getIntent().getStringExtra("ALBUM_NAME"));
        currentPhoto = null;
        for(Photo p : currentAlbum.getPhotos()){
            if(p.getUriString().equals(getIntent().getStringExtra("PHOTO_URI"))) {
                currentPhoto = p;
            }
        }

        imgView = (ImageView) findViewById(R.id.displayPhotoImageView);
        backButton = (Button) findViewById((R.id.backButton));
        editButton = (Button) findViewById(R.id.editPhotoButton);
        moveButton = (Button) findViewById(R.id.movePhotoButton);
        tagList = (ListView) findViewById((R.id.photoTagList));
        photoName = (TextView) findViewById(R.id.caption);

        backButton.setOnClickListener(backListener);
        editButton.setOnClickListener(editListener);
        if(null != currentPhoto) {
            imgView.setImageURI(Uri.parse(currentPhoto.getUriString()));
            photoName.setText((CharSequence)currentPhoto.getName());
            initList();
        }
    }

    private void initList(){
        List<Pair> tags = new ArrayList<>();
        tags.add(new Pair("Key", "Value"));
        tags.addAll(currentPhoto.getTags());
        ArrayAdapter<Pair> arrayAdapter = new ArrayAdapter<Pair>(
                this, android.R.layout.simple_list_item_1, tags);
        tagList.setAdapter(arrayAdapter);
    }
}
