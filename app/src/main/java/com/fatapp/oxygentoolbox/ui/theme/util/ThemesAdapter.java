package com.fatapp.oxygentoolbox.ui.theme.util;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fatapp.oxygentoolbox.R;
import com.fatapp.oxygentoolbox.util.ResourceUtil;
import com.fatapp.oxygentoolbox.util.SharedPreferencesUtils;

public class ThemesAdapter extends RecyclerView.Adapter<ThemesAdapter.ViewHolder> {
    private final Activity activity;

    public ThemesAdapter(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_themes, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getColorName().setText(ResourceUtil.getStringArray(R.array.setting_theme_array)[position]);
        holder.getCheck().setSelected(position == SharedPreferencesUtils.getPreferenceTheme().ordinal());
        switch (position) {
            case 0:
                setColor(holder, R.color.red_primary, R.color.red_primary_dark, R.color.red_primary_light, R.color.red_accent);
                break;
            case 1:
                setColor(holder, R.color.pink_primary, R.color.pink_primary_dark, R.color.pink_primary_light, R.color.pink_accent);
                break;
            case 2:
                setColor(holder, R.color.purple_primary, R.color.purple_primary_dark, R.color.purple_primary_light, R.color.purple_accent);
                break;
            case 3:
                setColor(holder, R.color.deep_purple_primary, R.color.deep_purple_primary_dark, R.color.deep_purple_primary_light, R.color.deep_purple_accent);
                break;
            case 4:
                setColor(holder, R.color.indigo_primary, R.color.indigo_primary_dark, R.color.indigo_primary_light, R.color.indigo_accent);
                break;
            case 5:
                setColor(holder, R.color.blue_primary, R.color.blue_primary_dark, R.color.blue_primary_light, R.color.blue_accent);
                break;
            case 6:
                setColor(holder, R.color.light_blue_primary, R.color.light_blue_primary_dark, R.color.light_blue_primary_light, R.color.light_blue_accent);
                break;
            case 7:
                setColor(holder, R.color.cyan_primary, R.color.cyan_primary_dark, R.color.cyan_primary_light, R.color.cyan_accent);
                break;
            case 8:
                setColor(holder, R.color.teal_primary, R.color.teal_primary_dark, R.color.teal_primary_light, R.color.teal_accent);
                break;
            case 9:
                setColor(holder, R.color.green_primary, R.color.green_primary_dark, R.color.green_primary_light, R.color.green_accent);
                break;
            case 10:
                setColor(holder, R.color.light_green_primary, R.color.light_green_primary_dark, R.color.light_green_primary_light, R.color.light_green_accent);
                break;
            case 11:
                setColor(holder, R.color.lime_primary, R.color.lime_primary_dark, R.color.lime_primary_light, R.color.lime_accent);
                break;
            case 12:
                setColor(holder, R.color.yellow_primary, R.color.yellow_primary_dark, R.color.yellow_primary_light, R.color.yellow_accent);
                break;
            case 13:
                setColor(holder, R.color.amber_primary, R.color.amber_primary_dark, R.color.amber_primary_light, R.color.amber_accent);
                break;
            case 14:
                setColor(holder, R.color.orange_primary, R.color.orange_primary_dark, R.color.orange_primary_light, R.color.orange_accent);
                break;
            case 15:
                setColor(holder, R.color.deep_orange_primary, R.color.deep_orange_primary_dark, R.color.deep_orange_primary_light, R.color.deep_orange_accent);
                break;
            case 16:
                setColor(holder, R.color.brown_primary, R.color.brown_primary_dark, R.color.brown_primary_light, R.color.brown_accent);
                break;
            case 17:
                setColor(holder, R.color.grey_primary, R.color.grey_primary_dark, R.color.grey_primary_light, R.color.grey_accent);
                break;
            case 18:
                setColor(holder, R.color.blue_grey_primary, R.color.blue_grey_primary_dark, R.color.blue_grey_primary_light, R.color.blue_grey_accent);
                break;
        }

        holder.getThemeLayout().setOnClickListener(view -> {
            SharedPreferencesUtils.setPreferenceTheme(SharedPreferencesUtils.Theme.values()[position]);
            ResourceUtil.restartActivity(activity, activity.getClass());
        });
    }

    @Override
    public int getItemCount() {
        return SharedPreferencesUtils.Theme.values().length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final LinearLayout themeLayout;
        private final TextView colorName;
        private final View check;
        private final View primaryColor;
        private final View primaryDarkColor;
        private final View primaryLightColor;
        private final View accentColor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            themeLayout = itemView.findViewById(R.id.theme_layout);
            colorName = itemView.findViewById(R.id.color_name);
            check = itemView.findViewById(R.id.check);
            primaryColor = itemView.findViewById(R.id.primary_color);
            primaryDarkColor = itemView.findViewById(R.id.primary_dark_color);
            primaryLightColor = itemView.findViewById(R.id.primary_light_color);
            accentColor = itemView.findViewById(R.id.accent_color);
        }

        public LinearLayout getThemeLayout() {
            return themeLayout;
        }

        public TextView getColorName() {
            return colorName;
        }

        public View getCheck() {
            return check;
        }

        public View getPrimaryColor() {
            return primaryColor;
        }

        public View getPrimaryDarkColor() {
            return primaryDarkColor;
        }

        public View getPrimaryLightColor() {
            return primaryLightColor;
        }

        public View getAccentColor() {
            return accentColor;
        }
    }

    private void setColor(@NonNull ViewHolder holder, @ColorRes int primary, @ColorRes int primaryDark, @ColorRes int primaryLight, @ColorRes int accent) {
        ((GradientDrawable) holder.getPrimaryColor().getBackground()).setColor(ResourceUtil.getColor(primary));
        ((GradientDrawable) holder.getPrimaryDarkColor().getBackground()).setColor(ResourceUtil.getColor(primaryDark));
        ((GradientDrawable) holder.getPrimaryLightColor().getBackground()).setColor(ResourceUtil.getColor(primaryLight));
        ((GradientDrawable) holder.getAccentColor().getBackground()).setColor(ResourceUtil.getColor(accent));
    }
}
