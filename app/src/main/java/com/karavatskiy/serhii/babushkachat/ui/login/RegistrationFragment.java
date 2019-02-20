package com.karavatskiy.serhii.babushkachat.ui.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.karavatskiy.serhii.babushkachat.R;
import com.karavatskiy.serhii.babushkachat.base.callback.OnCompleteListener;
import com.karavatskiy.serhii.babushkachat.base.ui.BaseFragmentDI;
import com.karavatskiy.serhii.babushkachat.utils.ValidatorSignIn;
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
    RegistrationFragmentPresenter registrationFragmentPresenter;


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

        initOnClicks();

        return root;
    }

    private void initOnClicks() {
        btnCreateNewAcc.setOnClickListener(v -> {
            if (checkInputData(etEmail.getEditableText().toString(),
                    etUserName.getEditableText().toString(),
                    etPassword.getEditableText().toString(),
                    etConfirmPassword.getEditableText().toString())) {
                registrationFragmentPresenter.requestRegistration(etEmail.getEditableText().toString(),
                        etPassword.getEditableText().toString());
            }
        });
    }


    private boolean checkInputData(String email, String userName, String password, String confirmPassword) {

        validatorSignIn.validateEmail(email, error -> {
                    if (error != null) {
                        etEmail.setError(error);
                    } else {
                        etEmail.setError(null);
                    }
                }
        );
        validatorSignIn.validateUserName(userName, error -> {
            if (error != null) {
                etUserName.setError(error);
            } else {
                etUserName.setError(null);
            }
        });
        validatorSignIn.validatePass(password, error -> {
            if (error != null) {
                etPassword.setError(error);
            } else {
                etPassword.setError(null);
            }
        });
        validatorSignIn.validateConfirmPass(password, confirmPassword, error -> {
            if (error != null) {
                etConfirmPassword.setError(error);
            } else {
                etConfirmPassword.setError(null);
            }
        });

        return validatorSignIn.isValidatedRegistration();
    }

    @Override
    public void onSuccess() {
        activity.hideProgress();
        activity.goToChannelList();
    }

    @Override
    public void onError(final Throwable throwable) {
        activity.hideProgress();
        Log.d(TAG, "onError: " + throwable);
    }


}