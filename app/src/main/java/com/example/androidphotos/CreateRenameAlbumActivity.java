package com.example.androidphotos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class CreateRenameAlbumActivity extends AppCompatActivity {
    private Button confirmOrCreateButton;
    private Button backButton;
    private EditText nameInput;

    private View.OnClickListener confirmOrCreateButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(null != nameInput && !nameInput.getText().toString().trim().isEmpty()){
                AndroidPhotos.getUserData().createAlbum(nameInput.getText().toString().trim());
            }
            Intent myIntent = new Intent(CreateRenameAlbumActivity.this, HomeActivity.class);
            CreateRenameAlbumActivity.this.startActivity(myIntent);
        }
    };

    private View.OnClickListener backListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent myIntent = new Intent(CreateRenameAlbumActivity.this, HomeActivity.class);
            CreateRenameAlbumActivity.this.startActivity(myIntent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_or_rename_album);

        confirmOrCreateButton = (Button) findViewById(R.id.confirmOrCreateButton);
        backButton = (Button) findViewById(R.id.backButton);
        nameInput = (EditText) findViewById((R.id.albumNameTextField));

        confirmOrCreateButton.setOnClickListener(confirmOrCreateButtonListener);
        backButton.setOnClickListener(backListener);
    }
}
