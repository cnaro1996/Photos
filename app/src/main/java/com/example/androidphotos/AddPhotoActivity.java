package com.example.androidphotos;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    private Button editTagsButton;
    private Button saveButton;
    private ImageView imgView;
    private ListView tagList;
    private Album currentAlbum;

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

        initList();

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

    private void initList(){
        List<Pair> tags = new ArrayList<>();
        tags.add(new Pair("Key", "Value"));
        ArrayAdapter<Pair> arrayAdapter = new ArrayAdapter<Pair>(
                this, android.R.layout.simple_list_item_1, tags);
        tagList.setAdapter(arrayAdapter);
    }

}
