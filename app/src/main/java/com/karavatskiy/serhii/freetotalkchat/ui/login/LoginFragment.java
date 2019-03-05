package com.karavatskiy.serhii.freetotalkchat.ui.login;

import static com.karavatskiy.serhii.freetotalkchat.ui.login.LoginFragmentPresenter.RC_SIGN_IN_GOOGLE;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import com.karavatskiy.serhii.freetotalkchat.R;
import com.karavatskiy.serhii.freetotalkchat.base.callback.OnCompleteListener;
import com.karavatskiy.serhii.freetotalkchat.base.ui.BaseFragmentDI;
import com.karavatskiy.serhii.freetotalkchat.utils.LogUtils;
import com.karavatskiy.serhii.freetotalkchat.utils.ValidatorSignIn;
import javax.inject.Inject;

public class LoginFragment extends BaseFragmentDI<LoginActivity>
        implements OnCompleteListener {

    public static final String TAG = "LoginFragment";

    private static final int RC_SIGN_IN_FACEBOOK = 64206;

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
    LoginFragmentPresenter presenter;
    @Inject
    ValidatorSignIn validatorSignIn;

    public static LoginFragment newInstance() {
        Bundle args = new Bundle();
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    protected void setupData() {
    }

    @Override
    protected void initOnClickListeners() {
        tvCreateAcc.setOnClickListener(v -> activity.showSignUpFragment());
        btnSignInFace.setOnClickListener(v ->
        {
            activity.showProgress(true);
            presenter.facebookSignIn(this);
        });
        btnSignInGoogle.setOnClickListener(v -> presenter.signInWithGoogle(this));
        btnSignIn.setOnClickListener(v -> {
            if (validate(etEmail.getEditableText().toString(), etPassword.getEditableText().toString())) {
                activity.showProgress(true);
                presenter.signInFirebaseEmail(etEmail.getEditableText().toString(),
                        etPassword.getEditableText().toString());
            }
        });
    }

    private boolean validate(String email, String password) {
        validatorSignIn.validateEmail(email, error -> etEmail.setError(error));
        validatorSignIn.validatePass(password, error -> etPassword.setError(error));

        return validatorSignIn.isLoginValid();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN_FACEBOOK) {
            presenter.facebookSignInResult(requestCode, resultCode, data);
        } else if (requestCode == RC_SIGN_IN_GOOGLE) {
            presenter.googleSignInResult(data);
        }
    }

    @Override
    public void onSuccess() {
        activity.showProgress(false);
        activity.goToChannelsList();
    }

    @Override
    public void onError(final Throwable throwable) {
        activity.showProgress(false);
        LogUtils.logDebug(TAG, "onError:" + throwable);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.disposeAll();
    }

}
