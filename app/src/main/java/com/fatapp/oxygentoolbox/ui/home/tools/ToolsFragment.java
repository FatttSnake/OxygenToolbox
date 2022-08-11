package com.fatapp.oxygentoolbox.ui.home.tools;

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
import com.fatapp.oxygentoolbox.ui.home.util.ToolsAdapter;

public class ToolsFragment extends Fragment {

    private View root;

    private ToolsViewModel toolsViewModel;

    private RecyclerView toolsRecyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        toolsViewModel = new ViewModelProvider(this).get(ToolsViewModel.class);
        root = inflater.inflate(R.layout.fragment_home_tools, container, false);

        //init
        initView();
        initLayout();

        return root;
    }

    private void initView() {
        toolsRecyclerView = root.findViewById(R.id.tools_recycler_view);
    }

    private void initLayout() {
        toolsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        toolsRecyclerView.setAdapter(new ToolsAdapter(getActivity()));
    }
}