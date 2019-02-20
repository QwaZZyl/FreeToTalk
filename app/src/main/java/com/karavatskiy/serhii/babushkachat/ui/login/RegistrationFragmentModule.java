package com.karavatskiy.serhii.babushkachat.ui.login;

import com.google.firebase.auth.FirebaseAuth;

import com.karavatskiy.serhii.babushkachat.base.callback.OnCompleteListener;
import com.karavatskiy.serhii.babushkachat.utils.ValidatorSignIn;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Serhii on 15.01.2019.
 */
@Module
public class RegistrationFragmentModule {

    @Provides
    OnCompleteListener provideOnRegistrationCompleteListener(RegistrationFragment registrationFragment) {
        return registrationFragment;
    }

    @Provides
    RegistrationFragmentPresenter provideRegistrationFragmentPresenter(FirebaseAuth auth,
            OnCompleteListener onCompleteListener) {
        RegistrationFragmentPresenter registrationFragmentPresenter =
                new RegistrationFragmentPresenter(auth);
        registrationFragmentPresenter.setOnCompleteListener(onCompleteListener);
        return registrationFragmentPresenter;
    }
    @Provides
    ValidatorSignIn provideValidatorSignIn(){
        return new ValidatorSignIn();
    }
}
