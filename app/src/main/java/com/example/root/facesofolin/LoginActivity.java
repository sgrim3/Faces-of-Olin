package com.example.root.facesofolin;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.facebook.Session;

public class LoginActivity extends FragmentActivity {
    private LoginFragment loginFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            // Brings up the Facebook login button
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

    // Returns to the main screen when the user opts to log out
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