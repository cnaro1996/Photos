package com.example.androidphotos;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidphotos.model.UserData.*;
import com.example.androidphotos.util.ImageGridAdapter;
import com.example.androidphotos.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private EditText tagsField;
    private Button backButton;
    private Button searchButton;
    private GridView searchResultsGrid;

    private AdapterView.OnItemClickListener searchResultsGridListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(SearchActivity.this, DisplayPhotoActivity.class);
            intent.setData((Uri) parent.getAdapter().getItem(position));
            AndroidPhotos.setPrevState(SearchActivity.this);
            SearchActivity.this.startActivity(intent);
        }
    };

    private View.OnClickListener searchListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            List<Photo> results = getPhotosFromTags();
            Uri[] photoURIs = getPhotoURIs(results);
            searchResultsGrid.setAdapter(new ImageGridAdapter(SearchActivity.this, photoURIs));
        }
    };

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
        setTitle("Search for Photos");

        tagsField = (EditText) findViewById(R.id.tagsField);
        backButton = (Button) findViewById(R.id.searchBackButton);
        searchButton = (Button) findViewById(R.id.searchButton);
        searchResultsGrid = (GridView) findViewById(R.id.searchResultsGrid);

        backButton.setOnClickListener(backListener);
        searchButton.setOnClickListener(searchListener);
        searchResultsGrid.setOnItemClickListener(searchResultsGridListener);
    }

    private Uri[] getPhotoURIs(List<Photo> results) {
        Uri[] URIs = new Uri[results.size()];
        int i = 0;
        for(Photo photo: results) {
            URIs[i] = Uri.parse(photo.getUriString());
        }
        return URIs;
    }

    private List<Photo> getPhotosFromTags() {
        List<Photo> results = new ArrayList<>();
        String tagsInput = tagsField.getText().toString();
        String tagExpression;

        if(!tagsInput.equals("")) {
            tagExpression = tagsInput;
            if(tagExpression.contains("AND")){
                String[] tags = tagExpression.split("AND");
                List<Photo> first = getPhotosFromTag(parseTag(tags[0].trim()));
                List<Photo> second = getPhotosFromTag(parseTag(tags[1].trim()));
                for(Photo p : first) {
                    if(second.contains(p)){
                        results.add(p);
                    }
                }
            } else if(tagExpression.contains("OR")) {
                String[] tags = tagExpression.split("OR");
                results.addAll(getPhotosFromTag(parseTag(tags[0].trim())));
                List<Photo> second = getPhotosFromTag(parseTag(tags[1].trim()));
                for(Photo p : second) {
                    if(!results.contains(p)){
                        results.add(p);
                    }
                }
            } else {
                results.addAll(getPhotosFromTag(parseTag(tagExpression)));
            }
        }
        return results;
    }

    private List<Photo> getPhotosFromTag(Pair<String, String> tag) {
        List<Photo> results = new ArrayList<Photo>();
        List<Album> albums = AndroidPhotos.getUserData().getAlbums();
        List<Pair<String,String>> tags;

        for(Album a: albums) {
            for(Photo p: a.getPhotos()) {
                tags = p.getTags();
                for(Pair<String,String> t : tags) {
                    if(t.getKey().equals(tag.getKey()) && t.getValue().contains(tag.getValue())){
                        results.add(p);
                        break;
                    }
                }
            }
        }

        return results;
    }

    private Pair<String,String> parseTag(String s) {
        String [] parse = s.split("=");
        return new Pair(parse[0], parse[1]);
    }
}
