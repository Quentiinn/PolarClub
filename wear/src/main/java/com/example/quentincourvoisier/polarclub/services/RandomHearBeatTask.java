package com.example.quentincourvoisier.polarclub.services;

import android.content.Context;
import android.content.Intent;
import android.provider.SyncStateContract;


import com.example.common.Constants;
import com.example.quentincourvoisier.polarclub.utils.DailyHeartBeat;

import java.util.TimerTask;


/**
 * Created by quentincourvoisier on 31/01/2018.
 */

public class RandomHearBeatTask extends TimerTask {
    public static final String TAG="RandomStepCounterTimerTask";
    private Context ctxt =null;
    private static int HeartBeatCount;
    public RandomHearBeatTask(Context ctxt){
        this.ctxt = ctxt.getApplicationContext();
        HeartBeatCount = (int) (Math.random()*200);
        DailyHeartBeat.updateHearbeat();
    }
    @Override
    public void run() {
        HeartBeatCount += (int) (Math.random()*15);
        DailyHeartBeat.updateHearbeat();
        Intent intent = new Intent();
        intent.setAction(Constants.HEART_COUNT_MESSAGE);
        intent.putExtra(Constants.HEART_COUNT_VALUE,DailyHeartBeat.getHearbeat());
        ctxt.sendBroadcast(intent);
    }
}
