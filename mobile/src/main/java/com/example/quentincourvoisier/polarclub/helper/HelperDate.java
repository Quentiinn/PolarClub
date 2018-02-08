package com.example.quentincourvoisier.polarclub.helper;

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
}
