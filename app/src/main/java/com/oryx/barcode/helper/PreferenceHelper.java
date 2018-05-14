package com.oryx.barcode.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.oryx.barcode.activity.core.ActionBarActivity;
import com.oryx.barcode.prefs.IUserPrefs;

public class PreferenceHelper {

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
