package com.example.root.facesofolin;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ImageUploadFragment extends DialogFragment {
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
        final View rootView = inflater.inflate(R.layout.fragment_image_upload, container, false);
        final EditText imageTitleEditText = (EditText) rootView.findViewById(R.id.title_image);
        final Button uploadImageButton = (Button) rootView.findViewById(R.id.upload_image);
        final EditText imageCaptionEditText = (EditText) rootView.findViewById(R.id.caption_text);
//        final EditText imageTagEditText = (EditText) rootView.findViewById(R.id.enter_tag_story);
        final EditText imageLocationEditText = (EditText) rootView.findViewById(R.id.story_upload_location);
//        final Firebase firebase = new Firebase("https://olinadmissionsapp.firebaseio.com/");
        final Firebase firebase = new Firebase("https://boiling-inferno-4244.firebaseio.com/");


//      code from "http://stackoverflow.com/questions/4181774/show-image-view-from-file-path-in-android"
        File imgFile = new File(activity.filepath);

        if(imgFile.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

            ImageView myImage = (ImageView) rootView.findViewById(R.id.picture);

            myImage.setImageBitmap(myBitmap);
        }
        else {Log.v ("thing","broke");}


        uploadImageButton.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick (View view) {
                        //TODO-get filepath from UploadHome
                       App.requestQueue.add(new CloudinaryFinal(activity.filepath, new StringCallback() {
                            @Override
                            public void gotURL(String url) {
                                Map<String, String> newItemMap = new HashMap<String, String>();
                                Map<String, Map<String, String>> newTitle = new HashMap<String, Map<String, String>>();
                                newItemMap.put("story_text", "");
                                newItemMap.put ("image_url", url);
                                newItemMap.put ("image_caption", imageCaptionEditText.getText().toString());
//                                newItemMap.put("tags", imageTagEditText.getText().toString());
                                newItemMap.put("location", imageLocationEditText.getText().toString());
                                newItemMap.put("date", Utils.getDate());

                                firebase.child(imageTitleEditText.getText().toString()).setValue(newItemMap);

                                String title = imageTitleEditText.getText().toString();
                                if (title.matches("")) {
                                    Toast.makeText(activity, "Please enter a title", Toast.LENGTH_LONG).show();
                                }
                                else {
                                    firebase.child (title).setValue(newItemMap);
                                    getDialog().dismiss();
                                }
                            }
                        }));

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