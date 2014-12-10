package com.example.root.facesofolin;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by root on 12/9/14.
 */
public class CloudinaryApi {

    public String getTimestamp () {
        int time = (int) (System.currentTimeMillis());
        Timestamp tsTemp = new Timestamp(time);
        String ts =  tsTemp.toString();
        return ts;
    }

    public void UploadImages (String filename, String timestamp) throws IOException, NoSuchAlgorithmException {
        try {
            String sig = "public_id=" + timestamp + "&timestamp=" + timestamp + "xPE3O1CPu04ryh5uHj_TvI4xcc0";
            String hashSig = AeSimpleSHA1.SHA1(sig);


            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost("https://api.cloudinary.com/v1_1/demo/image/upload");

            List<NameValuePair> pairs = new CopyOnWriteArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("file", filename));
            pairs.add(new BasicNameValuePair("api_key", "382541929414289"));
            pairs.add(new BasicNameValuePair("timestamp", timestamp));
            pairs.add(new BasicNameValuePair("signature", hashSig));
            pairs.add(new BasicNameValuePair("public_id", timestamp));

            HttpResponse response = client.execute(post);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
