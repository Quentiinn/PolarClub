package com.example.quentincourvoisier.polarclub.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.provider.SyncStateContract;
import android.support.annotation.Nullable;

import com.example.common.Constants;
import com.example.quentincourvoisier.polarclub.utils.DailyHeartBeat;

/**
 * Created by quentincourvoisier on 31/01/2018.
 */

public class WearHeartbeatService extends Service implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor countSensor;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_HEART_RATE) {
            DailyHeartBeat.updateHearbeat();
            sendStepCountUpdate();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        sendStepCountUpdate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Log.d(TAG, "onStart");
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
        sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_NORMAL);
        return super.onStartCommand(intent, flags, startId);
    }

    private void sendStepCountUpdate() {
        Intent intent = new Intent();
        intent.setAction(Constants.HEART_COUNT_MESSAGE);
        intent.putExtra(Constants.HEART_COUNT_VALUE,DailyHeartBeat.getHearbeat());
        sendBroadcast(intent);
    }
}
