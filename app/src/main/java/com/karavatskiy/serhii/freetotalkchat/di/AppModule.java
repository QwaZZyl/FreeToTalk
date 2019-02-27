package com.karavatskiy.serhii.freetotalkchat.di;

import android.app.Application;
import android.content.Context;
import com.google.firebase.auth.FirebaseAuth;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class AppModule {

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    FirebaseAuth providesFirebaseAuth(Context context) {
        return FirebaseAuth.getInstance();
    }
}