package com.example.root.facesofolin;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.support.v4.app.Fragment;
import android.widget.TextView;

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
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_story_list, container, false);

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

//                Dialog dialog = new Dialog(getActivity());
//                dialog.setTitle("Dialog");
//
//                TextView storyTitle = (TextView) rootView.findViewById(R.id.title_story_view);
//                final TextView storyText = (TextView) rootView.findViewById(R.id.story_text_view);
//                final TextView storyLocation = (TextView) rootView.findViewById(R.id.story_view_location);
//                // final Button backButton = (Button) rootView.findViewById(R.id.story_back_button);
//
//                //fill in textviews
//                storyTitle.setText(allStories.get(position).get_title());
//                storyText.setText(allStories.get(position).get_storytext());
//                storyLocation.setText(allStories.get(position).get_location());
//                dialog.setContentView(R.layout.fragment_story_view);
//                dialog.show();
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


