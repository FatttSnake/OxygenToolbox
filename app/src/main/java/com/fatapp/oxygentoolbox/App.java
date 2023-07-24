package com.fatapp.oxygentoolbox;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fatapp.oxygentoolbox.util.MultiLanguageUtils;
import com.fatapp.oxygentoolbox.util.ResourceUtil;
import com.fatapp.oxygentoolbox.util.SharedPreferencesUtils;
import com.fatapp.oxygentoolbox.util.tool.ToolsList;

import java.io.IOException;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        init();

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {
                init();
                ResourceUtil.loadAppTheme(activity);
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

    private void init() {
        ResourceUtil.init(App.this);
        SharedPreferencesUtils.init(App.this);
        ResourceUtil.setAppLocale(SharedPreferencesUtils.getPreferenceLocale());
        loadTools();
    }

    private void loadAppUiMode() {
        switch (SharedPreferencesUtils.getPreferenceUiMode()) {
            case LIGHT -> ResourceUtil.setAppUiMode(ResourceUtil.UI_MODE_LIGHT);
            case DARK -> ResourceUtil.setAppUiMode(ResourceUtil.UI_MODE_DARK);
            default -> ResourceUtil.setAppUiMode(ResourceUtil.getSystemUiMode());
        }
    }

    private void loadTools() {
        try {
            ToolsList.init(getResources().getAssets().open("json/BasicTools.json"));
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), R.string.init_tools_failed, Toast.LENGTH_LONG).show();
        }
    }
}
