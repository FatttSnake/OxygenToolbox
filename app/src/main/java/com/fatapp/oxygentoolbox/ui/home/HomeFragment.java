package com.fatapp.oxygentoolbox.ui.home;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fatapp.oxygentoolbox.MainActivity;
import com.fatapp.oxygentoolbox.R;
import com.fatapp.oxygentoolbox.layout.FoldLayout;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    View root;

    private HomeViewModel homeViewModel;

    private FoldLayout foldLayout;
    private FoldLayout foldLayout1;

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

        foldLayout1 = (FoldLayout) root.findViewById(R.id.foldLayout1);
        List<View> viewList1 = new ArrayList<>();
        viewList1.add(getLayoutInflater().inflate(R.layout.layout_item, null));
        foldLayout1.addItemView(viewList1);
    }
}