package com.karavatskiy.serhii.freetotalkchat.ui.login;

import static com.karavatskiy.serhii.freetotalkchat.ui.login.LoginFragment.TAG;

import android.content.Intent;
import android.support.v4.app.Fragment;
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
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.karavatskiy.serhii.freetotalkchat.R;
import com.karavatskiy.serhii.freetotalkchat.base.callback.OnCompleteListener;
import com.karavatskiy.serhii.freetotalkchat.base.presenter.BasePresenter;
import com.karavatskiy.serhii.freetotalkchat.utils.FirebaseRx;
import com.karavatskiy.serhii.freetotalkchat.utils.LogUtils;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.Arrays;

/**
 * Created by Serhii on 12.01.2019.
 */
public class LoginFragmentPresenter extends BasePresenter {

    static final int RC_SIGN_IN_GOOGLE = 9001;

    private static final String PROFILE_FACEBOOK_PROPERTIES = "public_profile";

    private FirebaseAuth firebaseAuth;
    private GoogleApiClient googleApiClient;
    private OnCompleteListener onCompleteListener;
    private GoogleSignInOptions googleSignInOptions;
    private CallbackManager facebookCallbackManager;

    public LoginFragmentPresenter(FirebaseAuth firebaseAuth) {
        super();
        this.firebaseAuth = firebaseAuth;
    }

    private Single<AccessToken> rxSubscribeFacebook() {
        return Single.create(emitter -> {
            facebookCallbackManager = CallbackManager.Factory.create();
            LoginManager.getInstance().registerCallback(facebookCallbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    LogUtils.logDebug(TAG, "facebook:onSuccess:" + loginResult);
                    firebaseAuthWithFacebookCredential(loginResult.getAccessToken());
                    emitter.onSuccess(loginResult.getAccessToken());
                }

                @Override
                public void onCancel() {
                    LogUtils.logDebug(TAG, "onCancel:facebook cancel");
                }

                @Override
                public void onError(FacebookException error) {
                    emitter.onError(error);
                    LogUtils.logDebug(TAG, "facebook:onError" + error);
                }
            });

        });
    }

    void facebookSignIn(Fragment fragment) {
        rxSubscribeFacebook().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe();
        LoginManager.getInstance().logInWithReadPermissions(fragment, Arrays.asList(PROFILE_FACEBOOK_PROPERTIES));
    }

    void facebookSignInResult(int requestCode, int resultCode, Intent data) {
        facebookCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void firebaseAuthWithFacebookCredential(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        Disposable disposable = FirebaseRx.signInWithCredential(firebaseAuth, credential)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(firebaseUser -> {
                            onCompleteListener.onSuccess();
                            LogUtils.logDebug(TAG,
                                    "firebaseAuthWithFacebookCredential:" + firebaseUser.getDisplayName());
                        },
                        throwable -> onCompleteListener.onError(throwable));
        compositeDisposable.add(disposable);
    }

    void signInWithGoogle(LoginFragment loginFragment) {
        createGoogleApiClient(loginFragment);
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        loginFragment.startActivityForResult(signInIntent, RC_SIGN_IN_GOOGLE);
    }

    private void createGoogleApiClient(Fragment fragment) {
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(fragment.getString(R.string.firebase_id))
                .requestEmail()
                .build();
        googleApiClient = new Builder(fragment.requireContext()).enableAutoManage(fragment.requireActivity(),
                connectionResult -> LogUtils
                        .logDebug(TAG, "createGoogleApiClient: " + connectionResult.getErrorMessage()))
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions).build();
    }


    void googleSignInResult(Intent data) {
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            firebaseAuthWithGoogleCredential(account);
        } catch (ApiException e) {
            LogUtils.logDebug(TAG, "Google sign in failed" + e);
        }
    }

    private void firebaseAuthWithGoogleCredential(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        Disposable disposable = FirebaseRx.signInWithCredential(firebaseAuth, credential)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(firebaseUser -> {
                            onCompleteListener.onSuccess();
                            LogUtils.logDebug(TAG, "firebaseAuthWithGoogleCredential: " + firebaseUser.getEmail());
                        }
                        ,
                        throwable -> onCompleteListener.onError(throwable));
        compositeDisposable.add(disposable);
    }

    void signInFirebaseEmail(String email, String password) {
        Disposable disposable = FirebaseRx.signInWithEmailAndPassword(firebaseAuth, email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(firebaseUser -> onCompleteListener.onSuccess(),
                        throwable -> onCompleteListener.onError(throwable));
        compositeDisposable.add(disposable);
    }

    public void setOnCompleteListener(OnCompleteListener onCompleteListener) {
        this.onCompleteListener = onCompleteListener;
    }
}
