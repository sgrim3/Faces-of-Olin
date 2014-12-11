package com.example.root.facesofolin;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class ImageUploadFragment extends Fragment {
    MainActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_image_upload, container, false);
        final EditText imageTitleEditText = (EditText) rootView.findViewById(R.id.title_image);
        final Button uploadImageButton = (Button) rootView.findViewById(R.id.upload_image);
        final EditText imageCaptionEditText = (EditText) rootView.findViewById(R.id.caption_text);
        final EditText imageTagEditText = (EditText) rootView.findViewById(R.id.enter_tags1);
        final EditText imageLocationEditText = (EditText) rootView.findViewById(R.id.enter_location1);
//        final EditText imageBuildingEditText = (EditText) rootView.findViewById(R.id.enter_building_picture);
//        final EditText imageFloorEditText = (EditText) rootView.findViewById(R.id.enter_floor_picture);
//        final EditText imageRoomEditText = (EditText) rootView.findViewById(R.id.enter_room_picture);

//        final Button buildingButton = (Button) rootView.findViewById(R.id.building_button);
//        final Button floorButton = (Button) rootView.findViewById(R.id.floor_button);
//        final Button roomButton = (Button) rootView.findViewById(R.id.room_button);
        final Firebase firebase = new Firebase("https://olinadmissionsapp.firebaseio.com/");

        //Initialize firebase
//        firebase.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot child : dataSnapshot.getChildren()) {
//                    //ChatObject chat = new ChatObject(child.get)
//                }
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//
//            }
//        });

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




        uploadImageButton.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick (View view) {
                        try {
                            CloudinaryAPIVolley.UploadImages("hi", "hi", new ResponseInformationCallback() {
                                @Override
                                public void handleResponse(String public_url, String secure_url, String public_id, String signature) {
                                    Map<String, String> newItemMap = new HashMap<String, String>();
                                    Map<String, Map<String, String>> newTitle = new HashMap<String, Map<String, String>>();

                                    newItemMap.put("story_text", "N/A");
                                    newItemMap.put("tags", imageTagEditText.getText().toString());
                                    newItemMap.put("location", imageLocationEditText.getText().toString());
                                    newItemMap.put("image_public_url", public_url);
                                    newItemMap.put("image_secure_url", secure_url);
                                    newItemMap.put("image_public_id", public_id);
                                    newItemMap.put("image_signature", signature);
                                    newItemMap.put("image_caption", imageCaptionEditText.getText().toString());
                                    newItemMap.put("date", Utils.getDate());

                                    firebase.child(imageTitleEditText.getText().toString()).setValue(newItemMap);
                                }
                            });
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        }

                        //activity.switchFragment(new UploadHome());
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