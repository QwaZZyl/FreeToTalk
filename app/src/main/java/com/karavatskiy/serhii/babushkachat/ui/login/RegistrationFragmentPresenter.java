package com.karavatskiy.serhii.babushkachat.ui.login;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Serhii on 12.01.2019.
 */
public class RegistrationFragmentPresenter {

    public static final String TAG = RegistrationFragmentPresenter.class.getName();

    private FirebaseAuth firebaseAuth;

    private OnRegistrationCompliteListener onRegistrationCompliteListener;

    RegistrationFragmentPresenter(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    public void setOnRegistrationCompliteListener(OnRegistrationCompliteListener onRegistrationCompliteListener) {
        this.onRegistrationCompliteListener = onRegistrationCompliteListener;
        Log.w(TAG, "We there with dagger");

    }

    void requestRegistration(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            onRegistrationCompliteListener.onRegistrationSuccess("createUserWithEmail:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            onRegistrationCompliteListener.onRegistrationError(task.getException().toString());
                        }

                        // ...
                    }
                });

    }
}
