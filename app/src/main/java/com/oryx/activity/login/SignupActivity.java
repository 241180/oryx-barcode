package com.oryx.activity.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.oryx.R;
import com.oryx.activity.core.AbstractActivity;
import com.oryx.context.IServer;
import com.oryx.service.AuthService;
import com.oryx.utils.GuiUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignupActivity extends AbstractActivity {
    private static final String TAG = "SignupActivity";

    @BindView(R.id.firstNameField)
    EditText _firstNameField;
    @BindView(R.id.lastNameField)
    EditText _lastNameField;
    @BindView(R.id.emailField)
    EditText _emailField;
    @BindView(R.id.phoneField)
    EditText _phoneField;
    @BindView(R.id.addressField)
    EditText _addressField;
    @BindView(R.id.passwordField)
    EditText _passwordField;
    @BindView(R.id.reEnterPasswordField)
    EditText _reEnterPasswordField;

    @BindView(R.id.signupBtn)
    Button _signupButton;
    @BindView(R.id.loginLink)
    TextView _loginLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String firstName = _firstNameField.getText().toString();
        String lastName = _lastNameField.getText().toString();
        String email = _emailField.getText().toString();
        String phone = _phoneField.getText().toString();
        String address = _addressField.getText().toString();
        String password = _passwordField.getText().toString();
        String reEnterPassword = _reEnterPasswordField.getText().toString();

        // TODO: Implement your own signup logic here.
        //IUser user = new IUser();
        AuthService.signup(IServer.host, firstName.toString(),
                lastName.toString(),
                email.toString(),
                phone.toString(),
                address.toString(),
                password.toString());

        GuiUtils.showWorker(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        //if(IServer.currentUser != null) {
                        onSignupSuccess();
                        //} else {
                        //    onSignupFailed();
                        //}
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String firstName = _firstNameField.getText().toString();
        String lastName = _lastNameField.getText().toString();
        String email = _emailField.getText().toString();
        String phone = _phoneField.getText().toString();
        String address = _addressField.getText().toString();
        String password = _passwordField.getText().toString();
        String reEnterPassword = _reEnterPasswordField.getText().toString();

        if (firstName.isEmpty() || firstName.length() < 3) {
            _firstNameField.setError("at least 3 characters");
            valid = false;
        } else {
            _firstNameField.setError(null);
        }

        if (lastName.isEmpty() || lastName.length() < 3) {
            _lastNameField.setError("at least 3 characters");
            valid = false;
        } else {
            _lastNameField.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailField.setError("enter a valid email address");
            valid = false;
        } else {
            _emailField.setError(null);
        }

        if (phone.isEmpty() || phone.length() != 10) {
            _phoneField.setError("Enter Valid Mobile Number");
            valid = false;
        } else {
            _phoneField.setError(null);
        }

        if (address.isEmpty()) {
            _addressField.setError("Enter Valid Address");
            valid = false;
        } else {
            _addressField.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordField.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordField.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            _reEnterPasswordField.setError("Password Do not match");
            valid = false;
        } else {
            _reEnterPasswordField.setError(null);
        }

        return valid;
    }
}