package com.fatapp.oxygentoolbox;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.splashscreen.SplashScreen;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.fatapp.oxygentoolbox.util.MultiLanguageUtils;
import com.fatapp.oxygentoolbox.util.ResourceUtil;
import com.fatapp.oxygentoolbox.util.VibratorController;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    private DrawerLayout drawerLayout;
    private NavigationView navView;
    private CoordinatorLayout mainPage;
    private Toolbar toolbar;

    private void initView() {
        drawerLayout = findViewById(R.id.drawer_layout);
        navView = findViewById(R.id.nav_view);
        mainPage = findViewById(R.id.main_page);
        toolbar = findViewById(R.id.toolbar);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initLayout();
        VibratorController.init();

//        shortCutCreateTest();
    }

    private void initLayout() {
        mainPage.setPadding(0, ResourceUtil.getStatusBarHeight(getWindow(), getApplicationContext()), 0, 0);

        setSupportActionBar(toolbar);

        navView.inflateHeaderView(R.layout.app_nav_header_main);
        navView.inflateMenu(R.menu.activity_main_drawer);
        navView.addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> drawerLayout.setDrawerLockMode(navView.getMenu().getItem(0).isChecked()
                ? DrawerLayout.LOCK_MODE_UNLOCKED
                : DrawerLayout.LOCK_MODE_LOCKED_CLOSED));
        navView.getMenu().getItem(4).setOnMenuItemClickListener(item -> {
            finish();
            return false;
        });

        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home)
                .setOpenableLayout(drawerLayout)
                .build();
        NavController navController = ((NavHostFragment) Objects.requireNonNull(getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment))).getNavController();
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

/*
    private void shortCutCreateTest() {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.N_MR1) {
            return;
        }
        Intent intent = new Intent();
        intent.setAction("android.intent.action.MAIN");
        intent.setClassName("com.fatapp.oxygentoolbox",
                "com.fatapp.oxygentoolbox.MainActivity");
        ShortcutInfo.Builder builder;
        builder = new ShortcutInfo.Builder(this, "Time Screen")
                .setIntent(new Intent()
                        .setAction("activity.timescreen")
                        .setClassName("com.fatapp.oxygentoolbox", "com.fatapp.oxygentoolbox.tools.timescreen.TimeScreenActivity"))
                .setShortLabel("Time Screen")
                .setLongLabel("Time Screen")
                .setIcon(Icon.createWithResource(this, R.drawable.ic_menu_home));
        ShortcutManager shortcutManager = getSystemService(ShortcutManager.class);
        shortcutManager.addDynamicShortcuts(Collections.singletonList(builder.build()));
    }
*/

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(MultiLanguageUtils.attachBaseContext(newBase));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MultiLanguageUtils.attachBaseContext(getApplicationContext());
    }
}