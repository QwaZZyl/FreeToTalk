package com.karavatskiy.serhii.babushkachat.ui.login;

import android.util.Log;
import com.google.firebase.auth.FirebaseAuth;
import com.karavatskiy.serhii.babushkachat.base.callback.OnCompleteListener;
import com.karavatskiy.serhii.babushkachat.utils.FirebaseRx;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Serhii on 12.01.2019.
 */
public class RegistrationFragmentPresenter {

    public static final String TAG = RegistrationFragmentPresenter.class.getName();

    private FirebaseAuth firebaseAuth;

    private OnCompleteListener onCompleteListener;

    RegistrationFragmentPresenter(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    public void setOnCompleteListener(OnCompleteListener onCompleteListener) {
        this.onCompleteListener = onCompleteListener;
        Log.w(TAG, "We there with dagger");
    }

    void requestRegistration(String email, String password) {
        Disposable disposable = FirebaseRx.rxCreateUserWithEmailAndPassword(firebaseAuth, email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(firebaseUser -> onCompleteListener.onSuccess(),
                        throwable -> onCompleteListener.onError(throwable));

    }
}
