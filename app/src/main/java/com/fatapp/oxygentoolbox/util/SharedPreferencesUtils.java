package com.fatapp.oxygentoolbox.util;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

public class SharedPreferencesUtils {
    private static SharedPreferences preferences;

    public static void init(Application app) {
        preferences = PreferenceManager.getDefaultSharedPreferences(app);
    }

    public static String getLocale() {
        return preferences.getString("app_language", "default");
    }

    public static boolean isNull() {
        return preferences == null;
    }
}
