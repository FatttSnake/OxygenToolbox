package com.fatapp.oxygentoolbox.ui.home.fav;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.fatapp.oxygentoolbox.R;

public class FavFragment extends Fragment {

    private View root;

    private FavViewModel favViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        favViewModel = new ViewModelProvider(this).get(FavViewModel.class);
        root = inflater.inflate(R.layout.fragment_home_fav, container, false);

        return root;
    }

}