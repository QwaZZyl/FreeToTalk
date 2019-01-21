package com.karavatskiy.serhii.babushkachat.base;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import dagger.android.support.AndroidSupportInjection;

/**
 * Created by Serhii on 11.01.2019.
 */
public abstract class BaseFragmentDI<A extends AppCompatActivity> extends android.support.v4.app.Fragment {
    protected A activity;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
        this.activity = (A) context;
    }

}
