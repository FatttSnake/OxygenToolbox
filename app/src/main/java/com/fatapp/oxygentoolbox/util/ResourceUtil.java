package com.fatapp.oxygentoolbox.util;

import android.app.Application;
import android.content.res.Resources;

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
}