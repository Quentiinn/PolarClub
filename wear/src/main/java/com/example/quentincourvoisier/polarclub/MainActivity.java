package com.example.quentincourvoisier.polarclub;

import android.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.quentincourvoisier.polarclub.fragments.HeartRateFragment;
import com.example.quentincourvoisier.polarclub.fragments.HomeFragment;

public class MainActivity extends WearableActivity implements  HomeFragment.OnFragmentInteractionListener , HeartRateFragment.OnFragmentInteractionListener {

    private TextView mTextView;

    private Button rejoindreButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.text);

//        HeartRateFragment scf = new HeartRateFragment();
  //      FragmentManager fragmentManager = getFragmentManager();
   //    fragmentManager.beginTransaction().replace(R.id.content_frame, scf).commit();

        HomeFragment hf = new HomeFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame , hf).commit();

        // Enables Always-on
        setAmbientEnabled();
    }



    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}
