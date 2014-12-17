package com.example.root.facesofolin;

import android.app.Application;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by root on 12/10/14.
 */
public class App extends Application {
    public static RequestQueue requestQueue;
    public static ImageLoader mImageLoader;
    @Override
    public void onCreate() {
        super.onCreate();

        requestQueue = Volley.newRequestQueue(this);
        mImageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(10);

            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }

            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }
        });
    }
}
