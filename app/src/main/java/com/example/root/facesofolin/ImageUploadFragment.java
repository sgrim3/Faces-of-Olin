package com.example.root.facesofolin;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.cloudinary.Util;
import com.firebase.client.Firebase;

import java.io.File;
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
        final EditText imageTagEditText = (EditText) rootView.findViewById(R.id.enter_tag_story);
        final EditText imageLocationEditText = (EditText) rootView.findViewById(R.id.story_upload_location);
        final Firebase firebase = new Firebase("https://olinadmissionsapp.firebaseio.com/");
        final UploadHome uploadHome = new UploadHome();


//      code from "http://stackoverflow.com/questions/4181774/show-image-view-from-file-path-in-android"
        File imgFile = new File(uploadHome.picturepath);

        if(imgFile.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

            ImageView myImage = (ImageView) rootView.findViewById(R.id.picture);

            myImage.setImageBitmap(myBitmap);
        }


        uploadImageButton.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick (View view) {
                        App.requestQueue.add(new CloudinaryFinal(uploadHome.picturepath, new CloudinaryFinal.StringCallback() {
                            @Override
                            public void gotURL(String url) {
                                //TODO - Save to firebase
                                Map<String, String> newItemMap = new HashMap<String, String>();
                                Map<String, Map<String, String>> newTitle = new HashMap<String, Map<String, String>>();

                                newItemMap.put("story_text", "n/a");
                                newItemMap.put("tags", imageTagEditText.getText().toString());
                                newItemMap.put("location", imageLocationEditText.getText().toString());
                                newItemMap.put("date", Utils.getDate());

                                firebase.child(imageTitleEditText.getText().toString()).setValue(newItemMap);
                            }
                        }));

//                                 activity.switchFragment(new UploadHome());
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