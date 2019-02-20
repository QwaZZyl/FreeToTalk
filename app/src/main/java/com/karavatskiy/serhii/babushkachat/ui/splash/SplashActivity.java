package com.karavatskiy.serhii.babushkachat.ui.splash;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.karavatskiy.serhii.babushkachat.ui.login.LoginActivity;

/**
 * Created by Serhii on 21.01.2019.
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LoginActivity.start(this);
    }

    @Override
    public void onBackPressed() {

    }
}
