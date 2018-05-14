package com.oryx.barcode.helper;

import android.Manifest;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;

public class GuiHelper {
    public static void showWorker(Runnable r, long delayMillis) {
        new android.os.Handler().postDelayed(r, delayMillis);
    }

    public static void configure_gps(AppCompatActivity activity) {
        // first check for permissions
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}
                    , 10);
        }
        return;
    }
}
