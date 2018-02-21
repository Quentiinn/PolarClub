package com.example.common.task;

import android.content.Context;
import android.content.Intent;

import com.example.common.Constants;

import java.sql.Timestamp;
import java.util.TimerTask;

/**
 * Created by antho on 21/02/2018.
 */

public class DebutSessionTask extends TimerTask {

    private long timestamp;
    private Context context;

    public DebutSessionTask(Context context) {
        this.context = context;
    }

    public static final String DEBUT_SESSION_MESSAGE = "debut_session_message";
    public static final String DEBUT_SESSION_VALUE = "debut_session_value";

    @Override
    public void run() {
        long timestampActual = new Timestamp(System.currentTimeMillis()).getTime();

        Intent intent = new Intent();
        intent.setAction(Constants.DEBUT_SESSION_MESSAGE);
        intent.putExtra(Constants.DEBUT_SESSION_VALUE, isStart(timestampActual));
        context.sendBroadcast(intent);
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    private boolean isStart(long actual) {
        return actual > (timestamp * 1000);
    }
}
