package com.example.quentincourvoisier.polarclub.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quentincourvoisier.polarclub.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.common.Constants.PREF_POLAR;
import static com.example.common.Constants.PREF_USER;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String BUTTON_SUBMIT = "button_submit";
    private static final String LINK_REGISTER = "link_register";

    private EditText emailField;
    private EditText passwordField;
    private Button buttonSubmit;
    private TextView linkRegister;

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

        buttonSubmit.setTag(BUTTON_SUBMIT);
        buttonSubmit.setOnClickListener(this);
        linkRegister.setTag(LINK_REGISTER);
        linkRegister.setOnClickListener(this);

        preferences = getSharedPreferences(PREF_POLAR, MODE_PRIVATE);

        auth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null && preferences.getString(PREF_USER, null) != null) {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
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
        switch ((String)v.getTag()) {
            case BUTTON_SUBMIT:
                startSignIn();
                break;
            case LINK_REGISTER:
                startActivity(new Intent(this, RegisterActivity.class));
                finish();
        }
    }

    private void startSignIn() {
        final String email = emailField.getText().toString();
        String password = passwordField.getText().toString();

        if (verif(email, password)) {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Sign in problem", Toast.LENGTH_SHORT).show();
                    } else {
                        preferences.edit().putString(PREF_USER, email).apply();
                    }
                }
            });
        }
    }

    private boolean verif(String email, String password) {
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Email not empty", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!email.contains("@")) {
            Toast.makeText(this, "Email is not valid", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Password not empty", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
