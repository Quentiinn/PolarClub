package com.example.quentincourvoisier.polarclub.utils;

import java.util.Random;

/**
 * Created by quentincourvoisier on 31/01/2018.
 */

public class DailyHeartBeat {
    private static int hearbeat;

    public static void updateHearbeat(){
        Random r = new Random();
        int i1 = r.nextInt(40) +80;
        hearbeat = i1;
    }

    public static int getHearbeat(){
        return hearbeat;
    }
}
