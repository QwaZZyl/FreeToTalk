package com.karavatskiy.serhii.freetotalkchat.ui.login;

import com.google.firebase.auth.FirebaseAuth;
import com.karavatskiy.serhii.freetotalkchat.base.callback.OnCompleteListener;
import com.karavatskiy.serhii.freetotalkchat.base.presenter.BasePresenter;
import com.karavatskiy.serhii.freetotalkchat.utils.FirebaseRx;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Serhii on 12.01.2019.
 */
public class RegistrationFragmentPresenter extends BasePresenter {


    private FirebaseAuth firebaseAuth;
    private OnCompleteListener onCompleteListener;

    RegistrationFragmentPresenter(FirebaseAuth firebaseAuth) {
        super();
        this.firebaseAuth = firebaseAuth;
    }


    public void setOnCompleteListener(OnCompleteListener onCompleteListener) {
        this.onCompleteListener = onCompleteListener;
    }

    void requestRegistration(String email, String password) {
        Disposable disposable = FirebaseRx.createUserWithEmailAndPassword(firebaseAuth, email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(firebaseUser -> onCompleteListener.onSuccess(),
                        throwable -> onCompleteListener.onError(throwable));
        compositeDisposable.add(disposable);
    }
}
