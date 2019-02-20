package com.karavatskiy.serhii.babushkachat.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import com.karavatskiy.serhii.babushkachat.R;
import com.karavatskiy.serhii.babushkachat.base.ui.BaseActivityWithDI;
import com.karavatskiy.serhii.babushkachat.ui.channelList.ChannelListActivity;
import com.karavatskiy.serhii.babushkachat.utils.UiUtils;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import javax.inject.Inject;

public class LoginActivity extends BaseActivityWithDI implements HasSupportFragmentInjector {

    private boolean doubleClicked;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    public static void start(AppCompatActivity activity) {
        activity.startActivity(new Intent(activity, LoginActivity.class));
        activity.finish();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        showProgress();
    }

    @Override
    protected void setupUi() {
        LoginFragment showFragment = LoginFragment.newInstance();
        UiUtils.replaceFragment(getSupportFragmentManager(), R.id.fragmentPlace, showFragment, LoginFragment.TAG);
    }

    @Override
    protected void setupData() {

    }

    public void showSignUpFragment() {
        Fragment registrationFragment = RegistrationFragment.newInstance();
        UiUtils.replaceFragment(getSupportFragmentManager(), R.id.fragmentPlace, registrationFragment,
                RegistrationFragment.TAG);
    }

    @Override
    public AndroidInjector<android.support.v4.app.Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
    public void goToChannelList(){
        ChannelListActivity.start(this);
    }
    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            if (doubleClicked) {
                super.finish();
            }
            doubleClicked = true;
            UiUtils.toast(this,getString(R.string.message_double_click_exit));
            new Handler().postDelayed(() -> doubleClicked = false, 2000);
        }
    }
}
