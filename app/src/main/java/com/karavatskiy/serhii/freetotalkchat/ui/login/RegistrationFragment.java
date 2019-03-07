package com.karavatskiy.serhii.freetotalkchat.ui.login;

import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;
import butterknife.BindView;
import com.karavatskiy.serhii.freetotalkchat.R;
import com.karavatskiy.serhii.freetotalkchat.base.callback.OnCompleteListener;
import com.karavatskiy.serhii.freetotalkchat.base.ui.BaseFragmentDI;
import com.karavatskiy.serhii.freetotalkchat.utils.LogUtils;
import com.karavatskiy.serhii.freetotalkchat.utils.ValidatorSignIn;
import javax.inject.Inject;

public class RegistrationFragment extends BaseFragmentDI<LoginActivity> implements OnCompleteListener {

    public static final String TAG = "RegistrationFragment";

    @BindView(R.id.btnCreateNewAcc)
    View btnCreateNewAcc;
    @BindView(R.id.etEmail)
    AppCompatEditText etEmail;
    @BindView(R.id.etUserName)
    AppCompatEditText etUserName;
    @BindView(R.id.etPassword)
    AppCompatEditText etPassword;
    @BindView(R.id.etConfirmPassword)
    AppCompatEditText etConfirmPassword;

    @Inject
    ValidatorSignIn validatorSignIn;

    @Inject
    RegistrationFragmentPresenter presenter;


    public static RegistrationFragment newInstance() {
        Bundle args = new Bundle();
        RegistrationFragment fragment = new RegistrationFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_registration;
    }

    @Override
    protected void setupData() {
    }

    @Override
    protected void initOnClickListeners() {
        btnCreateNewAcc.setOnClickListener(v -> {
            if (checkInputData(etEmail.getEditableText().toString(),
                    etUserName.getEditableText().toString(),
                    etPassword.getEditableText().toString(),
                    etConfirmPassword.getEditableText().toString())) {
                activity.showProgress(true);
                presenter.requestRegistration(etEmail.getEditableText().toString(),
                        etPassword.getEditableText().toString());
            }
        });
    }

    private boolean checkInputData(String email, String userName, String password, String confirmPassword) {
        validatorSignIn.validateEmail(email, error -> etEmail.setError(error));
        validatorSignIn.validateUserName(userName, error -> etUserName.setError(error));
        validatorSignIn.validatePass(password, error -> etPassword.setError(error));
        validatorSignIn.validateConfirmPass(password, confirmPassword, error -> etConfirmPassword.setError(error));

        return validatorSignIn.isValidatedRegistration();
    }

    @Override
    public void onSuccess() {
        activity.showProgress(false);
        activity.goToChannelsList();
    }

    @Override
    public void onError(final Throwable throwable) {
        activity.showProgress(false);
        LogUtils.logDebug(TAG, "onError: " + throwable);
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.disposeAll();
    }
}