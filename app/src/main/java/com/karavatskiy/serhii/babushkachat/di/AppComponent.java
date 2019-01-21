package com.karavatskiy.serhii.babushkachat.di;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
@Singleton
@Component(modules = {AndroidSupportInjectionModule.class, ActivityBindingModule.class, AppModule.class})
public interface AppComponent extends
        AndroidInjector<App> {

    @Override 
    void inject(App instance);

    @Component.Builder
    interface Builder {
        @BindsInstance
        AppComponent.Builder application(Application application);
        AppComponent build();
    }

}