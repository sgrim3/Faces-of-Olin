package com.example.root.facesofolin;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

public class StoryList extends Fragment {
    /**
     * Defines the list in which uploaded stories are shown
     * With each story's attributes being filled in via Firebase
     */

    private MainActivity activity;
    private ArrayList<Story> allStories = new ArrayList<Story>();
    private ArrayList<String> allTitles = new ArrayList<String>();
    private int dataSize = 0;
    private ListView list;

    public StoryList() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        //Inflate view
        final View rootView = inflater.inflate(R.layout.fragment_story_list, container, false);

        //Get list adapter
        final CustomList adapter = new CustomList(this.getActivity(), allStories, allTitles);

        //Initialize Firebase
        final Firebase firebase = new Firebase("https://boiling-inferno-4244.firebaseio.com/");
        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int counter = 0;

                //Fill in the values stored on Firebase for each story
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String time = child.getKey();
                    String story_text = child.child("story_text").getValue().toString();
                    String story_title = child.child("story_title").getValue().toString();
                    String location = child.child("location").getValue().toString();
                    String image = child.child("image_url").getValue().toString();
                    String image_caption = child.child("image_caption").getValue().toString();
                    String author = child.child("author").getValue().toString();

                    Story storyItem = new Story(time, location, story_text, story_title, image, image_caption, author);

                    counter++;

                    if (counter > dataSize) {
                        allStories.add(storyItem);
                        allTitles.add(story_title);
                        adapter.notifyDataSetChanged();
                    }
                }

                dataSize = counter;
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        list=(ListView)rootView.findViewById(R.id.story_list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            //When an item is clicked, show its StoryViewFragment
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                StoryViewFragment myDiag=new StoryViewFragment(allStories.get(position));
                myDiag.show(getFragmentManager(),"Diag");
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


