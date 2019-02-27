package com.karavatskiy.serhii.freetotalkchat.di;

import com.karavatskiy.serhii.freetotalkchat.ui.login.LoginActivity;
import com.karavatskiy.serhii.freetotalkchat.ui.login.LoginFragment;
import com.karavatskiy.serhii.freetotalkchat.ui.login.LoginFragmentModule;
import com.karavatskiy.serhii.freetotalkchat.ui.login.RegistrationFragment;
import com.karavatskiy.serhii.freetotalkchat.ui.login.RegistrationFragmentModule;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {

    @ContributesAndroidInjector()
    abstract LoginActivity bindsLoginActivity();

    @ContributesAndroidInjector(modules = LoginFragmentModule.class)
    abstract LoginFragment bindsLoginFragment();

    @ContributesAndroidInjector(modules = RegistrationFragmentModule.class)
    abstract RegistrationFragment bindsRegistrationFragment();
}