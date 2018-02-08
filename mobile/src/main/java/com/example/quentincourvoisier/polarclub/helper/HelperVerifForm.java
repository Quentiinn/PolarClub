package com.example.quentincourvoisier.polarclub.helper;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by antho on 08/02/2018.
 */

public class HelperVerifForm {
    
    public static boolean formLoginAndRegister(final String email, final String password, final Context context) {
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(context, "Email not empty", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!email.contains("@")) {
            Toast.makeText(context, "Email is not valid", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(context, "Password not empty", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
