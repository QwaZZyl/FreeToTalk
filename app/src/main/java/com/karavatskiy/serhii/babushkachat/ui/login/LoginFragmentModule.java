package com.karavatskiy.serhii.babushkachat.ui.login;

import com.google.firebase.auth.FirebaseAuth;

import com.karavatskiy.serhii.babushkachat.base.callback.OnCompleteListener;
import com.karavatskiy.serhii.babushkachat.utils.ValidatorSignIn;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Serhii on 12.01.2019.
 */
@Module
public class LoginFragmentModule {

    @Provides
    OnCompleteListener providesOnCompleteListener(LoginFragment loginFragment) {
        return loginFragment;
    }

    @Provides
    LoginFragmentPresenter providesLoginFragmentPresenter(FirebaseAuth auth, OnCompleteListener onCompleteListener,
            LoginFragment loginFragment) {
        LoginFragmentPresenter loginFragmentPresenter = new LoginFragmentPresenter(auth,loginFragment);
        loginFragmentPresenter.setOnCompleteListener(onCompleteListener);
        return loginFragmentPresenter;
    }

    @Provides
    ValidatorSignIn provideValidatorSignIn() {
        return new ValidatorSignIn();
    }
}
