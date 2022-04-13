package com.fatapp.oxygentoolbox.util;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.Window;

import androidx.annotation.AttrRes;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;
import androidx.appcompat.view.ContextThemeWrapper;

import com.google.android.material.color.MaterialColors;

import java.util.Objects;

public final class ResourceUtil {

    private static Application sApp;
    private static Resources sRes;

    public static void init(Application app) {
        sApp = app;
        sRes = app.getResources();
    }

    public static Application getApplication() {
        return sApp;
    }

    public static Resources getResources() {
        return sRes;
    }

    public static String getString(int resId) {
        return sRes.getString(resId);
    }

    public static int getColor(int resId) {
        return sRes.getColor(resId);
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
}