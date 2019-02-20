package com.karavatskiy.serhii.babushkachat.ui.login;

import static com.karavatskiy.serhii.babushkachat.ui.login.LoginFragment.TAG;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.karavatskiy.serhii.babushkachat.R;
import com.karavatskiy.serhii.babushkachat.base.callback.OnCompleteListener;
import com.karavatskiy.serhii.babushkachat.utils.FirebaseRx;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.Arrays;

/**
 * Created by Serhii on 12.01.2019.
 */
public class LoginFragmentPresenter {

    static final int RC_SIGN_IN = 9001;

    private FirebaseAuth firebaseAuth;

    private GoogleApiClient googleApiClient;

    private OnCompleteListener onCompleteListener;

    private GoogleSignInOptions googleSignInOptions;

    private CompositeDisposable compositeDisposable;

    private CallbackManager facebookCallbackManager;

    public LoginFragmentPresenter(FirebaseAuth firebaseAuth, Fragment fragment) {
        this.firebaseAuth = firebaseAuth;
        compositeDisposable = new CompositeDisposable();
        setupSignInFacebook();
        createGoogleApiClient(fragment);
    }

    public void setupSignInFacebook() {
        facebookCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(facebookCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                firebaseAuthWithFacebookCredential(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
            }
        });
    }

    void facebookSignIn(Fragment fragment) {
        LoginManager.getInstance().logInWithReadPermissions(fragment, Arrays.asList("public_profile"));
    }

    void facebookSignInResult(int requestCode, int resultCode, Intent data) {
        facebookCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void firebaseAuthWithFacebookCredential(AccessToken token) {
        Log.d(TAG, "firebaseAuthWithFacebookCredential:" + token);
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        Disposable disposable = FirebaseRx.rxSignInWithCredential(firebaseAuth, credential)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(firebaseUser ->
                        Log.d(TAG, "firebaseAuthWithFacebookCredential: " + firebaseUser.getDisplayName()));
        compositeDisposable.add(disposable);
    }

    private void createGoogleApiClient(Fragment fragment) {
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(fragment.getString(R.string.firebase_id))
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(fragment.requireContext())
                .enableAutoManage(fragment.requireActivity(), connectionResult -> {

                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions).build();
    }

    void signInWithGoogle(LoginFragment loginFragment) {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        loginFragment.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    void googleSignInResult(int requestCode, Intent data) {
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogleCredential(account);
            } catch (ApiException e) {
                Log.w(TAG, "Google sign in failed", e); // Internal error
            }
        }
    }

    private void firebaseAuthWithGoogleCredential(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogleCredential:" + acct.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        Disposable disposable = FirebaseRx.rxSignInWithCredential(firebaseAuth, credential)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        firebaseUser -> Log.d(TAG, "firebaseAuthWithGoogleCredential: " + firebaseUser.getEmail()));
        compositeDisposable.add(disposable);
    }

    void signInFirebaseEmail(String email, String password) {
        Disposable disposable = FirebaseRx.rxSignInWithEmailAndPassword(firebaseAuth, email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(firebaseUser -> onCompleteListener.onSuccess(),
                        throwable -> onCompleteListener.onError(throwable));
        compositeDisposable.add(disposable);
    }


    void fullSignOut(Fragment fragment) {
        firebaseAuth.signOut();
        facebookSignOut();
        googleSignOut(fragment);
        Log.d(TAG, "fullSignOut: success");
    }

    private void facebookSignOut() {
        AccessToken token = AccessToken.getCurrentAccessToken();
        if (token != null) {
            LoginManager.getInstance().logOut();
        }

    }

    private void googleSignOut(Fragment fragment) {
        GoogleSignInClient googleSignInClient = GoogleSignIn
                .getClient(fragment.requireActivity(), googleSignInOptions);
        if (googleApiClient.isConnected()) {
            googleSignInClient.signOut();
        }
    }

    public void setOnCompleteListener(OnCompleteListener onCompleteListener) {
        this.onCompleteListener = onCompleteListener;
    }

    void disposeAll() {
        compositeDisposable.clear();
    }

    void onPause(FragmentActivity activity) {
        if (googleApiClient != null) {
            googleApiClient.stopAutoManage(activity);
            googleApiClient.disconnect();
        }
    }
}
