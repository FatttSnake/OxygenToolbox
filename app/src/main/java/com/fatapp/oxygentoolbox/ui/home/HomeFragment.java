package com.fatapp.oxygentoolbox.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.fatapp.oxygentoolbox.R;
import com.fatapp.oxygentoolbox.ui.home.fav.FavFragment;
import com.fatapp.oxygentoolbox.ui.home.tools.ToolsFragment;
import com.fatapp.oxygentoolbox.util.SharedPreferencesUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeFragment extends Fragment {

    private View root;

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);

        ViewPager2 bottomNavViewPager = root.findViewById(R.id.bottom_nav_view_pager);
        BottomNavigationView bottomNavigationView = root.findViewById(R.id.bottom_navigation_view);

        bottomNavViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
            }
        });
        bottomNavigationView.setOnItemSelectedListener(item -> {
            bottomNavViewPager.setCurrentItem(item.getOrder());
            return true;
        });
        bottomNavViewPager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                if (position == 1) {
                    return new FavFragment();
                }
                return new ToolsFragment();
            }

            @Override
            public int getItemCount() {
                return 2;
            }
        });

        bottomNavViewPager.setCurrentItem(SharedPreferencesUtils.getPreferenceLaunchPage() == SharedPreferencesUtils.LaunchPage.tools ? 0 : 1, false);

        return root;
    }

}