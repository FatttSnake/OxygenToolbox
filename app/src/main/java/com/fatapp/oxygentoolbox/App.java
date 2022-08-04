package com.fatapp.oxygentoolbox;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fatapp.oxygentoolbox.util.MultiLanguageUtils;
import com.fatapp.oxygentoolbox.util.ResourceUtil;
import com.fatapp.oxygentoolbox.util.SharedPreferencesUtils;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {
                SharedPreferencesUtils.init(App.this);
                ResourceUtil.init(App.this);
                ResourceUtil.setAppLocale(SharedPreferencesUtils.getPreferenceLocale());
                loadAppUiMode();
            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {

            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {

            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {

            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {

            }
        });
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(MultiLanguageUtils.attachBaseContext(base));
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        MultiLanguageUtils.attachBaseContext(this);
    }

    public static void loadAppUiMode() {
        switch (SharedPreferencesUtils.getPreferenceUiMode()) {
            case light:
                ResourceUtil.setAppUiMode(ResourceUtil.UI_MODE_LIGHT);
                break;
            case dark:
                ResourceUtil.setAppUiMode(ResourceUtil.UI_MODE_DARK);
                break;
            default:
                ResourceUtil.setAppUiMode(ResourceUtil.getSystemUiMode());
        }
    }
}
