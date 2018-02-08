package com.example.quentincourvoisier.polarclub.activities;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.common.Constants;
import com.example.quentincourvoisier.polarclub.R;
import com.example.quentincourvoisier.polarclub.fragments.AddSessionFragment;
import com.example.quentincourvoisier.polarclub.fragments.HomeFragment;
import com.example.quentincourvoisier.polarclub.fragments.ListSessionFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.common.Constants.PREF_POLAR;
import static com.example.common.Constants.PREF_USER;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseAuth.AuthStateListener authStateListener;
    private ChildEventListener childEventListener;

    private SharedPreferences preferences;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setTitle("Home");
                    HomeFragment homeFragment = new HomeFragment();
                    FragmentTransaction homeFragmentTransaction = getFragmentManager().beginTransaction();
                    homeFragmentTransaction.replace(R.id.content, homeFragment, "FragmentName").commit();
                    return true;
                case R.id.navigation_new_session:
                    setTitle("New session");
                    AddSessionFragment sessionFragment = new AddSessionFragment();
                    FragmentTransaction addSessionFragmentTransaction = getFragmentManager().beginTransaction();
                    addSessionFragmentTransaction.replace(R.id.content, sessionFragment, "FragmentName").commit();
                    return true;
                case R.id.navigation_list_session:
                    setTitle("List Sessions");
                    ListSessionFragment listSessionFragment = new ListSessionFragment();
                    FragmentTransaction listSessionFragmentTransaction = getFragmentManager().beginTransaction();
                    listSessionFragmentTransaction.replace(R.id.content, listSessionFragment, "FragmentName").commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();

        preferences = getSharedPreferences(PREF_POLAR, MODE_PRIVATE);

        setTitle("Home");
        HomeFragment homeFragment = new HomeFragment();
        FragmentTransaction homeFragmentTransaction = getFragmentManager().beginTransaction();
        homeFragmentTransaction.replace(R.id.content, homeFragment, "FragmentName").commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            clearOnLogout();
        }
        return super.onOptionsItemSelected(item);
    }

    private void clearOnLogout() {
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            preferences.edit().remove(PREF_USER).apply();
            auth.signOut();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }
    }

    /**
     * session
     *      uid: 1
     *      debut: 1212121212
     *      frequence
     *          jeanne: 121
     *          paul: 123
     */
}
