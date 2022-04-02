package com.fatapp.oxygentoolbox.util;

import android.app.Service;
import android.os.Vibrator;

public class VibratorController {
    private static boolean hasVib;
    private static Vibrator sVib;

    public static void init() {
        sVib = (Vibrator) ResourceUtil.getApplication().getSystemService(Service.VIBRATOR_SERVICE);
        hasVib = sVib.hasVibrator();
    }

    public static void vibrate(long milliseconds) {
        if (hasVib) {
            sVib.vibrate(milliseconds);
        }
    }

    public static void vibrate(long[] pattern,int repeat){
        if (hasVib) {
            sVib.vibrate(pattern,repeat);
        }
    }

    public static void cancel(){
        if (hasVib) {
            sVib.cancel();
        }
    }
}
