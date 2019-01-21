package com.karavatskiy.serhii.babushkachat.di;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class MainActivityModule {
    @Provides
    @Named("unique_string_id")
    static String provideName(){
        return "I love Medium";
    }

}