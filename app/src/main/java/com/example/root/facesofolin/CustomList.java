package com.example.root.facesofolin;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

//popualating storylist
public class CustomList extends ArrayAdapter<String> {
    /**
     * Defines a custom list in which to show uploaded stories
     * Where each item is more complex than in a typical list
     */
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
        //Filling storylist view - each list item shows a story's title and location
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.story_list_single, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        TextView txtLocation = (TextView) rowView.findViewById(R.id.loc);
        txtTitle.setText(allTitles.get(position));
        txtLocation.setText(allStories.get(position).get_location());
        return rowView;
    }
}