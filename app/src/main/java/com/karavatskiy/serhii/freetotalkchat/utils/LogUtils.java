package com.karavatskiy.serhii.freetotalkchat.utils;

import android.util.Log;
import com.karavatskiy.serhii.freetotalkchat.BuildConfig;

/**
 * Created by Serhii on 27.02.2019.
 */
public class LogUtils {

    public static void logDebug(String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, "logDebug:" + message);
        }
    }
}
