package com.fatapp.oxygentoolbox.util.tool;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.fatapp.oxygentoolbox.R;
import com.fatapp.oxygentoolbox.util.ResourceUtil;

import java.util.Objects;

public class BaseActivityNormal extends AppCompatActivity {
    private final String TOOL_NAME = ToolsList.getToolName(getClass().getName());

    private ConstraintLayout constraintLayoutRoot;
    private Toolbar toolbar;
    private ConstraintLayout constraintLayoutContent;

    private void _initView() {
        constraintLayoutRoot = findViewById(R.id.constraint_layout_root);
        toolbar = findViewById(R.id.toolbar);
        constraintLayoutContent = findViewById(R.id.constraint_layout_content);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool_base_normal);
        _initView();
        _initLayout();
    }

    protected void loadView(Context context, @LayoutRes int layoutResID) {
        LayoutInflater.from(context).inflate(layoutResID, constraintLayoutContent);
    }

    private void _initLayout() {
        constraintLayoutRoot.setPadding(0, ResourceUtil.getStatusBarHeight(getWindow(), getApplicationContext()), 0, 0);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setTitle(TOOL_NAME);
    }

    protected String getToolName() {
        return TOOL_NAME;
    }

    protected ConstraintLayout getConstraintLayoutRoot() {
        return constraintLayoutRoot;
    }

    protected Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
