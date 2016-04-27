package com.example.android.cargotranport;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;

import android.content.Intent;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.cargotranport.DB.DAO.UserDAO;

import com.example.android.cargotranport.Services.GpsService;
import com.example.android.cargotranport.Entity.User;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.apache.commons.lang3.StringUtils;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    // private AuthenticationModel mAuthenticationModel;

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    //private UserLoginTask mAuthTask = null;

    // UI references.
    private EditText mUsernameView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private TextView mActionBarTitleTextView;
    private TextView mActionBarSubtitleTextView;
    private UserDAO userDAO;

    // private CustomSpinnerFragment mSpinnerFragment;
    private Button mOperationTypeButton;
    private Button mProjectButton;

    private boolean isWorking = false;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        userDAO = new UserDAO(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        createActionBar();

        mLoginFormView = findViewById(R.id.login_form); // ???
        mProgressView = findViewById(R.id.login_progressbar);
        mUsernameView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mProjectButton = (Button) findViewById(R.id.email_sign_in_button);
        startService(new Intent(getApplicationContext(),GpsService.class));
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();



    }

    private void createActionBar() {
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);

        View mCustomView = mInflater.inflate(R.layout.actionbar_main, null);
        mActionBarTitleTextView = (TextView) mCustomView.findViewById(R.id.txt_title);
        mActionBarTitleTextView.setText("CargoTransport - v1.0");

        mActionBarSubtitleTextView = (TextView) mCustomView.findViewById(R.id.txt_subtitle);
        mActionBarSubtitleTextView.setText("Ingresar al sistema");

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);

    }
    public void onLoginButtonClicked(View v) {
        attemptLogin();
    }
    @Override
    public void onBackPressed() {
        if (isWorking) return;
        super.onBackPressed();
    }

    private void attemptLogin() {
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        String username = mUsernameView.getText().toString().trim();
        if (StringUtils.isEmpty(username)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            mUsernameView.requestFocus();
            return;
        } else {
            User userlogin;
            userlogin = userDAO.obtuser(username);
            if (!userlogin.getLogin().toString().equals(username) || userlogin.getLogin() == null) {
                mUsernameView.setError("Usuario Incorrecto");
                mUsernameView.requestFocus();
                return;
            } else {
        String password = mPasswordView.getText().toString().trim();
        if (StringUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_empty_password));
            mPasswordView.requestFocus();
            return;
                } else {
                    if (!userlogin.getPassword().toString().equals(password) || userlogin.getPassword() == null) {
                        mPasswordView.setError("Password Incorrecto");
                        mPasswordView.requestFocus();
                        return;
                    } else {
                        showProgress(true);
                        Intent i = new Intent(LoginActivity.this, MainMenuActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        startActivity(i);

                        finish();

                    }
                }
            }
        }
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        isWorking = show;
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


}


