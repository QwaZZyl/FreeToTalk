package com.karavatskiy.serhii.babushkachat.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import com.google.firebase.auth.FirebaseAuth;
import com.karavatskiy.serhii.babushkachat.R;
import com.karavatskiy.serhii.babushkachat.base.callback.OnCompleteListener;
import com.karavatskiy.serhii.babushkachat.base.ui.BaseFragmentDI;
import com.karavatskiy.serhii.babushkachat.utils.ValidatorSignIn;
import javax.inject.Inject;

public class LoginFragment extends BaseFragmentDI<LoginActivity>
        implements OnCompleteListener {

    public static final String TAG = "LoginFragment";

    @BindView(R.id.btnSignIn)
    View btnSignIn;

    @BindView(R.id.btnSignInFace)
    View btnSignInFace;

    @BindView(R.id.btnSignInGoogle)
    View btnSignInGoogle;

    @BindView(R.id.tvCreateAcc)
    TextView tvCreateAcc;

    @BindView(R.id.etEmail)
    AppCompatEditText etEmail;

    @BindView(R.id.etPassword)
    AppCompatEditText etPassword;

    @Inject
    LoginFragmentPresenter loginFragmentPresenter;

    @Inject
    FirebaseAuth firebaseAuth;

    @Inject
    ValidatorSignIn validatorSignIn;

    public static LoginFragment newInstance() {
        Bundle args = new Bundle();
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_login;
    }

    @Override
    protected void setupData() {
        activity.hideProgress();
    }

    @Override
    protected void initOnClickListeners() {
        tvCreateAcc.setOnClickListener(v -> activity.showSignUpFragment());
        btnSignInFace.setOnClickListener(v -> loginFragmentPresenter.facebookSignIn(this));
        btnSignInGoogle.setOnClickListener(v -> loginFragmentPresenter.signInWithGoogle(this));
        btnSignIn.setOnClickListener(v -> {
            if (validate(etEmail.getEditableText().toString(), etPassword.getEditableText().toString())) {
                loginFragmentPresenter.signInFirebaseEmail(etEmail.getEditableText().toString(),
                        etPassword.getEditableText().toString());
                activity.showProgress();
            }
        });
    }

    private boolean validate(String eMail, String password) {
        validatorSignIn.validateEmail(eMail, error -> etEmail.setError(error));
        validatorSignIn.validatePass(password, error -> etPassword.setError(error));
        return validatorSignIn.isLoginValid();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginFragmentPresenter.facebookSignInResult(requestCode, resultCode, data);
        loginFragmentPresenter.googleSignInResult(requestCode, data);
    }

    @Override
    public void onSuccess() {
        activity.hideProgress();
        activity.goToChannelList();
    }

    @Override
    public void onError(final Throwable throwable) {
        activity.hideProgress();
        Log.d(TAG, "onError:" + throwable);
    }

    @Override
    public void onPause() {
        super.onPause();
        loginFragmentPresenter.onPause(activity);
    }

    @Override
    public void onStop() {
        super.onStop();
        loginFragmentPresenter.disposeAll();
    }

}
