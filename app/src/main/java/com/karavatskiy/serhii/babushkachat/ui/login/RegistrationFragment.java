package com.karavatskiy.serhii.babushkachat.ui.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.karavatskiy.serhii.babushkachat.R;
import com.karavatskiy.serhii.babushkachat.base.BaseFragmentDI;
import com.karavatskiy.serhii.babushkachat.ui.UiUtils;
import javax.inject.Inject;

public class RegistrationFragment extends BaseFragmentDI<LoginActivity> implements OnRegistrationCompliteListener {

    @BindView(R.id.btnCreateNewAcc)
    View btnCreateNewAcc;

    @BindView(R.id.tilSignUpEmail)
    TextInputLayout tilSignUpEmail;

    @BindView(R.id.tilSignUpUserName)
    TextInputLayout tilSignUpUserName;

    @BindView(R.id.tilSignUpPassword)
    TextInputLayout tilSignUpPassword;

    @BindView(R.id.tilSignUpConfirmPassword)
    TextInputLayout tilSignUpConfirmPassword;

    @Inject
    RegistrationFragmentPresenter registrationFragmentPresenter;

    private AppCompatEditText etEmail;

    private AppCompatEditText ettUserName;

    private AppCompatEditText etPassword;

    private AppCompatEditText etConfirmPassword;


    public static RegistrationFragment newInstance() {
        Bundle args = new Bundle();
        RegistrationFragment fragment = new RegistrationFragment();
        fragment.setArguments(args);

        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_registration, null);
        ButterKnife.bind(this, root);

        etEmail = (AppCompatEditText) tilSignUpEmail.getEditText();
        ettUserName = (AppCompatEditText) tilSignUpUserName.getEditText();
        etPassword = (AppCompatEditText) tilSignUpPassword.getEditText();
        etConfirmPassword = (AppCompatEditText) tilSignUpConfirmPassword.getEditText();
        initOnClicks();

        return root;
    }

    private void initOnClicks() {
        btnCreateNewAcc.setOnClickListener(v -> {
            if (validate(etEmail.getText().toString(), ettUserName.getText().toString(),
                    etPassword.getText().toString(), etConfirmPassword.getText().toString())) {
                registrationFragmentPresenter.requestRegistration(etEmail.getText().toString(),
                        etPassword.getText().toString());
            }
        });
    }


    private boolean validate(String email, String userName, String password, String confirmPassword) {
        // Reset errors.
        etEmail.setError(null);
        ettUserName.setError(null);
        etPassword.setError(null);
        etConfirmPassword.setError(null);

        Validator validator = new Validator();
        etEmail.setError(validator.validateEmail(email));
        ettUserName.setError(validator.validateUserName(userName));
        etPassword.setError(validator.validatePass(password));
        etConfirmPassword.setError(validator.validateConfirmPass(password, confirmPassword));

        return validator.isValidatedRegistration();
    }

    @Override
    public void onRegistrationSuccess(String msg) {
        UiUtils.toast(activity, msg);
    }

    @Override
    public void onRegistrationError(String msg) {
        UiUtils.toast(activity, msg);
    }
}