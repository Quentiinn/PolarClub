package com.example.quentincourvoisier.polarclub.helper;


import android.util.Log;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by sylvain on 08/02/18.
 */

public class HelperDate {

    public static String timestampToDateString(long longDate) {
        Timestamp timestamp = new Timestamp(longDate);
        Date date = new Date(timestamp.getTime() * 1000);

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);

        return String.valueOf(
                calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.YEAR) + " "
                        + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND)
        );
    }

    public static String datetimeFrToUs(String dateFr) {
        String[] parts = dateFr.split("/");
        return parts[2] + '-' + parts[1] + '-' + parts[0];
    }

    public static long dateToTimestamp(String stringDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        long date = 0;

        try {
            Date parsedDate = dateFormat.parse(stringDate);
            Timestamp timestamp = new Timestamp(parsedDate.getTime());
            date = timestamp.getTime() / 1000;
        } catch (ParseException e) {
            Log.d("HelperDate", e.getMessage());
            e.printStackTrace();
        }

        return date;
    }
}
