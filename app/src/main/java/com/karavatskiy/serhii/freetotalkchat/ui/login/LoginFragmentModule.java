package com.karavatskiy.serhii.freetotalkchat.ui.login;

import com.google.firebase.auth.FirebaseAuth;
import com.karavatskiy.serhii.freetotalkchat.base.callback.OnCompleteListener;
import com.karavatskiy.serhii.freetotalkchat.utils.ValidatorSignIn;
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
    LoginFragmentPresenter providesLoginFragmentPresenter(FirebaseAuth auth, OnCompleteListener onCompleteListener) {
        LoginFragmentPresenter loginFragmentPresenter = new LoginFragmentPresenter(auth);
        loginFragmentPresenter.setOnCompleteListener(onCompleteListener);

        return loginFragmentPresenter;
    }

    @Provides
    ValidatorSignIn provideValidatorSignIn() {
        return new ValidatorSignIn();
    }
}
