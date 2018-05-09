package com.oryx.barcode.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.oryx.barcode.activity.core.ActionBarActivity;
import com.oryx.barcode.prefs.IUserPrefs;

public class PrefUtils {


    public static SharedPreferences settings = null;

    public static SharedPreferences loadSettingsPreferences(ActionBarActivity activity) {
        if (settings == null) {
            settings = activity.getSharedPreferences(IUserPrefs.PREF_SETTINGS,
                    Context.MODE_PRIVATE);
        }

        return settings;
    }

    public static void saveSettingsPreferences() {
        SharedPreferences.Editor editor = settings.edit();
        editor.commit();
    }


}
