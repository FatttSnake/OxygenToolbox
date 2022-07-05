package com.fatapp.oxygentoolbox.ui.theme;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.fatapp.oxygentoolbox.R;

public class ThemeFragment extends Fragment {

    private ThemeViewModel themeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        themeViewModel = new ViewModelProvider(this).get(ThemeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_theme, container, false);
        return root;
    }
}