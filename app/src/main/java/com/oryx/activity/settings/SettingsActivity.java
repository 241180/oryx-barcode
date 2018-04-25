package com.oryx.activity.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.oryx.R;
import com.oryx.activity.core.AbstractActivity;
import com.oryx.context.IServer;
import com.oryx.prefs.IUserPrefs;
import com.oryx.utils.PrefUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsActivity extends AbstractActivity {
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
        IServer.host = _hostField.getText().toString();
        SharedPreferences settings = PrefUtils.loadSettingsPreferences(this);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(IUserPrefs.PREF_HOST, IServer.host);
        editor.commit();

        finish();
    }

    @Override
    protected void loadPreferences() {
        SharedPreferences settings = PrefUtils.loadSettingsPreferences(this);
        _hostField.setText(settings.getString(IUserPrefs.PREF_HOST, "10.0.2.2"));
        IServer.host = _hostField.getText().toString();
    }
}