package com.karavatskiy.serhii.freetotalkchat.base.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import dagger.android.AndroidInjection;

/**
 * Created by Serhii on 11.01.2019.
 */
public abstract class BaseActivityDI extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
    }
}
