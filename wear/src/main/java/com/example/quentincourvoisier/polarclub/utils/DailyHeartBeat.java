package com.example.quentincourvoisier.polarclub.utils;

import android.util.Log;

import java.util.Random;

/**
 * Created by quentincourvoisier on 31/01/2018.
 */

public class DailyHeartBeat {
    private static int hearbeat;

    private static int average = 80;

    public static void setMoreAverage(){
        average++;
        Log.i("++" , String.valueOf(average));
    }

    public static void setLessAverage(){
        average--;
        Log.i("--" , String.valueOf(average));
    }

    public static void updateHearbeat(){
        Random r = new Random();
        int i1 = r.nextInt(40) +80;
        //hearbeat = i1;
        hearbeat = (int) (r.nextGaussian() * 5 + average);
    }

    public static int getHearbeat(){
        return hearbeat;
    }


    // LOI UNIFORME
    public static double loi_uniforme() {
        Random r = new Random();
        double i = r.nextInt(100);
        return i;
    }
    // LOI NORMALE
    public static double _loi_normale(double m, double s){
        double x1,x2,y;
        x1 = loi_uniforme();
        x2 = loi_uniforme();

        // methode de Boc-Muller
        y = Math.pow(-2*Math.log(x1),0.5)*Math.cos(2.* 3.*x2);
        return m + s*y;
    }
}
