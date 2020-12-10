package com.example.androidphotos.util;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.File;

//TODO: Need to convert from using image paths to image URI's.
public class ImageGridAdapter extends BaseAdapter {
    private Context context;

    public String[] thumbPaths;

    public ImageGridAdapter(Context context, String[] paths) {
        this.context = context;
        thumbPaths = paths;
    }

    @Override
    public int getCount() {
        return thumbPaths.length;
    }

    @Override
    public Object getItem(int position) {
        return thumbPaths[position];
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
            imageView.setLayoutParams(new ViewGroup.LayoutParams(192,192));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            imageView = (ImageView) convertView;
        }
        imageView.setImageBitmap(BitmapFactory
                .decodeFile(new File(thumbPaths[position]).getAbsolutePath()));

        return imageView;
    }
}
