package com.example.androidphotos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidphotos.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class EditTagsActivity extends AppCompatActivity {
    private Button backButton;
    private View.OnClickListener backListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent myIntent = new Intent(EditTagsActivity.this, AddPhotoActivity.class);
            EditTagsActivity.this.startActivity(myIntent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_tags);
        backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(backListener);

    }
}
