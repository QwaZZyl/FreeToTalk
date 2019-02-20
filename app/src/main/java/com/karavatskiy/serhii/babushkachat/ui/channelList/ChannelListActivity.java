package com.karavatskiy.serhii.babushkachat.ui.channelList;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Serhii on 20.02.2019.
 */
public class ChannelListActivity extends AppCompatActivity {

    public static void start(AppCompatActivity activity) {
        activity.startActivity(new Intent(activity, ChannelListActivity.class));
        activity.finish();
    }

}
