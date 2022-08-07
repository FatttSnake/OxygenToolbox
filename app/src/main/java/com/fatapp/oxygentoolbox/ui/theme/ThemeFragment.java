package com.fatapp.oxygentoolbox.ui.theme;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fatapp.oxygentoolbox.R;
import com.fatapp.oxygentoolbox.ui.theme.util.ThemesAdapter;

public class ThemeFragment extends Fragment {

    private ThemeViewModel themeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        themeViewModel = new ViewModelProvider(this).get(ThemeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_theme, container, false);

        RecyclerView themeRecyclerView = root.findViewById(R.id.theme_recycler_view);
        themeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        themeRecyclerView.setAdapter(new ThemesAdapter(getActivity()));

        return root;
    }
}