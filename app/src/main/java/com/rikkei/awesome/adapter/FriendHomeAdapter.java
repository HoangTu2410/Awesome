package com.rikkei.awesome.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.rikkei.awesome.ui.home.friend.AllUserFragment;
import com.rikkei.awesome.ui.home.friend.ListFriendFragment;
import com.rikkei.awesome.ui.home.friend.RequestFragment;

public class FriendHomeAdapter extends FragmentStatePagerAdapter {
    public FriendHomeAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                ListFriendFragment listFriendFragment = new ListFriendFragment();
                return listFriendFragment;
            case 1:
                AllUserFragment allUserFragment = new AllUserFragment();
                return allUserFragment;
            case 2:
                RequestFragment requestFragment = new RequestFragment();
                return requestFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0: return "BẠN BÈ";
            case 1: return "TẤT CẢ";
            case 2: return "YÊU CẦU";
        }
        return null;
    }
}
