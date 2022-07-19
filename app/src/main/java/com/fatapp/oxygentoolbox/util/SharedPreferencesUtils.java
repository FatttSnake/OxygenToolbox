package com.fatapp.oxygentoolbox.util;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.fatapp.oxygentoolbox.R;

import java.util.Locale;
import java.util.Objects;

public class SharedPreferencesUtils {
    private static SharedPreferences preferences;

    public static void init(Application app) {
        preferences = PreferenceManager.getDefaultSharedPreferences(app);
    }

    public static Locale getLanguage() {
        String languagePreference;
        String language;
        String country;
        if (SharedPreferencesUtils.isNull() ||
                (languagePreference = preferences.getString(ResourceUtil.getString(R.string.setting_language_key), ResourceUtil.getString(R.string.setting_language_default_value))).equals(ResourceUtil.getString(R.string.setting_language_default_value))) {
            language = Objects.requireNonNull(ResourceUtil.getSystemLocale().get(0)).getLanguage();
            country = Objects.requireNonNull(ResourceUtil.getSystemLocale().get(0)).getCountry();
        } else {
            language = languagePreference.substring(0, languagePreference.indexOf("_"));
            country = languagePreference.substring(languagePreference.indexOf("_") + 1);
        }

        return new Locale(language, country);
    }

    public static boolean isNull() {
        return preferences == null;
    }

    public static LaunchPage getLaunchPage() {
        return LaunchPage.valueOf(preferences.getString(ResourceUtil.getString(R.string.setting_launch_page_key), ResourceUtil.getString(R.string.setting_launch_page_default_value)));
    }

    public enum LaunchPage {
        tools,favourites
    }
}
