package com.example.androidphotos;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidphotos.model.UserData.*;
import com.example.androidphotos.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddPhotoActivity extends AppCompatActivity {

    private static final int GALLERY_REQUEST_CODE = 123;
    private Button backButton;
    private Button selectButton;
    private Button saveButton;
    private ImageView imgView;
    private CheckBox locationCB;
    private CheckBox personCB;
    private EditText locationInput;
    private EditText personInput;

    private Album currentAlbum;
    private Uri uri;
    private String name;

    private View.OnClickListener backListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent myIntent = new Intent(AddPhotoActivity.this, ViewAlbumActivity.class);
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

    private View.OnClickListener saveListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(null != currentAlbum && null != uri) {
                Photo add = new Photo(uri);
                if(locationCB.isSelected() && !locationInput.getText().toString().trim().isEmpty()) {
                    add.addTag(new Pair<>("Location", locationInput.getText().toString().trim()));
                }
                if(personCB.isSelected() && !personInput.getText().toString().trim().isEmpty()) {
                    add.addTag(new Pair<>("Person", personInput.getText().toString().trim()));
                }
                add.setName(name);
                currentAlbum.addPhoto(add);
            }

            Intent myIntent = new Intent(AddPhotoActivity.this, ViewAlbumActivity.class);
            AddPhotoActivity.this.startActivity(myIntent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_photo);

        backButton = (Button) findViewById(R.id.backButton);
        selectButton = (Button) findViewById(R.id.selectButton);
        saveButton = (Button) findViewById(R.id.savePhotoButton);
        imgView = (ImageView) findViewById(R.id.addPhotoImageView);
        locationCB = (CheckBox) findViewById(R.id.locationCB);
        personCB = (CheckBox) findViewById(R.id.personCB);
        locationInput = (EditText) findViewById(R.id.locationInput);
        personInput = (EditText) findViewById(R.id.personInput);


        backButton.setOnClickListener(backListener);
        selectButton.setOnClickListener(selectListener);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && null != data) {
           Uri imageData = data.getData();
           Cursor returnCursor =
                   getContentResolver().query(imageData, null, null, null, null);
           int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
           name = returnCursor.getString(nameIndex);
           uri = imageData;
           imgView.setImageURI(imageData);

        }
    }
}
