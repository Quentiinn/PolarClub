package com.example.quentincourvoisier.polarclub.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quentincourvoisier.polarclub.R;
import com.example.quentincourvoisier.polarclub.helper.HelperVerifForm;
import com.google.firebase.auth.FirebaseAuth;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.example.common.Constants.PREF_POLAR;
import static com.example.common.Constants.PREF_USER;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "TAG_LOGIN_ACTIVITY";

    private static final String BUTTON_SUBMIT = "button_submit";
    private static final String LINK_REGISTER = "link_register";

    private EditText emailField;
    private EditText passwordField;
    private Button buttonSubmit;
    private TextView linkRegister;
    private ProgressBar progressBar;

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailField = findViewById(R.id.loginAct_email);
        passwordField = findViewById(R.id.loginAct_password);
        linkRegister = findViewById(R.id.loginAct_link);
        buttonSubmit = findViewById(R.id.loginAct_submit);
        progressBar = findViewById(R.id.loginAct_loader);

        buttonSubmit.setTag(BUTTON_SUBMIT);
        buttonSubmit.setOnClickListener(this);
        linkRegister.setTag(LINK_REGISTER);
        linkRegister.setOnClickListener(this);

        preferences = getSharedPreferences(PREF_POLAR, MODE_PRIVATE);

        auth = FirebaseAuth.getInstance();
        authStateListener = firebaseAuth -> {
            if (firebaseAuth.getCurrentUser() != null && preferences.getString(PREF_USER, null) != null) {
                Log.i(TAG, "CONNECTION - authStateListener");
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onClick(View v) {
        switch ((String) v.getTag()) {
            case BUTTON_SUBMIT:
                startSignIn();
                break;
            case LINK_REGISTER:
                startActivity(new Intent(this, RegisterActivity.class));
                finish();
        }
    }

    private void startSignIn() {
        progressBar.setVisibility(VISIBLE);
        final String email = emailField.getText().toString();
        final String password = passwordField.getText().toString();

        if (HelperVerifForm.formLoginAndRegister(email, password, this)) {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (!task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Sign in problem", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(GONE);
                } else {
                    preferences.edit().putString(PREF_USER, email).apply();
                    progressBar.setVisibility(GONE);
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }
            });
        }
    }
}
