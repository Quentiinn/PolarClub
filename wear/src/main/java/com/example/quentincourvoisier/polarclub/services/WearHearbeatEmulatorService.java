package com.example.quentincourvoisier.polarclub.services;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;

import java.util.Timer;



/**
 * Created by quentincourvoisier on 31/01/2018.
 */

public class WearHearbeatEmulatorService extends WearHeartbeatService {

    public static final String TAG = "WearHearbeatEmulatorService";
    private static Timer mTimer =null ;

    @SuppressLint("LongLogTag")
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mTimer ==null) {
            mTimer = new Timer();
            long delay = 1000;
            long period = 1000;
            mTimer.scheduleAtFixedRate(new RandomHearBeatTask(getApplicationContext()),delay,period);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        mTimer.cancel();
        mTimer = null;
        super.onDestroy();
    }
}
