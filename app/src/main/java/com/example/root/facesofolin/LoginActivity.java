package com.example.root.facesofolin;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.facebook.Session;

public class LoginActivity extends FragmentActivity {
    private LoginFragment loginFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            loginFragment = new LoginFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, loginFragment)
                    .commit();
        }
        else {
            loginFragment = (LoginFragment) getSupportFragmentManager().findFragmentById(android.R.id.content);
        }
    }

    public static void logOut(Context context) {
        Session session = Session.getActiveSession();

        if (session != null) {
            if (!session.isClosed()) {
                session.closeAndClearTokenInformation();
            }
        }
        else {
            session = new Session(context);
            Session.setActiveSession(session);

            session.closeAndClearTokenInformation();
        }
    }
}