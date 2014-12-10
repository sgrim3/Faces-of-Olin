package com.example.root.facesofolin;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.support.v4.app.Fragment;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

public class StoryList extends Fragment {
    MainActivity activity;
    ArrayList<Story> allStories = new ArrayList<Story>();
    ArrayList<String> allTitles = new ArrayList<String>();
    ListView list;

    public StoryList() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_story_list, container, false);

        final CustomList adapter = new CustomList(this.getActivity(), allStories, allTitles);

        final Firebase firebase = new Firebase("https://olinadmissionsapp.firebaseio.com/stories");

        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String title = child.getKey();
                    String story_text = child.child("story_text").getValue().toString();
                    String date = child.child("date").getValue().toString();
                    String location = child.child("location").getValue().toString();
                    String image = child.child("image").getValue().toString();
                    String image_caption = child.child("image_caption").getValue().toString();

                    Story storyItem = new Story(title, location, story_text, date, image, image_caption);

                    allStories.add(storyItem);
                    allTitles.add(title);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        list=(ListView)rootView.findViewById(R.id.story_list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                //if story, switch to story, if image switch to image
                //activity.switchFragment(new StoryViewFragment(allStories.get(position)));
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        this.activity = (MainActivity) activity;
        super.onAttach(activity);
    }
}


