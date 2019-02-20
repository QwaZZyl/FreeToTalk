package com.karavatskiy.serhii.babushkachat.base.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import dagger.android.AndroidInjection;

/**
 * Created by Serhii on 11.01.2019.
 */
public abstract class BaseActivityWithDI extends BaseActivity { // Remove 'with' or add to fragment

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
    }
}
