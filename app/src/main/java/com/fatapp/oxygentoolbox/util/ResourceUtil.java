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

import androidx.annotation.ArrayRes;
import androidx.annotation.AttrRes;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.core.os.ConfigurationCompat;
import androidx.core.os.LocaleListCompat;

import com.fatapp.oxygentoolbox.R;
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

    public static String getString(@StringRes int resId) {
        return sApp.getResources().getString(resId);
    }

    public static String[] getStringArray(@ArrayRes int redId) {
        return sApp.getResources().getStringArray(redId);
    }

    public static int getColor(@ColorRes int resId) {
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

    public static void loadAppTheme(Activity activity) {
        switch (SharedPreferencesUtils.getPreferenceTheme()) {
            case RED:
                activity.setTheme(R.style.Theme_OxygenToolbox_Red);
                activity.getWindow().setStatusBarColor(getColor(R.color.red_primary));
                break;
            case PINK:
                activity.setTheme(R.style.Theme_OxygenToolbox_Pink);
                activity.getWindow().setStatusBarColor(getColor(R.color.pink_primary));
                break;
            case PURPLE:
                activity.setTheme(R.style.Theme_OxygenToolbox_Purple);
                activity.getWindow().setStatusBarColor(getColor(R.color.purple_primary));
                break;
            case DEEP_PURPLE:
                activity.setTheme(R.style.Theme_OxygenToolbox_DeepPurple);
                activity.getWindow().setStatusBarColor(getColor(R.color.deep_purple_primary));
                break;
            case INDIGO:
                activity.setTheme(R.style.Theme_OxygenToolbox_Indigo);
                activity.getWindow().setStatusBarColor(getColor(R.color.indigo_primary));
                break;
            case BLUE:
                activity.setTheme(R.style.Theme_OxygenToolbox_Blue);
                activity.getWindow().setStatusBarColor(getColor(R.color.blue_primary));
                break;
            case LIGHT_BLUE:
                activity.setTheme(R.style.Theme_OxygenToolbox_LightBlue);
                activity.getWindow().setStatusBarColor(getColor(R.color.light_blue_primary));
                break;
            case CYAN:
                activity.setTheme(R.style.Theme_OxygenToolbox_Cyan);
                activity.getWindow().setStatusBarColor(getColor(R.color.cyan_primary));
                break;
            case TEAL:
                activity.setTheme(R.style.Theme_OxygenToolbox_Teal);
                activity.getWindow().setStatusBarColor(getColor(R.color.teal_primary));
                break;
            case GREEN:
                activity.setTheme(R.style.Theme_OxygenToolbox_Green);
                activity.getWindow().setStatusBarColor(getColor(R.color.green_primary));
                break;
            case LIGHT_GREEN:
                activity.setTheme(R.style.Theme_OxygenToolbox_LightGreen);
                activity.getWindow().setStatusBarColor(getColor(R.color.light_green_primary));
                break;
            case LIME:
                activity.setTheme(R.style.Theme_OxygenToolbox_Lime);
                activity.getWindow().setStatusBarColor(getColor(R.color.lime_primary));
                break;
            case YELLOW:
                activity.setTheme(R.style.Theme_OxygenToolbox_Yellow);
                activity.getWindow().setStatusBarColor(getColor(R.color.yellow_primary));
                break;
            case AMBER:
                activity.setTheme(R.style.Theme_OxygenToolbox_Amber);
                activity.getWindow().setStatusBarColor(getColor(R.color.amber_primary));
                break;
            case ORANGE:
                activity.setTheme(R.style.Theme_OxygenToolbox_Orange);
                activity.getWindow().setStatusBarColor(getColor(R.color.orange_primary));
                break;
            case DEEP_ORANGE:
                activity.setTheme(R.style.Theme_OxygenToolbox_DeepOrange);
                activity.getWindow().setStatusBarColor(getColor(R.color.deep_orange_primary));
                break;
            case BROWN:
                activity.setTheme(R.style.Theme_OxygenToolbox_Brown);
                activity.getWindow().setStatusBarColor(getColor(R.color.brown_primary));
                break;
            case GREY:
                activity.setTheme(R.style.Theme_OxygenToolbox_Grey);
                activity.getWindow().setStatusBarColor(getColor(R.color.grey_primary));
                break;
            case BLUE_GREY:
                activity.setTheme(R.style.Theme_OxygenToolbox_BlueGrey);
                activity.getWindow().setStatusBarColor(getColor(R.color.blue_grey_primary));
                break;
        }
    }

    @SuppressWarnings("unused")
    public enum LaunchPage {
        TOOLS, FAVOURITES
    }

    @SuppressWarnings("unused")
    public enum UiMode {
        SYSTEM, LIGHT, DARK
    }

    @SuppressWarnings("unused")
    public enum Theme {
        RED, PINK, PURPLE, DEEP_PURPLE, INDIGO, BLUE, LIGHT_BLUE, CYAN, TEAL, GREEN, LIGHT_GREEN, LIME, YELLOW, AMBER, ORANGE, DEEP_ORANGE, BROWN, GREY, BLUE_GREY
    }
}