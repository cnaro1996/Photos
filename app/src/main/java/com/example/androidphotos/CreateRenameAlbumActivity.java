package com.example.androidphotos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidphotos.model.UserData;

public class CreateRenameAlbumActivity extends AppCompatActivity {
    private Button confirmOrCreateButton;
    private Button backButton;
    private EditText nameInput;
    private TextView albumLabel;

    private UserData user;

    private Intent intent;

    private View.OnClickListener confirmOrCreateButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(null != nameInput && !nameInput.getText().toString().trim().isEmpty()){
                if(intent.getStringExtra("TYPE").equals("Rename")){
                    user.getAlbum(intent.getStringExtra("ALBUM_NAME")).
                            setName(nameInput.getText().toString());
                    AndroidPhotos.setUserData(user);
                    Intent myIntent = new Intent(CreateRenameAlbumActivity.this, ViewAlbumActivity.class);
                    myIntent.putExtra("ALBUM_NAME", nameInput.getText().toString());
                    CreateRenameAlbumActivity.this.startActivity(myIntent);
                } else {
                    AndroidPhotos.getUserData().createAlbum(nameInput.getText().toString().trim());
                    Intent myIntent = new Intent(CreateRenameAlbumActivity.this, HomeActivity.class);
                    CreateRenameAlbumActivity.this.startActivity(myIntent);
                }
            }
        }
    };

    private View.OnClickListener backListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(intent.getStringExtra("TYPE").equals("Rename")){
                Intent myIntent = new Intent(CreateRenameAlbumActivity.this, ViewAlbumActivity.class);
                CreateRenameAlbumActivity.this.startActivity(myIntent);
            } else {
                Intent myIntent = new Intent(CreateRenameAlbumActivity.this, HomeActivity.class);
                CreateRenameAlbumActivity.this.startActivity(myIntent);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_or_rename_album);
        user = AndroidPhotos.getUserData();

        intent = this.getIntent();

        albumLabel = (TextView) findViewById(R.id.albumLabel);
        confirmOrCreateButton = (Button) findViewById(R.id.confirmOrCreateButton);
        backButton = (Button) findViewById(R.id.backButton);
        nameInput = (EditText) findViewById((R.id.albumNameTextField));

        if(intent.getStringExtra("TYPE").equals("Rename")){
            albumLabel.setText("Rename Album");
        } else {
            albumLabel.setText("Create Album");
        }

        confirmOrCreateButton.setOnClickListener(confirmOrCreateButtonListener);
        backButton.setOnClickListener(backListener);
    }
}
