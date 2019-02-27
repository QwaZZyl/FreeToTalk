package com.karavatskiy.serhii.freetotalkchat.base.presenter;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Serhii on 24.02.2019.
 */
public abstract class BasePresenter {

    protected CompositeDisposable compositeDisposable;

    public BasePresenter() {
        compositeDisposable = new CompositeDisposable();
    }

    public void disposeAll() {
        compositeDisposable.clear();
    }
}
