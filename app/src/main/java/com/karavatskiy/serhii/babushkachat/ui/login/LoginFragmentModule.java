package com.karavatskiy.serhii.babushkachat.ui.login;

import android.content.Context;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.karavatskiy.serhii.babushkachat.R;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Serhii on 12.01.2019.
 */
@Module
public class LoginFragmentModule {

    @Provides
    OnSignInListener providesOnSignInListener(LoginFragment loginFragment) {
        return loginFragment;
    }

    @Provides
    GoogleApiClient provideGoogleApiClient(LoginFragment loginFragment) {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(loginFragment.getString(R.string.firebase_id))
                .requestEmail()
                .build();
        return new GoogleApiClient.Builder(loginFragment.requireContext())
                .enableAutoManage(loginFragment.requireActivity(), loginFragment)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();
    }

    @Provides
    LoginFragmentPresenter providesLoginFragmentPresenter(FirebaseAuth auth, OnSignInListener onSignInListener) {
        LoginFragmentPresenter loginFragmentPresenter = new LoginFragmentPresenter(auth);
        loginFragmentPresenter.setOnSignInListener(onSignInListener);
        return loginFragmentPresenter;
    }
}
