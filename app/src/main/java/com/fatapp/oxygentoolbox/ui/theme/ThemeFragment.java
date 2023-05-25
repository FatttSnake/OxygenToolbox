package com.fatapp.oxygentoolbox.ui.theme;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fatapp.oxygentoolbox.R;
import com.fatapp.oxygentoolbox.ui.theme.util.ThemesAdapter;

public class ThemeFragment extends Fragment {
    private RecyclerView recyclerViewThemes;

    private void initView(View root) {
        recyclerViewThemes = root.findViewById(R.id.recycler_view_themes);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_theme, container, false);

        initView(root);
        initThemes();

        return root;
    }

    private void initThemes() {
        recyclerViewThemes.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewThemes.setAdapter(new ThemesAdapter(getActivity()));
    }
}