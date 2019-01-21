package com.karavatskiy.serhii.babushkachat.ui.login;

/**
 * Created by Serhii on 15.01.2019.
 */
public interface OnSignInListener {
    void onSignInSuccess(String message);
    void onSignInFailure(String message);
}
