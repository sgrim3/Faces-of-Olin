package com.example.root.facesofolin;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by sihrc on 12/14/14.
 */
public class CloudinaryFinal extends StringRequest {
    // Path to the picture
    String picturePath;


    public CloudinaryFinal(String picturePath, final StringCallback callback) {
        super(Method.POST, "https://api.cloudinary.com/v1_1/djovbfmav/image/upload", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject res = new JSONObject(response);
                    callback.gotURL(res.getString("url"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("CloudRequest Failed", error.toString());
            }
        });

        this.picturePath = picturePath;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return new HashMap<String, String>() {{
            put("Content-Type", "application/x-www-form-urlencoded");
        }};
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        HashMap<String, String> params = new HashMap<String, String>(4);

        Bitmap bm = BitmapFactory.decodeFile(picturePath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
        byte[] b = baos.toByteArray();

        params.put("file", "data:image/jpeg;base64," + Base64.encodeToString(b, Base64.DEFAULT));
        String timestamp = String.valueOf(System.currentTimeMillis());
        params.put("timestamp", timestamp);
        try {
            params.put("signature", Utils.SHA1("timestamp=" + timestamp + "xPE3O1CPu04ryh5uHj_TvI4xcc0"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        params.put("api_key", "382541929414289");
        return params;
    }
}