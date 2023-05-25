package com.fatapp.oxygentoolbox.util;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.util.DisplayMetrics;

import androidx.annotation.RequiresApi;

import java.util.Locale;

public class MultiLanguageUtils {

    public static Context attachBaseContext(Context context) {
        Locale locale = SharedPreferencesUtils.getPreferenceLocale();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            return createConfigurationContext(context, locale.getLanguage(), locale.getCountry());
        } else {
            return updateConfiguration(context, locale.getLanguage(), locale.getCountry());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N_MR1)
    private static Context createConfigurationContext(Context context, String language, String country) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        Locale locale = new Locale(language, country);
        LocaleList localeList = new LocaleList(locale);
        configuration.setLocales(localeList);
        return context.createConfigurationContext(configuration);
    }

    private static Context updateConfiguration(Context context, String language, String country) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        Locale locale = new Locale(language, country);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            configuration.setLocales(new LocaleList(locale));
        } else {
            configuration.locale = locale;
            DisplayMetrics displayMetrics = resources.getDisplayMetrics();
            resources.updateConfiguration(configuration, displayMetrics);
        }
        return context;
    }
}