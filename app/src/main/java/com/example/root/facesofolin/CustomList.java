package com.example.root.facesofolin;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomList extends ArrayAdapter<String> {
    private ArrayList<Story> allStories;
    private ArrayList<String> allTitles;
    private Activity context;

    public CustomList(Activity context, ArrayList<Story> allStories, ArrayList<String> allTitles) {
        super(context, R.layout.story_list_single, allTitles);

        this.allStories = allStories;
        this.allTitles = allTitles;
        this.context = context;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.story_list_single, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        //ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        TextView txtLocation = (TextView) rowView.findViewById(R.id.loc);
        txtTitle.setText(allTitles.get(position));
//        imageView.setImageResource(imageId.get(position));
        txtLocation.setText(allStories.get(position).get_location());

        return rowView;
    }
}