package com.fatapp.oxygentoolbox.util;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;

import com.fatapp.oxygentoolbox.tools.TimeScreenActivity;

public class BasicToolsLauncher {
    public static void launch(int activity, Context context) {
        switch (activity) {
            case 0:
                startActivity(context, new Intent(context, TimeScreenActivity.class), null);
                break;
            case 1:
                break;
        }
    }
}
