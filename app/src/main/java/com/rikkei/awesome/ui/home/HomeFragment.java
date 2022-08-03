package com.rikkei.awesome.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.rikkei.awesome.R;
import com.rikkei.awesome.adapter.HomeAdapter;

public class HomeFragment extends Fragment {

    private BottomNavigationView nav_bottom;
    private ViewPager viewPager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (viewPager.getCurrentItem()>0) {
                    viewPager.setCurrentItem(0);
                } else {
                    if (getActivity().getSupportFragmentManager().getBackStackEntryCount()>0) {
                        getActivity().getSupportFragmentManager().popBackStack();
                    }
                }
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this,callback);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nav_bottom = view.findViewById(R.id.nav_bottom);
        viewPager = view.findViewById(R.id.fragment_container);
        HomeAdapter homeAdapter = new HomeAdapter(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(homeAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0: nav_bottom.getMenu().findItem(R.id.message_view).setChecked(true);
                        break;
                    case 1: nav_bottom.getMenu().findItem(R.id.friend_view).setChecked(true);
                        break;
                    case 2: nav_bottom.getMenu().findItem(R.id.profile_view).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        nav_bottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.message_view:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.friend_view:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.profile_view:
                        viewPager.setCurrentItem(2);
                        break;
                }
                return true;
            }
        });
    }
}
