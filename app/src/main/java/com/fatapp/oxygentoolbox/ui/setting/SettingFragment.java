package com.fatapp.oxygentoolbox.ui.setting;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.fatapp.oxygentoolbox.MainActivity;
import com.fatapp.oxygentoolbox.R;
import com.fatapp.oxygentoolbox.ui.about.LibrariesActivity;
import com.fatapp.oxygentoolbox.util.ResourceUtil;

public class SettingFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.fragment_setting, rootKey);
        ListPreference appLanguagePreference = findPreference(ResourceUtil.getString(R.string.setting_language_key));
        if (appLanguagePreference != null) {
            appLanguagePreference.setOnPreferenceChangeListener((preference, newValue) -> {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                requireActivity().startActivity(intent);
                requireActivity().finish();
                return true;
            });
        }

        Preference aboutPreference = findPreference(ResourceUtil.getString(R.string.setting_about_oxygen_toolbox_key));
        if (aboutPreference != null) {
            aboutPreference.setOnPreferenceClickListener(preference -> {
                Intent intent = new Intent(getActivity(), LibrariesActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
//                Navigation.findNavController(requireView()).navigate(R.id.action_setting_to_about);
                return true;
            });
        }
    }
}