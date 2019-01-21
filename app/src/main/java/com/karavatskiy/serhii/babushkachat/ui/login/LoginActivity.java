package com.karavatskiy.serhii.babushkachat.ui.login;

import android.support.v4.app.Fragment;
import android.os.Bundle;

import com.karavatskiy.serhii.babushkachat.R;
import com.karavatskiy.serhii.babushkachat.base.BaseActivityWithDI;
import com.karavatskiy.serhii.babushkachat.ui.UiUtils;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class LoginActivity extends BaseActivityWithDI implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LoginFragment showFragment = LoginFragment.newInstance();
        UiUtils.replaceFragment(getSupportFragmentManager(), R.id.fragmentPlace, showFragment, "tst");

    }

    public void changeFragment() {
        Fragment registrationFragment = RegistrationFragment.newInstance();
        UiUtils.replaceFragment(getSupportFragmentManager(), R.id.fragmentPlace, registrationFragment, "tst");
    }

    @Override
    public AndroidInjector<android.support.v4.app.Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;

    }
}
