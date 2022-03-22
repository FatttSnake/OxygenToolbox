package com.fatapp.oxygentoolbox.ui.home;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
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
import com.fatapp.oxygentoolbox.util.BasicToolsLauncher;
import com.fatapp.oxygentoolbox.util.ToolsList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    private void initFoldLayout() {
        try {
            ToolsList.init(getResources().getAssets().open("json/BasicTools.json"));
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "初始化工具集失败", Toast.LENGTH_LONG).show();
            return;
        }

        for (ToolsList.Tool tool : ToolsList.getToolList()) {
            View foldLayoutBodyLayout = getLayoutInflater().inflate(R.layout.fold_layout_body, null);
            ViewGroup autoLinefeedLayout = foldLayoutBodyLayout.findViewById(R.id.auto_linefeed_layout);

            for (ToolsList.Button button : tool.getButtonList()) {
                View toolButtonLayout = getLayoutInflater().inflate(R.layout.tool_button, null);

                Button toolButton = toolButtonLayout.findViewById(R.id.tool_button);
                toolButton.setText(button.getText());
                toolButton.setOnClickListener(v -> {
                    BasicToolsLauncher.launch(button.getActivity(), getContext());
                });
                autoLinefeedLayout.addView(toolButtonLayout);
            }

            List<View> viewList = new ArrayList<>();
            viewList.add(foldLayoutBodyLayout);

            View foldLayoutHead = getLayoutInflater().inflate(R.layout.fold_layout, null);
            FoldLayout foldLayout = foldLayoutHead.findViewById(R.id.fold_layout);
            ((TextView) foldLayout.findViewById(R.id.fold_layout_text_view)).setText(tool.getFoldLayoutTitle());

            TextView foldLayoutIcon = foldLayout.findViewById(R.id.fold_layout_icon);
            foldLayoutIcon.setTypeface(Typeface.createFromAsset(Objects.requireNonNull(getContext()).getAssets(), tool.getFont()));
            foldLayoutIcon.setText(tool.getIcon());

            foldLayout.addItemView(viewList);

            foldLayoutsLinearLayout.removeAllViews();
            foldLayoutsLinearLayout.addView(foldLayoutHead);
        }

        /*for (int i = 0; i < 10; i++) {
            View toolButton = getLayoutInflater().inflate(R.layout.tool_button, null);
            ((Button) toolButton.findViewById(R.id.toolButton)).setText("Button");
            toolButton.findViewById(R.id.toolButton).setOnClickListener(v -> {
                BasicToolsLauncher.launch(0, getContext());
            });

            View foldLayoutBody = getLayoutInflater().inflate(R.layout.fold_layout_body, null);
            ViewGroup layout_item_AutoLinefeedLayout = foldLayoutBody.findViewById(R.id.layout_item_AutoLinefeedLayout);
            layout_item_AutoLinefeedLayout.addView(toolButton);

            List<View> viewList = new ArrayList<>();
            viewList.add(foldLayoutBody);

            View foldLayoutHead = getLayoutInflater().inflate(R.layout.fold_layout, null);
            FoldLayout foldLayout = foldLayoutHead.findViewById(R.id.foldLayout);
            ((TextView) foldLayout.findViewById(R.id.foldLayoutTextView)).setText("FoldLayout_" + i);
            foldLayout.addItemView(viewList);

            foldLayoutsLinearLayout = root.findViewById(R.id.foldLayoutsLinearLayout);
            foldLayoutsLinearLayout.addView(foldLayoutHead);
        }*/
    }


}