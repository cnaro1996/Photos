package com.example.androidphotos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidphotos.model.UserData.*;
import com.example.androidphotos.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class EditTagsActivity extends AppCompatActivity {
    private Button backButton;
    private Button saveButton;
    private CheckBox locationCB;
    private CheckBox personCB;
    private EditText locationInput;
    private EditText personInput;

    private Photo currentPhoto;

    private View.OnClickListener backListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent myIntent = new Intent(EditTagsActivity.this, DisplayPhotoActivity.class);
            EditTagsActivity.this.startActivity(myIntent);
        }
    };

    private View.OnClickListener saveListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(null != currentPhoto) {
                List<Pair<String, String>> newTags = new ArrayList<>();
                if(locationCB.isSelected() && !locationInput.getText().toString().trim().isEmpty()) {
                    newTags.add(new Pair<>("Location", locationInput.getText().toString().trim()));
                }
                if(personCB.isSelected() && !personInput.getText().toString().trim().isEmpty()) {
                    newTags.add(new Pair<>("Person", personInput.getText().toString().trim()));
                }
                currentPhoto.getTags().clear();
                currentPhoto.getTags().addAll(newTags);
            }

            Intent myIntent = new Intent(EditTagsActivity.this, DisplayPhotoActivity.class);
            EditTagsActivity.this.startActivity(myIntent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_tags);
        backButton = (Button) findViewById(R.id.backButton);
        saveButton = (Button) findViewById(R.id.savePhotoButton);
        locationCB = (CheckBox) findViewById(R.id.locationCB);
        personCB = (CheckBox) findViewById(R.id.personCB);
        locationInput = (EditText) findViewById(R.id.locationInput);
        personInput = (EditText) findViewById(R.id.personInput);
        backButton.setOnClickListener(backListener);
        saveButton.setOnClickListener(saveListener);

        if(null != currentPhoto) {
            for(Pair p : currentPhoto.getTags()) {
                if(p.getKey().equals("Location")) {
                    locationCB.setSelected(true);
                    locationInput.setText((CharSequence)p.getValue());
                } else if(p.getKey().equals("Person")) {
                    personCB.setSelected(true);
                    personInput.setText((CharSequence)p.getValue());
                }
            }
        }
    }
}
