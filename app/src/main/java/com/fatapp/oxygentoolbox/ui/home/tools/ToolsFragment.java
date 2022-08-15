package com.fatapp.oxygentoolbox.ui.home.tools;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fatapp.oxygentoolbox.R;
import com.fatapp.oxygentoolbox.ui.home.util.ToolsAdapter;

public class ToolsFragment extends Fragment {

    private RecyclerView recyclerViewTools;

    private void initView(View root) {
        recyclerViewTools = root.findViewById(R.id.recycler_view_tools);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home_tools, container, false);

        initView(root);
        initTools();

        return root;
    }

    private void initTools() {
        recyclerViewTools.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewTools.setAdapter(new ToolsAdapter(getActivity()));
    }
}