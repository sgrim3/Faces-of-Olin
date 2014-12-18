package com.example.root.facesofolin;

//Import packages
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class UploadHome extends Fragment {

    /**
     * Fragment which allows the user to choose whether they want
     * To upload a picture or write a story
     */

    //Initialize variables
    public static final int RESULT_LOAD_IMAGE = 1;
    public String filepath = "words";

    MainActivity activity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_upload_home, container, false);

        //Initiate all the buttons
        final Button storyButton = (Button) rootView.findViewById(R.id.story_button);
        final Button uploadPicButton = (Button) rootView.findViewById(R.id.choose_picture_button);

        //When the story button is pressed
        storyButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        StoryUploadFragment myDiag=new StoryUploadFragment();
                        myDiag.show(getFragmentManager(),"Diag");

                    }
                });

        //When the choose picture button is pressed
        uploadPicButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        //switch to library
                        Intent intent = new Intent(
                                Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, RESULT_LOAD_IMAGE);
                    }
                });

        return rootView;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == Activity.RESULT_OK && null != data) {
            //Saving filepath
            filepath = Utils.pickPicture(activity,data);
            activity.filepath = filepath;

            //Open imageupload dialog
            ImageUploadFragment myDiag=new ImageUploadFragment();
            myDiag.show(getFragmentManager(),"Diag");

        }
    }

    @Override
    public void onAttach(Activity activity) {
        this.activity = (MainActivity) activity;
        super.onAttach(activity);
    }
}
