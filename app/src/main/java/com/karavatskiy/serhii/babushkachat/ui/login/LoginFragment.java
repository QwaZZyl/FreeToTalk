package com.karavatskiy.serhii.babushkachat.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;
import com.karavatskiy.serhii.babushkachat.R;
import com.karavatskiy.serhii.babushkachat.base.BaseFragmentDI;
import com.karavatskiy.serhii.babushkachat.ui.UiUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.karavatskiy.serhii.babushkachat.ui.login.LoginFragmentPresenter.RC_SIGN_IN;

public class LoginFragment extends BaseFragmentDI<LoginActivity>
        implements GoogleApiClient.OnConnectionFailedListener, OnSignInListener {

    public static final String TAG = "LoginFragment";

    @BindView(R.id.btnLogin)
    View btnLogin;

    @BindView(R.id.btnSignInFace)
    View btnSignInFace;

    @BindView(R.id.btnSignInGoogle)
    View btnSignInGoogle;

    @BindView(R.id.tilLoginEmail)
    TextInputLayout tilLoginEmail;

    @BindView(R.id.tilLoginPassword)
    TextInputLayout tilLoginPassword;

    @BindView(R.id.tvCreateAcc)
    TextView tvCreateAcc;

    @Inject
    LoginFragmentPresenter loginFragmentPresenter;

    @Inject
    GoogleApiClient googleApiClient;

    private AppCompatEditText etEmail;

    private AppCompatEditText etPassword;

    public static LoginFragment newInstance() {
        Bundle args = new Bundle();
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, null);
        ButterKnife.bind(this, root);
        etEmail = (AppCompatEditText) tilLoginEmail.getEditText();
        etPassword = (AppCompatEditText) tilLoginPassword.getEditText();
        loginFragmentPresenter.setFragment(this);
        initOnClicks();
        return root;
    }

    private void initOnClicks() {
        tvCreateAcc.setOnClickListener(v -> activity.changeFragment());
        btnSignInFace.setOnClickListener(v -> loginFragmentPresenter.signOut());
        btnSignInGoogle.setOnClickListener(v -> {
            loginFragmentPresenter.signInAction();
        });
        btnLogin.setOnClickListener(v -> {
            if (check(etEmail.getText().toString(), etPassword.getText().toString()))
                loginFragmentPresenter
                        .signInFireBaseEmail(etEmail.getText().toString(), etPassword.getText().toString());
        });
    }

    private boolean check(String eMail, String password) {
        Validator validator = new Validator();
        etEmail.setError(null);
        etPassword.setError(null);
        etPassword.setError(validator.validatePass(password));
        etEmail.setError(validator.validateEmail(eMail));
        return validator.isValidatedLogin();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                loginFragmentPresenter.firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (googleApiClient != null) {
            googleApiClient.stopAutoManage(activity);
            googleApiClient.disconnect();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }


    @Override
    public void onSignInSuccess(String message) {
        UiUtils.toast(activity, message);
    }

    @Override
    public void onSignInFailure(String message) {
        UiUtils.toast(activity, message);
    }
}
