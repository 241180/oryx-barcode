package com.oryx.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.oryx.activity.core.AbstractActivity;
import com.oryx.prefs.IUserSettings;

public class PrefUtils {


    public static SharedPreferences settings = null;

    public static SharedPreferences loadSettingsPreferences(AbstractActivity activity){
        if(settings == null){
            settings = activity.getSharedPreferences(IUserSettings.PREF_SETTINGS,
                    Context.MODE_PRIVATE);
        }

        return settings;
    }

    public static void saveSettingsPreferences(){
        SharedPreferences.Editor editor = settings.edit();
        editor.commit();
    }


}
