package com.example.root.facesofolin;


//import packages
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class UploadHome extends Fragment {

    //initialize variables

    private Uri fileUri;   //filepath for pictures

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;
    public static final int RESULT_LOAD_IMAGE = 1;

    MainActivity activity;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_upload_home, container, false);

        //Initiate all the buttons!
        final Button storyButton = (Button) rootView.findViewById(R.id.story_button);
        final Button takePicButton = (Button) rootView.findViewById(R.id.take_picture_button);
        final Button uploadPicButton = (Button) rootView.findViewById(R.id.choose_picture_button);


        //when the story button is pressed
        storyButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        //switch to story fragment
                        //activity.switchFragment(new StoryUploadFragment());
                    }
                });

        //when the take picture button is pressed
        takePicButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        //switch to camera
                        // create Intent to take a picture and return control to the calling application
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        fileUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".png"));
//                        fileUri = MediaUtils.getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name
                        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

                    }
                });

        //when the choose picture button is pressed
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
//        CloudinaryStuff cloudinaryStuff = new CloudinaryStuff();
        CloudinaryApi cloudinaryApi = new CloudinaryApi();
//        CloudinaryAPIVolley cloudinaryAPIVolley = new CloudinaryAPIVolley();
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == Activity.RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = activity.getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            final String picturePath = cursor.getString(columnIndex);

            try {
                Log.d("Cloudinary", "made it to upload " +picturePath );
                CloudinaryAPIVolley.UploadImages(picturePath, Utils.getTimestamp(), new ResponseInformationCallback() {
                    @Override
                    public void handleResponse(String public_url, String secure_url, String public_id, String signature) {
                        System.out.println(public_id);
                        Log.d("Cloudinary",picturePath);
                    }
                });
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }


            cursor.close();
            //activity.switchFragment(new ImageUploadFragment());

//            Figure out where to put the picture
//            ImageView imageView = (ImageView) findViewById(R.id.imgView);
//            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }

        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                // Image captured and saved to fileUri specified in the Intent
                Toast.makeText(activity, "Image saved to:\n" +
                        fileUri, Toast.LENGTH_LONG).show();
//                Log.d("dropbox", "I AM uPLOADING");
//                DropboxStuff.uploadToDropbox(fileUri.toString());
//                Log.d("dropbox", "I UPLOADED");
//                activity.switchFragment(new ImageUploadFragment());
            } else if (resultCode == Activity.RESULT_CANCELED) {
                // User cancelled the image capture
            } else {
                // Image capture failed, advise user
            }
        }

        if (requestCode == CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                // Video captured and saved to fileUri specified in the Intent
                Toast.makeText(activity, "Video saved to:\n" +
                        data.getData(), Toast.LENGTH_LONG).show();
            } else if (resultCode == Activity.RESULT_CANCELED) {
                // User cancelled the video capture
                Toast.makeText(activity, "Capture Cancelled", Toast.LENGTH_LONG)
                        .show();
            } else {
                // Video capture failed, advise user
                Toast.makeText(activity, "Capture failed", Toast.LENGTH_LONG)
                        .show();
            }
        }
    }

    @Override
    public void onAttach(Activity activity) {
        this.activity = (MainActivity) activity;
        super.onAttach(activity);
    }
}
