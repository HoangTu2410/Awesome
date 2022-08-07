package com.rikkei.awesome.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.rikkei.awesome.ui.home.friend.FriendHomeFragment;
import com.rikkei.awesome.ui.home.message.ListMessageFragment;
import com.rikkei.awesome.ui.home.profile.ProfileFragment;

public class HomeAdapter extends FragmentStateAdapter {

    public HomeAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: return new ListMessageFragment();
            case 1: return new FriendHomeFragment();
            case 2: return new ProfileFragment();
        }
        return new ListMessageFragment();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
