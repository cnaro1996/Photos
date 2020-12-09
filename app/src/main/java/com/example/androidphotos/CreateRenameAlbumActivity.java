package com.example.androidphotos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class CreateRenameAlbumActivity extends AppCompatActivity {
    private Button confirmOrCreateButton;
    private Button backButton;

    private View.OnClickListener confirmOrCreateButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

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

        confirmOrCreateButton.setOnClickListener(confirmOrCreateButtonListener);
        backButton.setOnClickListener(backListener);
    }
}
