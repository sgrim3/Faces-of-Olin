package com.example.root.facesofolin;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by root on 12/10/14.
 */
public class App extends Application {
    public static RequestQueue requestQueue;
    @Override
    public void onCreate() {
        super.onCreate();

        requestQueue = Volley.newRequestQueue(this);
    }
}
