package com.karavatskiy.serhii.babushkachat.utils;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import io.reactivex.Single;

/**
 * Created by Serhii on 02.02.2019.
 */
public class FirebaseRx {

    public static Single<FirebaseUser> rxSignInWithEmailAndPassword(FirebaseAuth firebaseAuth,
            String email,
            String password) {
        return Single.create(emitter -> firebaseAuth
                .signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        emitter.onSuccess(user);
                    } else {
                        emitter.onError(task.getException());
                    }
                }));
    }

    public static Single<FirebaseUser> rxSignInWithCredential(FirebaseAuth firebaseAuth, AuthCredential credential) {
        return Single.create(emitter -> firebaseAuth
                .signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        emitter.onSuccess(user);
                    } else {
                        emitter.onError(task.getException());
                    }
                }));
    }

    public static Single<FirebaseUser> rxCreateUserWithEmailAndPassword(FirebaseAuth firebaseAuth, String email,
            String password) {
        return Single.create(emitter -> firebaseAuth
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        emitter.onSuccess(user);
                    } else {
                        emitter.onError(task.getException());
                    }
                }));
    }
}
