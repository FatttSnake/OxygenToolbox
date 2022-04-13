package com.fatapp.oxygentoolbox.ui.theme;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.fatapp.oxygentoolbox.R;

public class ThemeFragment extends Fragment {

    private ThemeViewModel themeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        themeViewModel = new ViewModelProvider(this).get(ThemeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_theme, container, false);
        final TextView textView = root.findViewById(R.id.text_theme);
        themeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}