package com.fatapp.oxygentoolbox.ui.home;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.fatapp.oxygentoolbox.R;
import com.fatapp.oxygentoolbox.layout.FoldLayout;
import com.fatapp.oxygentoolbox.util.ToolsLauncher;
import com.fatapp.oxygentoolbox.util.ToolsList;
import com.fatapp.oxygentoolbox.util.VibratorController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private View root;

    private HomeViewModel homeViewModel;

    private LinearLayout foldLayoutsLinearLayout;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);

        //init
        initView();
        initLayout();

        return root;
    }

    private void initView() {
        foldLayoutsLinearLayout = root.findViewById(R.id.fold_layouts_linear_layout);
    }

    private void initLayout() {
        initFoldLayout();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initFoldLayout() {
        try {
            ToolsList.init(getResources().getAssets().open("json/BasicTools.json"));
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), R.string.init_tools_failed, Toast.LENGTH_LONG).show();
            return;
        }

        for (ToolsList.Tool tool : ToolsList.getToolList()) {
            View foldLayoutBodyLayout = getLayoutInflater().inflate(R.layout.fold_layout_body, null);
            ViewGroup autoLinefeedLayout = foldLayoutBodyLayout.findViewById(R.id.auto_linefeed_layout);

            for (ToolsList.Button button : tool.getButtonList()) {
                View toolButtonLayout = getLayoutInflater().inflate(R.layout.tool_button, null);
                Button toolButton = toolButtonLayout.findViewById(R.id.tool_button);
                toolButton.setText(button.getText());

                toolButton.setOnClickListener(v -> ToolsLauncher.launch(getContext(), button.getActivity()));

                toolButton.setOnTouchListener((v, event) -> {
                    if (event.getAction()== MotionEvent.ACTION_DOWN) {
                        v.animate().translationZ(8f).setDuration(100L);
                    }
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        v.animate().translationZ(0).setDuration(100L);
                    }
                    return false;
                });

                toolButton.setOnLongClickListener(v -> {
                    v.animate().translationZ(0).setDuration(100L);
                    VibratorController.vibrate(1);
                    return false;
                });

                autoLinefeedLayout.addView(toolButtonLayout);
            }

            List<View> viewList = new ArrayList<>();
            viewList.add(foldLayoutBodyLayout);

            View foldLayoutHead = getLayoutInflater().inflate(R.layout.fold_layout, null);
            FoldLayout foldLayout = foldLayoutHead.findViewById(R.id.fold_layout);
            ((TextView) foldLayout.findViewById(R.id.fold_layout_text_view)).setText(tool.getFoldLayoutTitle());

            TextView foldLayoutIcon = foldLayout.findViewById(R.id.fold_layout_icon);
            foldLayoutIcon.setTypeface(Typeface.createFromAsset(requireContext().getAssets(), tool.getFont()));
            foldLayoutIcon.setText(tool.getIcon());

            foldLayout.addItemView(viewList);

            foldLayoutsLinearLayout.removeAllViews();
            foldLayoutsLinearLayout.addView(foldLayoutHead);
        }

/*
        for (int i = 0; i < 10; i++) {
            View toolButton = getLayoutInflater().inflate(R.layout.tool_button, null);
            ((Button) toolButton.findViewById(R.id.tool_button)).setText("Button");
            toolButton.findViewById(R.id.tool_button).setOnClickListener(v -> {
                BasicToolsLauncher.launch(0, getContext());
            });

            View foldLayoutBody = getLayoutInflater().inflate(R.layout.fold_layout_body, null);
            ViewGroup layout_item_AutoLinefeedLayout = foldLayoutBody.findViewById(R.id.auto_linefeed_layout);
            layout_item_AutoLinefeedLayout.addView(toolButton);

            List<View> viewList = new ArrayList<>();
            viewList.add(foldLayoutBody);

            View foldLayoutHead = getLayoutInflater().inflate(R.layout.fold_layout, null);
            FoldLayout foldLayout = foldLayoutHead.findViewById(R.id.fold_layout);
            ((TextView) foldLayout.findViewById(R.id.fold_layout_text_view)).setText("FoldLayout_" + i);
            foldLayout.addItemView(viewList);

            foldLayoutsLinearLayout.addView(foldLayoutHead);
        }
*/
    }


}