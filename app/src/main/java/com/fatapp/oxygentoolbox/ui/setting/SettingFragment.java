package com.fatapp.oxygentoolbox.ui.setting;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.fatapp.oxygentoolbox.MainActivity;
import com.fatapp.oxygentoolbox.R;
import com.fatapp.oxygentoolbox.util.MultiLanguageUtils;
import com.fatapp.oxygentoolbox.util.ResourceUtil;

import java.util.Objects;

public class SettingFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.fragment_setting, rootKey);
        ListPreference appLanguage = findPreference("app_language");
        if (appLanguage != null) {
            appLanguage.setOnPreferenceChangeListener((preference, newValue) -> {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                requireActivity().startActivity(intent);
                requireActivity().finish();
                return true;
            });
        }
    }
}