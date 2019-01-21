package com.karavatskiy.serhii.babushkachat.di;

import android.app.Application;
import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    FirebaseAuth providesFirebaseAuth(Context context){
        return FirebaseAuth.getInstance();
    }
}