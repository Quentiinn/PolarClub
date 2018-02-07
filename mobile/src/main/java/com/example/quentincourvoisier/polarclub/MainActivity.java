package com.example.quentincourvoisier.polarclub;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.quentincourvoisier.polarclub.fragments.AddSessionFragment;
import com.example.quentincourvoisier.polarclub.fragments.HomeFragment;
import com.example.quentincourvoisier.polarclub.fragments.ListSessionFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

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

        setTitle("Home");
        HomeFragment homeFragment = new HomeFragment();
        FragmentTransaction homeFragmentTransaction = getFragmentManager().beginTransaction();
        homeFragmentTransaction.replace(R.id.content, homeFragment, "FragmentName").commit();
    }

}
