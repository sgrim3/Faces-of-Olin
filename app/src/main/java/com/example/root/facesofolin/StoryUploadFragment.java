package com.example.root.facesofolin;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class StoryUploadFragment extends DialogFragment {
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
        final EditText storyTitleEditText = (EditText) rootView.findViewById(R.id.title_story);
        final Button uploadStoryButton = (Button) rootView.findViewById(R.id.upload_story);
        final EditText storyTextEditText = (EditText) rootView.findViewById(R.id.story_text);
        final EditText storyLocationEditText = (EditText) rootView.findViewById(R.id.story_upload_location);

//        final EditText storyTagEditText = (EditText) rootView.findViewById(R.id.enter_tag_story);

//        final EditText storyBuildingEditText = (EditText) rootView.findViewById(R.id.enter_building_story);
//        final EditText storyFloorEditText = (EditText) rootView.findViewById(R.id.enter_floor_story);
//        final EditText storyRoomEditText = (EditText) rootView.findViewById(R.id.enter_room_story);

//        final Button buildingButton = (Button) rootView.findViewById(R.id.building_button);
//        final Button floorButton = (Button) rootView.findViewById(R.id.floor_button);
//        final Button roomButton = (Button) rootView.findViewById(R.id.room_button);


//        //Building button click
//        buildingButton.setOnClickListener(
//                new View.OnClickListener() {
//                    public void onClick(View view) {
//                        //drop down menu
//                        //send building to firebase
//                        //pick proper floor array
//                    }
//                });
//
//        //Floor button click
//        floorButton.setOnClickListener(
//                new View.OnClickListener() {
//                    public void onClick(View view) {
//                        //default to "choose building first"
//                        //drop down menu
//                        //send floor to firebase
//                        //choose correct room array
//                    }
//                });
//
//        //Room button click
//        roomButton.setOnClickListener(
//                new View.OnClickListener() {
//                    public void onClick(View view) {
//                        //default to "choose building and room first"
//                        //drop down menu
//                        //send room to firebase
//                    }
//                });

//        final Firebase firebase = new Firebase("https://olinadmissionsapp.firebaseio.com/stories");
        final Firebase firebase = new Firebase("https://boiling-inferno-4244.firebaseio.com/");

        //Upload button click
        uploadStoryButton.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick (View view) {
                    Map<String, String> newItemMap = new HashMap<String, String>();
                    Map<String, Map<String, String>> newTitle = new HashMap<String, Map<String, String>>();

                        newItemMap.put("story_text", storyTextEditText.getText().toString());
                        newItemMap.put("image_url", "");
                        newItemMap.put("image_caption", "");
//                        newItemMap.put("tags", storyTagEditText.getText().toString());
                        newItemMap.put("location", storyLocationEditText.getText().toString());
                        newItemMap.put("date", Utils.getDate());

                        String title = storyTitleEditText.getText().toString();
                        if (title.matches("")) {
                            Toast.makeText(activity, "Please enter a title", Toast.LENGTH_LONG).show();
                        }
                        else {
                            firebase.child (title).setValue(newItemMap);
                            Log.v ("title","should be there");
                            getDialog().dismiss();
                        }
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