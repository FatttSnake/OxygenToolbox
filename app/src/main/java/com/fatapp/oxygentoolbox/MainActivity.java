package com.fatapp.oxygentoolbox;

import android.os.Bundle;
import android.view.View;
import android.view.Menu;

import com.fatapp.oxygentoolbox.util.ResourceUtil;
import com.fatapp.oxygentoolbox.util.VibratorController;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    public static AppCompatActivity mainActivity;

    private AppBarConfiguration mAppBarConfiguration;

    private Toolbar toolbar;
    private FloatingActionButton fab;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        fab = findViewById(R.id.fab);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        mainActivity = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init
        initView();
        initLayout();
        ResourceUtil.init(getApplication());
        VibratorController.init();

//        shortCutCreateTest();
    }

    private void initLayout() {
        setSupportActionBar(toolbar);
        fab.setOnClickListener(view -> {
            /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();*/
        });
        navigationView.inflateHeaderView(R.layout.nav_header_main);
        navigationView.inflateMenu(R.menu.activity_main_drawer);
        navigationView.addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
            if (navigationView.getMenu().getItem(0).isChecked()) {
                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                fab.setVisibility(View.VISIBLE);
            } else {
                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                fab.setVisibility(View.GONE);
            }
        });
        navigationView.getMenu().getItem(4).setOnMenuItemClickListener(item -> {
            finish();
            return false;
        });
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = ((NavHostFragment) Objects.requireNonNull(getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment))).getNavController();
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
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
                        .setClassName("com.fatapp.oxygentoolbox", "com.fatapp.oxygentoolbox.tools.TimeScreenActivity"))
                .setShortLabel("Time Screen")
                .setLongLabel("Time Screen")
                .setIcon(Icon.createWithResource(this, R.drawable.ic_menu_home));
        ShortcutManager shortcutManager = getSystemService(ShortcutManager.class);
        shortcutManager.addDynamicShortcuts(Collections.singletonList(builder.build()));
    }
*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}