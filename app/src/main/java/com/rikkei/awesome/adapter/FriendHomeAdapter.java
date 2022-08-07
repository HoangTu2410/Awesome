package com.rikkei.awesome.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.rikkei.awesome.ui.home.friend.AllUserFragment;
import com.rikkei.awesome.ui.home.friend.ListFriendFragment;
import com.rikkei.awesome.ui.home.friend.RequestFragment;


public class FriendHomeAdapter extends FragmentStateAdapter {

    public FriendHomeAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new ListFriendFragment();
            case 1:
                return new AllUserFragment();
            case 2:
                return new RequestFragment();
        }
        return new ListFriendFragment();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
