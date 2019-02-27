package com.karavatskiy.serhii.freetotalkchat.ui.channelsList;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Serhii on 20.02.2019.
 */
public class ChannelsListActivity extends AppCompatActivity {

    public static void start(AppCompatActivity activity) {
        activity.startActivity(new Intent(activity, ChannelsListActivity.class));
        activity.finish();
    }

}
