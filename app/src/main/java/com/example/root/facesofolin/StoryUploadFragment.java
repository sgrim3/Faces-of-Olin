package com.example.root.facesofolin;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;

import java.util.HashMap;
import java.util.Map;

public class StoryUploadFragment extends DialogFragment {
    /**
     * The fragment in which the attributes of story are filled in
     * By the user and then uploaded and stored in Firebase
     */

    MainActivity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, android.R.style.Theme_Holo_Light);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        final View rootView = inflater.inflate(R.layout.fragment_story_upload, container, false);

        //Initializing views from xml
        final EditText storyTitleEditText = (EditText) rootView.findViewById(R.id.title_story);
        final Button uploadStoryButton = (Button) rootView.findViewById(R.id.upload_story);
        final EditText storyTextEditText = (EditText) rootView.findViewById(R.id.story_text);
        final EditText storyLocationEditText = (EditText) rootView.findViewById(R.id.story_upload_location);

        //Initialize firebase
        final Firebase firebase = new Firebase("https://boiling-inferno-4244.firebaseio.com/");

        //Upload button click
        uploadStoryButton.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick (View view) {

                        //upload everything to firebase
                        Map<String, String> newItemMap = new HashMap<String, String>();

                        newItemMap.put("story_text", storyTextEditText.getText().toString());
                        newItemMap.put ("story_title", storyTitleEditText.getText().toString());
                        newItemMap.put("image_url", "");
                        newItemMap.put("image_caption", "");
                        newItemMap.put("author", ((MainActivity)getActivity()).getUsername());
                        newItemMap.put("location", storyLocationEditText.getText().toString());

                        String date = Long.toString(System.currentTimeMillis());

                        firebase.child (date).setValue(newItemMap);

                        //close dialog
                        getDialog().dismiss();
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