package com.example.androidphotos.util;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;


public class ImageGridAdapter extends BaseAdapter {
    private Context context;

    public Uri[] thumbURIs;

    public ImageGridAdapter(Context context, Uri[] URIs) {
        this.context = context;
        thumbURIs = URIs;
    }

    @Override
    public int getCount() {
        return thumbURIs.length;
    }

    @Override
    public Object getItem(int position) {
        return thumbURIs[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if(convertView == null) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(192,192));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            imageView = (ImageView) convertView;
        }
        imageView.setImageURI(thumbURIs[position]);

        Log.v("ImageGridAdapter", "Calling convertView " + convertView);

        return imageView;
    }
}
