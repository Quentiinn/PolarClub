package com.example.quentincourvoisier.polarclub.helper;

import java.security.Timestamp;
import java.text.DateFormat;
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
        Date date = new Date(longDate);
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return String.valueOf(
                calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.YEAR) + " "
                + calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND)
        );
    }

    public static String datetimeFrToUs(String dateFr) {
        String[] parts = dateFr.split("/");
        return parts[2] + '-' + parts[1] + '-' + parts[0];
    }

    public static long dateToTimestamp(String stringDate)  {
        DateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        Date date = null;
        try {
            date = format.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime() / 1000L;
    }
}
