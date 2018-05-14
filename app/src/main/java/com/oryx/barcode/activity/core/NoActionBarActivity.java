package com.oryx.barcode.activity.core;

public abstract class NoActionBarActivity extends AbstractActivity{


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
}
