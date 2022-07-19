package com.fatapp.oxygentoolbox.ui.about;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.fatapp.oxygentoolbox.R;
import com.fatapp.oxygentoolbox.ui.about.util.LibrariesAdapter;
import com.fatapp.oxygentoolbox.util.DependenciesJson;
import com.fatapp.oxygentoolbox.util.MultiLanguageUtils;
import com.fatapp.oxygentoolbox.util.ResourceUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class LibrariesActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ConstraintLayout librariesPage;
    private RecyclerView librariesRecyclerView;
    private SearchView searchView;

    private LibrariesAdapter librariesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libraries);

        initView();
        initLayout();
        loadLibraries();
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        librariesPage = findViewById(R.id.libraries_page);
        librariesRecyclerView = findViewById(R.id.libraries_recycler_view);
    }

    private void initLayout() {
        librariesPage.setPadding(0, ResourceUtil.getStatusBarHeight(getWindow(), getApplicationContext()), 0, 0);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.setting_open_source);
    }

    private void loadLibraries() {
        StringBuilder dependenciesJson = new StringBuilder();
        try {
            InputStream inputStream = ResourceUtil.getResources().openRawResource(R.raw.dependencies);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str;
            while ((str = bufferedReader.readLine()) != null) {
                dependenciesJson.append(str);
            }
            DependenciesJson dependencies = new Gson().fromJson(dependenciesJson.toString(), new TypeToken<DependenciesJson>() {
            }.getType());
            librariesRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            librariesAdapter = new LibrariesAdapter(this, dependencies);
            librariesRecyclerView.addItemDecoration(new LibrariesAdapter.LibrariesItemDecoration());
            librariesRecyclerView.setAdapter(librariesAdapter);
        } catch (IOException e) {
            Log.d("TAG", e.toString());
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.seach_view, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                librariesAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                librariesAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }

    @Override
    public void onBackPressed() {
        // Close search view when back button pressed
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }

        super.onBackPressed();
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