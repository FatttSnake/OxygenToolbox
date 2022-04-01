package com.fatapp.oxygentoolbox.util;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.fatapp.oxygentoolbox.R;
import com.fatapp.oxygentoolbox.tools.TimeScreenActivity;

public class ToolsLauncher {
    public static void launch(Context context, String activity) {
        try {
            startActivity(context, new Intent(context, Class.forName(activity)), null);
        } catch (ClassNotFoundException e) {
            Toast.makeText(context, R.string.activity_class_not_found, Toast.LENGTH_LONG).show();
        }
    }
}
