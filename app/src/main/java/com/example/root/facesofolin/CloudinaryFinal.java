package com.example.root.facesofolin;

/**
 * Created by root on 12/14/14.
 */
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
        params.put("signature", SHA1("timestamp=" + timestamp + "xPE3O1CPu04ryh5uHj_TvI4xcc0"));
        params.put("api_key", "382541929414289");
        return params;
    }


    private static String convertToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (byte b : data) {
            int halfbyte = (b >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                buf.append((0 <= halfbyte) && (halfbyte <= 9) ? (char) ('0' + halfbyte) : (char) ('a' + (halfbyte - 10)));
                halfbyte = b & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    public static String SHA1(String text) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            assert md != null;
            md.update(text.getBytes("iso-8859-1"), 0, text.length());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte[] sha1hash = md.digest();
        return convertToHex(sha1hash);
    }

    public interface StringCallback {
        public void gotURL(String url);
    }
}