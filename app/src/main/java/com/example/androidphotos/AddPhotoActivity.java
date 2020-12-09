package com.example.androidphotos;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class AddPhotoActivity extends AppCompatActivity {

    private static final int GALLERY_REQUEST_CODE = 123;
    private Button backButton;
    private Button selectButton;
    private Button editTagsButton;
    private Button saveButton;
    private ImageView imgView;
    private ListView tagList;

    private View.OnClickListener backListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent myIntent = new Intent(AddPhotoActivity.this, HomeActivity.class);
            AddPhotoActivity.this.startActivity(myIntent);
        }
    };

    private View.OnClickListener selectListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_REQUEST_CODE);
        }
    };

    private View.OnClickListener editTagsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent myIntent = new Intent(AddPhotoActivity.this, EditTagsActivity.class);
            AddPhotoActivity.this.startActivity(myIntent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_photo);

        backButton = (Button) findViewById(R.id.backButton);
        selectButton = (Button) findViewById(R.id.selectButton);
        editTagsButton = (Button) findViewById(R.id.editTagsButton);
        saveButton = (Button) findViewById(R.id.savePhotoButton);
        imgView = (ImageView) findViewById(R.id.addPhotoImageView);
        tagList = (ListView) findViewById(R.id.photoTagList);

        backButton.setOnClickListener(backListener);
        selectButton.setOnClickListener(selectListener);
        editTagsButton.setOnClickListener(editTagsListener);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && null != data) {
           Uri imageData = data.getData();
           imgView.setImageURI(imageData);
        }
    }
}
