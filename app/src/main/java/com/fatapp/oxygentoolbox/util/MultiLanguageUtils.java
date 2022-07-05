package com.fatapp.oxygentoolbox.util;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.LocaleList;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import androidx.annotation.RequiresApi;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.core.os.ConfigurationCompat;
import androidx.core.os.LocaleListCompat;

import com.fatapp.oxygentoolbox.R;

import java.util.Locale;

public class MultiLanguageUtils {

    public static Context attachBaseContext(Context context) {
        String locale;
        if (SharedPreferencesUtils.isNull()) {
            locale = "default";
        } else {
            locale = SharedPreferencesUtils.getLocale();
        }
        String language;
        String country;
        if (!locale.equals("default")) {
            language = locale.substring(0, locale.indexOf("_"));
            country = locale.substring(locale.indexOf("_") + 1);
        } else {
            language = ResourceUtil.getSystemLocale().get(0).getLanguage();
            country = ResourceUtil.getSystemLocale().get(0).getCountry();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            return createConfigurationContext(context, language, country);
        } else {
            return updateConfiguration(context, language, country);
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