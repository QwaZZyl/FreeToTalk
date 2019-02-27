package com.karavatskiy.serhii.freetotalkchat.ui.login;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import com.karavatskiy.serhii.freetotalkchat.R;
import com.karavatskiy.serhii.freetotalkchat.base.ui.BaseActivityDI;
import com.karavatskiy.serhii.freetotalkchat.ui.channelsList.ChannelsListActivity;
import com.karavatskiy.serhii.freetotalkchat.utils.UiUtils;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import javax.inject.Inject;

public class LoginActivity extends BaseActivityDI implements HasSupportFragmentInjector {

    private boolean doubleClicked;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    public static void start(AppCompatActivity activity) {
        activity.startActivity(new Intent(activity, LoginActivity.class));
        activity.finish();
    }

    public void showSignUpFragment() {
        Fragment registrationFragment = RegistrationFragment.newInstance();
        UiUtils.replaceFragment(getSupportFragmentManager(), R.id.fragmentPlace, registrationFragment,
                RegistrationFragment.TAG);
    }

    public void goToChannelsList() {
        ChannelsListActivity.start(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void setupUi() {
        LoginFragment showFragment = LoginFragment.newInstance();
        UiUtils.replaceFragment(getSupportFragmentManager(), R.id.fragmentPlace, showFragment, LoginFragment.TAG);
    }

    @Override
    protected void setupData() {

    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            if (doubleClicked) {
                super.finish();
            }
            doubleClicked = true;
            UiUtils.toast(this, getString(R.string.message_double_click_exit));
            new Handler().postDelayed(() -> doubleClicked = false, 2000);
        }
    }
}
