package com.karavatskiy.serhii.babushkachat.ui.login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.karavatskiy.serhii.babushkachat.R;
import com.karavatskiy.serhii.babushkachat.ui.UiUtils;

import javax.inject.Inject;

import static com.karavatskiy.serhii.babushkachat.ui.login.LoginFragment.TAG;

/**
 * Created by Serhii on 12.01.2019.
 */
public class LoginFragmentPresenter implements GoogleApiClient.OnConnectionFailedListener {

    static final int RC_SIGN_IN = 9001;

    private FirebaseAuth firebaseAuth;

    @Inject
    GoogleApiClient googleApiClient;

    private Fragment fragment;

    private OnSignInListener onSignInListener;

    LoginFragmentPresenter(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
    }


    public void setOnSignInListener(OnSignInListener onSignInListener) {
        this.onSignInListener = onSignInListener;
    }

    void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }


    void signInAction() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        fragment.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    void signInFireBaseEmail(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        onSignInListener.onSignInSuccess("Sign in success with mail");
                        onSignInListener.onSignInSuccess(user.getDisplayName());

                    } else {
                        onSignInListener.onSignInFailure("signInWithCredential:failure");
                    }

                    // ...
                });
    }

    void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        onSignInListener.onSignInSuccess("Sign in success");
                        onSignInListener.onSignInSuccess(user.getDisplayName());
                        //  updateUI(user);
                    } else {
                        // If sign in fails, display a message to the user.
                        onSignInListener.onSignInFailure("signInWithCredential:failure");
                    }
                });
    }


    void signOut() {
        UiUtils.toast(fragment.requireActivity(), "signout success");
        firebaseAuth.signOut();
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
