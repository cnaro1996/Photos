package com.example.androidphotos;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidphotos.model.UserData.*;

import java.util.List;

public class SlideshowActivity extends AppCompatActivity {
    private ImageView imageView;
    private Button prevButton;
    private Button nextButton;
    private Button backButton;

    private int index;
    private Album currentAlbum;
    List<Photo> photos;


    private View.OnClickListener prevListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(null == photos) return;
            if(index - 1 >= 0) {
                index--;
                imageView.setImageURI(Uri.parse(photos.get(index).getUriString()));
            }
            setButtonSensitivities();
        }
    };

    private View.OnClickListener nextListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(null == photos) return;
            if(index + 1 <= photos.size()-1) {
                index++;
                imageView.setImageURI(Uri.parse(photos.get(index).getUriString()));
            }
            setButtonSensitivities();
        }
    };

    private View.OnClickListener backListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent myIntent = new Intent(SlideshowActivity.this, ViewAlbumActivity.class);
            myIntent.putExtra("ALBUM_NAME", currentAlbum.getName());
            SlideshowActivity.this.startActivity(myIntent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slideshow);
        currentAlbum = AndroidPhotos.getUserData().getAlbum(getIntent().getStringExtra("ALBUM_NAME"));

        imageView = (ImageView) findViewById(R.id.slideshowImageView);
        prevButton = (Button) findViewById(R.id.prevButton);
        nextButton = (Button) findViewById(R.id.nextButton);
        backButton = (Button) findViewById(R.id.backButton);

        backButton.setOnClickListener(backListener);
        prevButton.setOnClickListener(prevListener);
        nextButton.setOnClickListener(nextListener);

        index = 0;
        if(null != currentAlbum) {
            photos = currentAlbum.getPhotos();
            if(null != photos && !photos.isEmpty()) {
                imageView.setImageURI(Uri.parse(photos.get(index).getUriString()));
                setButtonSensitivities();
            }
        }
    }

    private void setButtonSensitivities() {
        if(index == 0) {
            prevButton.setEnabled(false);
            nextButton.setEnabled(true);
        } else if(index == photos.size()-1) {
            prevButton.setEnabled(true);
            nextButton.setEnabled(false);
        } else {
            prevButton.setEnabled(true);
            nextButton.setEnabled(true);
        }
    }
}
