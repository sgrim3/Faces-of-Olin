package com.example.root.facesofolin;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

public class StoryViewFragment extends DialogFragment {
    /**
     * Fragment in which a story shown in the story list can be seen
     * In more details, with its attributes filled in via Firebase
     */
    Story story;

    //Get the story
    public StoryViewFragment(Story story) {
        this.story = story;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, android.R.style.Theme_Holo_Light);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_story_view, container, false);
        final TextView storyTitle = (TextView) rootView.findViewById(R.id.title_story_view);
        final TextView storyText = (TextView) rootView.findViewById(R.id.story_text_view);
        final TextView storyLocation = (TextView) rootView.findViewById(R.id.story_view_location);
        final TextView imageCaption = (TextView) rootView.findViewById(R.id.caption_view);
        final TextView author = (TextView) rootView.findViewById(R.id.author_view);

        //Fill in the views
        storyTitle.setText(story.get_storytitle());
        storyText.setText(story.get_storytext());
        storyLocation.setText(story.get_location());
        imageCaption.setText(story.get_image_caption());
        author.setText(story.get_author());

        //Get the image
        NetworkImageView imgAvatar = (NetworkImageView) rootView.findViewById(R.id.image_view);
        imgAvatar.setImageUrl(story.get_image_url(), App.mImageLoader);

        return rootView;
    }

}