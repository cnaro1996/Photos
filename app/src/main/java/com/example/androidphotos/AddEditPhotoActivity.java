package com.example.androidphotos;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidphotos.model.UserData;
import com.example.androidphotos.model.UserData.*;
import com.example.androidphotos.util.Pair;

public class AddEditPhotoActivity extends AppCompatActivity {

    private static final int GALLERY_REQUEST_CODE = 123;
    private UserData user;

    private Button backButton;
    private Button selectButton;
    private Button saveButton;
    private ImageView imgView;
    private CheckBox locationCB;
    private CheckBox personCB;
    private EditText locationInput;
    private EditText personInput;

    private Album currentAlbum;
    private Photo currentPhoto;
    private Uri uri;
    private String name;
    private int type; //0=add, 1=edit

    private View.OnClickListener backListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent myIntent;
            if(type == 1 && null != currentPhoto) {
                myIntent = new Intent(AddEditPhotoActivity.this, DisplayPhotoActivity.class);
                myIntent.putExtra("ALBUM_NAME", currentAlbum.getName());
                myIntent.putExtra("PHOTO_URI", currentPhoto.getUriString());
            } else {
                myIntent = new Intent(AddEditPhotoActivity.this, ViewAlbumActivity.class);
                myIntent.putExtra("ALBUM_NAME", currentAlbum.getName());
            }
            AddEditPhotoActivity.this.startActivity(myIntent);
        }
    };

    private View.OnClickListener selectListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_REQUEST_CODE);
        }
    };

    private View.OnClickListener saveListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(null != currentAlbum && null != uri) {
                Photo add = new Photo(uri);
                if(locationCB.isChecked() && !locationInput.getText().toString().trim().isEmpty()) {
                    add.addTag(new Pair<>("Location", locationInput.getText().toString().trim()));
                }
                if(personCB.isChecked() && !personInput.getText().toString().trim().isEmpty()) {
                    add.addTag(new Pair<>("Person", personInput.getText().toString().trim()));
                }
                add.setName(name);
                if(type == 1) {
                    currentAlbum.removePhoto(currentPhoto);
                }
                currentAlbum.addPhoto(add);
                AndroidPhotos.setUserData(user);
            }

            Intent myIntent = new Intent(AddEditPhotoActivity.this, ViewAlbumActivity.class);
            myIntent.putExtra("ALBUM_NAME", currentAlbum.getName());
            AddEditPhotoActivity.this.startActivity(myIntent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_photo);
        setTitle("Add Photo");
        currentAlbum = AndroidPhotos.getUserData().getAlbum(getIntent().getStringExtra("ALBUM_NAME"));
        String photoUri = getIntent().getStringExtra("PHOTO_URI");
        if(null != currentAlbum) {
            currentPhoto = currentAlbum.searchPhotoByUriString(photoUri);
        }
        type = getIntent().getIntExtra("TYPE", 0);
        user = AndroidPhotos.getUserData();

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
        saveButton.setOnClickListener(saveListener);

        //edit photo, not add
        if(type == 1 && null != currentPhoto) {
            setTitle("Edit Photo");
            selectButton.setEnabled(false);
            uri = Uri.parse(currentPhoto.getUriString());
            name = currentPhoto.getName();
            imgView.setImageURI(uri);

            for(Pair p : currentPhoto.getTags()) {
                if(p.getKey().equals("Location")) {
                    locationCB.setChecked(true);
                    locationInput.setText((CharSequence)p.getValue());
                } else if(p.getKey().equals("Person")) {
                    personCB.setChecked(true);
                    personInput.setText((CharSequence)p.getValue());
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && null != data) {
           Uri imageData = data.getData();
           name = getFileName(imageData);
           if(null == name) {
               if(null != currentAlbum) {
                   name = "Photo " + currentAlbum.getPhotos().size() + 1;
               } else {
                   name = "";
               }
           }
           uri = imageData;
           imgView.setImageURI(imageData);

        }
    }

    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }
}
