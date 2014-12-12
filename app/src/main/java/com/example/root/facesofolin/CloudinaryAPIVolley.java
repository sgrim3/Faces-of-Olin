package com.example.root.facesofolin;

import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

;

/**
 * Created by root on 12/9/14.
 */
public class CloudinaryAPIVolley {
    public static void UploadImages(final String filename, final String timestamp, final ResponseInformationCallback callback) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        String sig = "timestamp=" + timestamp + "xPE3O1CPu04ryh5uHj_TvI4xcc0";
        final String hashSig = Utils.SHA1(sig);

        JSONObject object = new JSONObject();

        try {
//            object.put("file", encodeTobase64(filename));
            object.put("file", "http://www.stanford.edu/dept/CTL/cgi-bin/academicskillscoaching/wp-content/uploads/2012/07/baby-duck.jpg");
            object.put("api_key", "382541929414289");
            object.put("timestamp", timestamp);
            object.put("signature", hashSig);
            Log.d("cloudinary", object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        //mPostCommentResponse.requestStarted();
        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST,"https://api.cloudinary.com/v1_1/djovbfmav/image/upload", object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // TODO - get public url / secure+url, public_id, width, height, format, resource_type,
                        try {
                            String public_url = response.getString("url");
                            String secure_url = response.getString("secure_url");
                            String public_id = response.getString("public_id");
                            String received_signature = response.getString("signature");
                            callback.handleResponse(public_url,secure_url,public_id,received_signature);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error != null) {
                    error.printStackTrace();
                    Log.i("DebugDebug", new String(error.networkResponse.data));
//                    Log.i("CloudinaryAPICall Error", error.);
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        App.requestQueue.add(sr);
    }


}