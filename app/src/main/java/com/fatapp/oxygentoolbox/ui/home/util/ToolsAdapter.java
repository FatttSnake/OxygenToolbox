package com.fatapp.oxygentoolbox.ui.home.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fatapp.oxygentoolbox.R;
import com.fatapp.oxygentoolbox.layout.AutoLinefeedLayout;
import com.fatapp.oxygentoolbox.layout.FoldLayout;
import com.fatapp.oxygentoolbox.util.VibratorController;
import com.fatapp.oxygentoolbox.util.tool.ToolsLauncher;
import com.fatapp.oxygentoolbox.util.tool.ToolsList;

import java.util.Collections;

public class ToolsAdapter extends RecyclerView.Adapter<ToolsAdapter.ViewHolder> {
    private ViewGroup parent;
    private final Activity activity;

    public ToolsAdapter(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.parent = parent;
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tools, parent, false);
        return new ViewHolder(inflate);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        View foldLayoutBodyLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.fold_layout_body, parent, false);
        AutoLinefeedLayout autoLinefeedLayout = foldLayoutBodyLayout.findViewById(R.id.auto_linefeed_layout);
        ToolsList.Tool tool = ToolsList.getToolList().get(position);
        for (ToolsList.Button button : tool.getButtonList()) {
            View toolButtonLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.fold_layout_button, parent, false);
            Button toolButton = toolButtonLayout.findViewById(R.id.button_tool);
            toolButton.setText(button.getText());
            toolButton.setOnClickListener(view -> ToolsLauncher.launch(activity, parent.getContext(), button.getActivity()));
            toolButton.setOnTouchListener((view, motionEvent) -> {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    view.animate().translationZ(8f).setDuration(100L);
                }else {
                    view.animate().translationZ(0).setDuration(100L);
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

        TextView foldLayoutIcon = holder.getFoldLayout().findViewById(R.id.text_view_icon);
        foldLayoutIcon.setTypeface(Typeface.createFromAsset(parent.getContext().getAssets(), tool.getFont()));
        foldLayoutIcon.setText(tool.getIcon());
        ((TextView) holder.getFoldLayout().findViewById(R.id.text_view_title)).setText(tool.getFoldLayoutTitle());
        holder.getFoldLayout().addItemView(Collections.singletonList(foldLayoutBodyLayout));
    }

    @Override
    public int getItemCount() {
        return ToolsList.getToolList().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final FoldLayout foldLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            foldLayout = itemView.findViewById(R.id.fold_layout);
        }

        public FoldLayout getFoldLayout() {
            return foldLayout;
        }
    }
}
