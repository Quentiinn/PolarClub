package com.example.quentincourvoisier.polarclub.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.quentincourvoisier.polarclub.R;
import com.example.quentincourvoisier.polarclub.helper.HelperVerifForm;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.example.common.Constants.PREF_POLAR;
import static com.example.common.Constants.PREF_USER;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "TAG_REGISTER_ACTIVITY";

    private static final String BUTTON_SUBMIT = "button_submit";
    private static final String BUTTON_RETURN = "button_return";

    private EditText emailField;
    private EditText passwordField;
    private Button submitButton;
    private Button returnButton;
    private ProgressBar progressBar;

    private FirebaseAuth auth;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailField = findViewById(R.id.registerAct_email);
        passwordField = findViewById(R.id.registerAct_password);
        submitButton = findViewById(R.id.registerAct_submit);
        returnButton = findViewById(R.id.registerAct_retour);
        progressBar = findViewById(R.id.registerAct_loader);

        submitButton.setTag(BUTTON_SUBMIT);
        submitButton.setOnClickListener(this);
        returnButton.setTag(BUTTON_RETURN);
        returnButton.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();

        preferences = getSharedPreferences(PREF_POLAR, MODE_PRIVATE);
    }

    @Override
    public void onClick(View v) {
        switch ((String) v.getTag()) {
            case BUTTON_SUBMIT:
                registerUser();
                break;
            case BUTTON_RETURN:
                annuler();
                break;
        }
    }

    private void registerUser() {
        progressBar.setVisibility(VISIBLE);
        final String email = emailField.getText().toString();
        final String password = passwordField.getText().toString();

        if (HelperVerifForm.formLoginAndRegister(email, password, this)) {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
                if (!task.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Error register", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(GONE);
                } else {
                    onAuthSuccess(task.getResult().getUser(), email);
                }
            });
        }
    }

    private void onAuthSuccess(FirebaseUser user, String email) {
        preferences.edit().putString(PREF_USER, email).apply();
        progressBar.setVisibility(GONE);
        startActivity(new Intent(this, MainActivity.class));
    }

    private void annuler() {
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }
}
