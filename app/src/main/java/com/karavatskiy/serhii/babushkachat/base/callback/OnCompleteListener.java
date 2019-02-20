package com.karavatskiy.serhii.babushkachat.base.callback;

/**
 * Created by Serhii on 15.01.2019.
 */
public interface OnCompleteListener {

    void onSuccess();

    void onError(Throwable throwable);
}
