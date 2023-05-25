package com.fatapp.oxygentoolbox.util.tool;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.fatapp.oxygentoolbox.R;
import com.ypz.bangscreentools.BangScreenTools;

public class BaseActivityHorizontal extends AppCompatActivity {
    private final String TOOL_NAME = ToolsList.getToolName(getClass().getName());
    private ConstraintLayout constraintLayoutRoot;
    private ConstraintLayout constraintLayoutContent;

    private void _initView() {
        constraintLayoutRoot = findViewById(R.id.constraint_layout_root);
        constraintLayoutContent = findViewById(R.id.constraint_layout_content);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        BangScreenTools.getBangScreenTools().fullscreen(getWindow(), this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool_base_horizontal);
        _initView();
    }

    protected void loadView(Context context, @LayoutRes int layoutResID) {
        LayoutInflater.from(context).inflate(layoutResID, constraintLayoutContent);
    }

    protected String getToolName() {
        return TOOL_NAME;
    }

    protected ConstraintLayout getConstraintLayoutRoot() {
        return constraintLayoutRoot;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        BangScreenTools.getBangScreenTools().windowChangeFullscreen(getWindow());
        super.onWindowFocusChanged(hasFocus);
    }
}
