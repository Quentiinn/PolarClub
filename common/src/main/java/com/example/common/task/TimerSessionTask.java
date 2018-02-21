package com.example.common.task;

import android.content.Context;
import android.content.Intent;

import com.example.common.Constants;

import java.sql.Timestamp;
import java.util.TimerTask;

/**
 * Created by antho on 20/02/2018.
 */

public class TimerSessionTask extends TimerTask {

    public static final String TIME_SESSION_MESSAGE = "time_session_message";
    public static final String TIME_SESSION_VALUE = "time_session_value";

    private long timestamp;
    private Context context;

    public TimerSessionTask(Context context) {
        this.context = context;
    }

    @Override
    public void run() {
        long timestampActual = new Timestamp(System.currentTimeMillis()).getTime();

        Intent intent = new Intent();
        intent.setAction(Constants.TIME_SESSION_MESSAGE);
        intent.putExtra(Constants.TIME_SESSION_VALUE, isFinish(timestampActual));
        context.sendBroadcast(intent);
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    private boolean isFinish(long actual) {
        return actual > (timestamp * 1000);
    }
}
