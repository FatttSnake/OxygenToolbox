package com.fatapp.oxygentoolbox.ui.home;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.fatapp.oxygentoolbox.R;
import com.fatapp.oxygentoolbox.layout.FoldLayout;
import com.fatapp.oxygentoolbox.util.BasicToolsLauncher;
import com.fatapp.oxygentoolbox.util.ToolsList;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    View root;

    private HomeViewModel homeViewModel;

    private LinearLayout foldLayoutsLinearLayout;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);
        initView();
        return root;
    }

    private void initView() {
        for (ToolsList.Tool tool : ToolsList.getToolList()) {

            View foldLayoutBody = getLayoutInflater().inflate(R.layout.fold_layout_body, null);
            ViewGroup layout_item_AutoLinefeedLayout = foldLayoutBody.findViewById(R.id.layout_item_AutoLinefeedLayout);

            for (ToolsList.Button button : tool.getButtonList()) {
                View toolButton = getLayoutInflater().inflate(R.layout.tool_button, null);
                ((Button) toolButton.findViewById(R.id.toolButton)).setText(button.getText());
                toolButton.findViewById(R.id.toolButton).setOnClickListener(v -> {
                    BasicToolsLauncher.launch(button.getActivity(), getContext());
                });
                layout_item_AutoLinefeedLayout.addView(toolButton);
            }

            List<View> viewList = new ArrayList<>();
            viewList.add(foldLayoutBody);

            View foldLayoutHead = getLayoutInflater().inflate(R.layout.fold_layout, null);
            FoldLayout foldLayout = foldLayoutHead.findViewById(R.id.foldLayout);
            ((TextView) foldLayout.findViewById(R.id.foldLayoutTextView)).setText(tool.getFoldLayoutTitle());
            ((TextView) foldLayout.findViewById(R.id.foldLayoutIcon)).setTypeface(Typeface.createFromAsset(getContext().getAssets(), tool.getFont()));
            ((TextView) foldLayout.findViewById(R.id.foldLayoutIcon)).setText(tool.getIcon());

            foldLayout.addItemView(viewList);

            foldLayoutsLinearLayout = root.findViewById(R.id.foldLayoutsLinearLayout);
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