package com.karavatskiy.serhii.babushkachat.ui.login;

import com.google.firebase.auth.FirebaseAuth;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Serhii on 15.01.2019.
 */
@Module
public class RegistrationFragmentModule {

    @Provides
    OnRegistrationCompliteListener provideOnRegistrationCompleteListener(RegistrationFragment registrationFragment) {
        return registrationFragment;
    }

    @Provides
    RegistrationFragmentPresenter provideRegistrationFragmentPresenter(FirebaseAuth auth,
            OnRegistrationCompliteListener onRegistrationCompliteListener) {
        RegistrationFragmentPresenter registrationFragmentPresenter =
                new RegistrationFragmentPresenter(auth);
        registrationFragmentPresenter.setOnRegistrationCompliteListener(onRegistrationCompliteListener);
        return registrationFragmentPresenter;
    }
}
