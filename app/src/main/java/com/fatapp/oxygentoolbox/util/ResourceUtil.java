package com.fatapp.oxygentoolbox.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.LocaleList;
import android.util.DisplayMetrics;
import android.view.Window;

import androidx.annotation.AttrRes;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.core.os.ConfigurationCompat;
import androidx.core.os.LocaleListCompat;

import com.google.android.material.color.MaterialColors;

import java.util.Locale;
import java.util.Objects;

public final class ResourceUtil {
    private static int UI_MODE_SYSTEM;
    public static final int UI_MODE_LIGHT = Configuration.UI_MODE_NIGHT_NO + Configuration.UI_MODE_TYPE_NORMAL;
    public static final int UI_MODE_DARK = Configuration.UI_MODE_NIGHT_YES + Configuration.UI_MODE_TYPE_NORMAL;

    private static Application sApp;

    public static void init(Application app) {
        sApp = app;
        if (UI_MODE_SYSTEM == 0) {
            UI_MODE_SYSTEM = getAppUiMode();
        }
    }

    public static Application getApplication() {
        return sApp;
    }

    public static Resources getResources() {
        return sApp.getResources();
    }

    public static Configuration getConfiguration() {
        return sApp.getResources().getConfiguration();
    }

    public static DisplayMetrics getDisplayMetrics() {
        return sApp.getResources().getDisplayMetrics();
    }

    public static String getString(int resId) {
        return sApp.getResources().getString(resId);
    }

    public static int getColor(int resId) {
        return sApp.getResources().getColor(resId);
    }

    @ColorInt
    public static int getThemeAttrColor(@NonNull Context context, @StyleRes int themeResId, @AttrRes int attrResId) {
        return MaterialColors.getColor(new ContextThemeWrapper(context, themeResId), attrResId, Color.WHITE);
    }

    public static int getStatusBarHeight(Window window, Context context) {
        Rect localRect = new Rect();
        window.getDecorView().getWindowVisibleDisplayFrame(localRect);
        int mStatusBarHeight = localRect.top;
        if (mStatusBarHeight == 0) {
            try {
                @SuppressLint("PrivateApi")
                Class<?> localClass = Class.forName("com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int status_bar_height = Integer.parseInt(Objects.requireNonNull(localClass.getField("status_bar_height").get(localObject)).toString());
                mStatusBarHeight = context.getResources().getDimensionPixelSize(status_bar_height);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (mStatusBarHeight == 0) {
            int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                mStatusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
            }
        }
        return mStatusBarHeight;
    }

    public static int getNavigationBarHeight(Context context) {
        int rid = context.getResources().getIdentifier("config_showNavigationBar", "bool", "android");
        if (rid != 0) {
            int resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
            return context.getResources().getDimensionPixelSize(resourceId);
        } else {
            return 0;
        }
    }

    public static Locale getAppLocale() {
        Locale local;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            local = getConfiguration().getLocales().get(0);
        } else {
            local = getConfiguration().locale;
        }
        return local;
    }

    public static LocaleListCompat getSystemLocale() {
        Configuration configuration = Resources.getSystem().getConfiguration();
        return ConfigurationCompat.getLocales(configuration);
    }

    public static void setAppLocale(Locale locale) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            getConfiguration().setLocale(locale);
            getConfiguration().setLocales(new LocaleList(locale));
            getApplication().createConfigurationContext(getConfiguration());
        } else {
            getConfiguration().setLocale(locale);
        }
        updateConfiguration();
    }

    public static int getSystemUiMode() {
        return UI_MODE_SYSTEM;
    }

    public static int getAppUiMode() {
        return getConfiguration().uiMode;
    }

    public static void setAppUiMode(int uiMode) {
        getConfiguration().uiMode = uiMode;
        updateConfiguration();
    }

    private static void updateConfiguration() {
        getResources().updateConfiguration(getConfiguration(), getDisplayMetrics());
    }

    public static void restartActivity(Activity activity, Class<?> cls) {
        Intent intent = new Intent(activity, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(intent);
        activity.finish();
    }

    public static int dpToPx(float dp) {
        return (int) (dp * getDisplayMetrics().density + 0.5f);
    }

    public static float pxToDp(int px) {
        return px / getDisplayMetrics().density + 0.5f;
    }

    public static String getAppVersionName() {
        try {
            PackageManager packageManager = sApp.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(sApp.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "Null";
        }
    }

    public static int getAppVersionCode() {
        try {
            PackageManager packageManager = sApp.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(sApp.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            return -1;
        }
    }
}