package com.fatapp.oxygentoolbox.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.fatapp.oxygentoolbox.R;
import com.fatapp.oxygentoolbox.layout.FoldLayout;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    View root;

    private HomeViewModel homeViewModel;

    private FoldLayout foldLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);
        initView();
        return root;
    }

    private void initView() {
        foldLayout = (FoldLayout) root.findViewById(R.id.foldLayout);

        List<View> viewList = new ArrayList<>();

        viewList.add(getLayoutInflater().inflate(R.layout.layout_item, null));

        foldLayout.addItemView(viewList);
    }
}