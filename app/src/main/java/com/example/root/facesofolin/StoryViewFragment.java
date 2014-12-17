package com.example.root.facesofolin;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

public class StoryViewFragment extends DialogFragment {
    Story story;
    MainActivity activity;

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
        // Inflate the layout for this fragment

        final View rootView = inflater.inflate(R.layout.fragment_story_view, container, false);
        final TextView storyTitle = (TextView) rootView.findViewById(R.id.title_story_view);
        final TextView storyText = (TextView) rootView.findViewById(R.id.story_text_view);
        final TextView storyLocation = (TextView) rootView.findViewById(R.id.story_view_location);
        final TextView imageCaption = (TextView) rootView.findViewById(R.id.caption_view);
//        final ImageView imageView = (ImageView) rootView.findViewById(R.id.image_view);



        //fill in views
        storyTitle.setText(story.get_title());
        storyText.setText(story.get_storytext());
        storyLocation.setText(story.get_location());
        imageCaption.setText(story.get_image_caption());


        //Get image

        NetworkImageView imgAvatar = (NetworkImageView) rootView.findViewById(R.id.image_view);
        imgAvatar.setImageUrl(story.get_image_url(), App.mImageLoader);



        return rootView;
    }

}