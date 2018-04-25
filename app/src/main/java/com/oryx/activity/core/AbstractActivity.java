package com.oryx.activity.core;

import android.support.v7.app.AppCompatActivity;

public abstract class AbstractActivity extends AppCompatActivity {


    @Override
    protected void onPause() {
        super.onPause();
        savePreferences();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPreferences();
    }

    protected void savePreferences() {

    }

    protected void loadPreferences() {

    }
}
