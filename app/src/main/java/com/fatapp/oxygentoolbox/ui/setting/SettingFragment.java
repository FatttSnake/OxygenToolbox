package com.fatapp.oxygentoolbox.ui.setting;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.fatapp.oxygentoolbox.MainActivity;
import com.fatapp.oxygentoolbox.R;
import com.fatapp.oxygentoolbox.util.ResourceUtil;

public class SettingFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.fragment_setting, rootKey);
        ListPreference appLanguagePreference = findPreference(ResourceUtil.getString(R.string.setting_language_key));
        if (appLanguagePreference != null) {
            appLanguagePreference.setOnPreferenceChangeListener((preference, newValue) -> {
                ResourceUtil.restartActivity(requireActivity(), MainActivity.class);
                return true;
            });
        }

        Preference aboutPreference = findPreference(ResourceUtil.getString(R.string.setting_about_oxygen_toolbox_key));
        if (aboutPreference != null) {
            aboutPreference.setOnPreferenceClickListener(preference -> {
                Navigation.findNavController(requireView()).navigate(R.id.action_setting_to_about);
                return true;
            });
        }

        ListPreference uiModePreference = findPreference(ResourceUtil.getString(R.string.setting_ui_mode_key));
        if (uiModePreference != null) {
            uiModePreference.setOnPreferenceChangeListener((preference, newValue) -> {
                ResourceUtil.restartActivity(requireActivity(), MainActivity.class);
                return true;
            });
        }
    }
}