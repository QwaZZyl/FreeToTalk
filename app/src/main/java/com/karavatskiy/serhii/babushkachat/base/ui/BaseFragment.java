package com.karavatskiy.serhii.babushkachat.base.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;

/**
 * Created by Serhii on 20.02.2019.
 */
public abstract class BaseFragment<A extends AppCompatActivity> extends Fragment {

    protected A activity;

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        this.activity = (A) context;
    }

    protected abstract int getLayout();

    protected abstract void setupData();

    protected abstract void initOnClickListeners();

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container,
            @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(getLayout(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupData();
        ButterKnife.bind(this, view);
        initOnClickListeners();
    }
}
