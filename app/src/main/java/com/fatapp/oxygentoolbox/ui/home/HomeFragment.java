package com.fatapp.oxygentoolbox.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.fatapp.oxygentoolbox.R;
import com.fatapp.oxygentoolbox.ui.home.fav.FavFragment;
import com.fatapp.oxygentoolbox.ui.home.tools.ToolsFragment;
import com.fatapp.oxygentoolbox.util.ResourceUtil;
import com.fatapp.oxygentoolbox.util.SharedPreferencesUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeFragment extends Fragment {
    private ViewPager2 viewPagerBottomNav;
    private BottomNavigationView navigationViewBottomNav;

    private void initView(View root) {
        viewPagerBottomNav = root.findViewById(R.id.view_pager_bottom_nav);
        navigationViewBottomNav = root.findViewById(R.id.navigation_view_bottom_nav);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        initView(root);
        initBottomNav();

        return root;
    }

    private void initBottomNav() {
        navigationViewBottomNav.setOnItemSelectedListener(item -> {
            viewPagerBottomNav.setCurrentItem(item.getOrder());
            return true;
        });

        viewPagerBottomNav.setAdapter(new FragmentStateAdapter(this) {
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
        viewPagerBottomNav.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                navigationViewBottomNav.getMenu().getItem(position).setChecked(true);
            }
        });
        viewPagerBottomNav.setCurrentItem(SharedPreferencesUtils.getPreferenceLaunchPage() == ResourceUtil.LaunchPage.TOOLS ? 0 : 1, false);
    }

}