package com.fatapp.oxygentoolbox.util.tool;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.fatapp.oxygentoolbox.R;

public class ToolsLauncher {
    public static void launch(Activity activity, Context context, String launchActivity) {
        try {
            startActivity(context, new Intent(context, Class.forName(launchActivity))
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK),
                    ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
        } catch (ClassNotFoundException e) {
            Toast.makeText(context, R.string.activity_class_not_found, Toast.LENGTH_LONG).show();
        }
    }
}
