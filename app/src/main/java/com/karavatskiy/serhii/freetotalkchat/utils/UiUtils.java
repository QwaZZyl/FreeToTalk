package com.karavatskiy.serhii.freetotalkchat.utils;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

/**
 * Created by Serhii on 12.01.2019.
 */
public class UiUtils {

    public static void toast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void replaceFragment(FragmentManager fragmentManager, int containerId, Fragment fragment,
            String tag) {
        fragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(containerId, fragment, tag)
                .addToBackStack(tag)
                .commit();
    }
}
