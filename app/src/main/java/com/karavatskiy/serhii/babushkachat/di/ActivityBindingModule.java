package com.karavatskiy.serhii.babushkachat.di;

import com.karavatskiy.serhii.babushkachat.ui.login.LoginActivity;
import com.karavatskiy.serhii.babushkachat.ui.login.LoginFragment;
import com.karavatskiy.serhii.babushkachat.ui.login.LoginFragmentModule;
import com.karavatskiy.serhii.babushkachat.ui.login.RegistrationFragment;
import com.karavatskiy.serhii.babushkachat.ui.login.RegistrationFragmentModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {
    
    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract LoginActivity bindsLoginActivity();
    @ContributesAndroidInjector(modules = LoginFragmentModule.class)
    abstract LoginFragment bindsLoginFragment();
    @ContributesAndroidInjector(modules = RegistrationFragmentModule.class)
    abstract RegistrationFragment bindsRegistrationFragment();
}