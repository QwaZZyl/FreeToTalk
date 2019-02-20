package com.karavatskiy.serhii.babushkachat.base.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.karavatskiy.serhii.babushkachat.R;

/**
 * Created by Serhii on 21.01.2019.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.llMask)
    View llMask;

    protected abstract void setupUi();

    protected abstract void setupData();

    protected abstract int getLayoutId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutId());

        ButterKnife.bind(this);

        setupUi();
        setupData();
    }

    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        llMask.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
        llMask.setVisibility(View.GONE);
    }
}
