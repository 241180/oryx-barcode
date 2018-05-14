package com.oryx.barcode.activity.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.oryx.barcode.R;
import com.oryx.barcode.activity.core.ActionBarActivity;
import com.oryx.barcode.context.StaticServer;
import com.oryx.barcode.prefs.IUserPrefs;
import com.oryx.barcode.helper.PreferenceHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsActivity extends ActionBarActivity {
    private static final String TAG = "SettingsActivity";

    @BindView(R.id.hostField)
    EditText _hostField;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
    }

    public void save(View v) {
        StaticServer.host = _hostField.getText().toString();
        SharedPreferences settings = PreferenceHelper.loadSettingsPreferences(this);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(IUserPrefs.PREF_HOST, StaticServer.host);
        editor.commit();

        finish();
    }

    @Override
    public void loadPreferences() {
        SharedPreferences settings = PreferenceHelper.loadSettingsPreferences(this);
        _hostField.setText(settings.getString(IUserPrefs.PREF_HOST, "10.0.2.2"));
        StaticServer.host = _hostField.getText().toString();
    }
}