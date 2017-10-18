package com.practice.mnnit.init;

/**
 * Created by Shivani gupta on 10/13/2017.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class CustomAdapter extends BaseAdapter {
    Context context;
    int[] Images;
    LayoutInflater inflter;

    public CustomAdapter(Context applicationContext, int[] Images) {
        this.context = applicationContext;
        this.Images = Images;
        inflter = (LayoutInflater.from(applicationContext));

    }

    @Override
    public int getCount() {
        return Images.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = inflter.inflate(R.layout.list, null);
        ImageView Image = (ImageView) view.findViewById(R.id.imageView4);
        Image.setImageResource(Images[position]);
        return view;
    }
}
