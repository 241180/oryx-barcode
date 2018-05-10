package com.oryx.barcode.activity.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.oryx.barcode.R;
import com.oryx.barcode.activity.core.ActionBarActivity;
import com.oryx.barcode.context.IServer;
import com.oryx.barcode.context.IUser;
import com.oryx.barcode.prefs.IUserPrefs;
import com.oryx.barcode.service.AuthService;
import com.oryx.barcode.utils.GuiUtils;
import com.oryx.barcode.utils.PrefUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends ActionBarActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    @BindView(R.id.emailField)
    EditText _emailField;
    @BindView(R.id.passwordField)
    EditText _passwordField;
    @BindView(R.id.loginBtn)
    Button _loginButton;
    @BindView(R.id.rememberMeField)
    CheckBox _remember_me;
    @BindView(R.id.keepConnectedField)
    CheckBox _keep_connected;
    @BindView(R.id.signupLink)
    TextView _signupLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");
        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        // TODO: Implement your own authentication logic here.
        IUser user = new IUser();
        AuthService.connect(IServer.host, _emailField.getText().toString(), _passwordField.getText().toString());

        GuiUtils.showWorker(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        if(IServer.currentUser != null) {
                            onLoginSuccess();
                        } else {
                            onLoginFailed();
                        }
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically

                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        savePreferences();
        AuthService.sendRegistrationToServer(IServer.host, IServer.currentUser.getEmail(), IServer.token);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailField.getText().toString();
        String password = _passwordField.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailField.setError("enter a valid email address");
            valid = false;
        } else {
            _emailField.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordField.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordField.setError(null);
        }

        return valid;
    }

    @Override
    public void savePreferences() {
        SharedPreferences settings = PrefUtils.loadSettingsPreferences(this);
        SharedPreferences.Editor editor = settings.edit();
        if (_remember_me.isChecked() || _keep_connected.isChecked()) {
            _remember_me.setChecked(true);
            editor.putString(IUserPrefs.PREF_EMAIL, _emailField.getText().toString());
            editor.putString(IUserPrefs.PREF_PASSWORD, _passwordField.getText().toString());
            editor.putBoolean(IUserPrefs.PREF_REMEMBER, _remember_me.isChecked());
        }

        if (_keep_connected.isChecked()) {
            editor.putBoolean(IUserPrefs.PREF_KEEP_CONNECTED, _keep_connected.isChecked());
        }

        editor.putString(IUserPrefs.PREF_FIRE_BASE_TOKEN, IServer.token);
        editor.commit();
    }

    @Override
    public void loadPreferences() {
        SharedPreferences settings = PrefUtils.loadSettingsPreferences(this);
        _emailField.setText(settings.getString(IUserPrefs.PREF_EMAIL, ""));
        _passwordField.setText(settings.getString(IUserPrefs.PREF_PASSWORD, ""));
        _remember_me.setChecked(settings.getBoolean(IUserPrefs.PREF_REMEMBER, false));
        _keep_connected.setChecked(settings.getBoolean(IUserPrefs.PREF_KEEP_CONNECTED, false));
        IServer.host = settings.getString(IUserPrefs.PREF_HOST, "10.0.2.2");
        IServer.token = settings.getString(IUserPrefs.PREF_FIRE_BASE_TOKEN, null);

        if(_keep_connected.isChecked() && !IServer.logOut){
            login();
        }
    }
}