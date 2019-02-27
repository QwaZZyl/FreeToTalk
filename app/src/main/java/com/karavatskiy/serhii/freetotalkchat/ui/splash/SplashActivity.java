package com.karavatskiy.serhii.freetotalkchat.ui.splash;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.karavatskiy.serhii.freetotalkchat.ui.channelsList.ChannelsListActivity;
import com.karavatskiy.serhii.freetotalkchat.ui.login.LoginActivity;

/**
 * Created by Serhii on 21.01.2019.
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isAuthenticated()) {
            LoginActivity.start(this);
        } else {
            ChannelsListActivity.start(this);
        }
    }

    private boolean isAuthenticated() {
        return FirebaseAuth.getInstance().getCurrentUser() == null;
    }

    @Override
    public void onBackPressed() {
        //that method must be clear, so user can`t press back on splash screen
    }
}
