package com.karavatskiy.serhii.freetotalkchat.base.callback;

/**
 * Created by Serhii on 15.01.2019.
 */
public interface OnResultListener<T> {

    void onSuccess(T result);

    void onError(Throwable throwable);
}
