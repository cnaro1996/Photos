package com.example.androidphotos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SearchActivity extends AppCompatActivity {
    private Button backButton;
    private Button searchButton;

    private View.OnClickListener backListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent myIntent = new Intent(SearchActivity.this, HomeActivity.class);
            SearchActivity.this.startActivity(myIntent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_photos);

        backButton = (Button) findViewById(R.id.searchBackButton);
        searchButton = (Button) findViewById(R.id.searchButton);

        backButton.setOnClickListener(backListener);
    }
}
