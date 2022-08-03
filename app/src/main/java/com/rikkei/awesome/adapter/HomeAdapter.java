package com.rikkei.awesome.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.rikkei.awesome.ui.home.friend.FriendHomeFragment;
import com.rikkei.awesome.ui.home.message.ListMessageFragment;
import com.rikkei.awesome.ui.home.profile.ProfileFragment;

public class HomeAdapter extends FragmentStatePagerAdapter {

    public HomeAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new ListMessageFragment();
            case 1: return new FriendHomeFragment();
            case 2: return new ProfileFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
