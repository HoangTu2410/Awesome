package com.rikkei.awesome.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.rikkei.awesome.R;

public class FriendListFragment extends Fragment {

    Context context;
    View view;
    Toolbar toolbar;
    TextView title;
    TabLayout tabLayout;

    public FriendListFragment(Context context) {
        this.context = context;
    }

    public FriendListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_child_friend, container, false);
        Init();
        tabLayout.addOnTabSelectedListener(onTabSelectedListener);
        getChildFragmentManager().beginTransaction().replace(R.id.friend_container, new FriendFragment(context)).addToBackStack(null).commit();

        return view;
    }

    void Init(){
        tabLayout = view.findViewById(R.id.tab_layout_friend);
    }

    private final TabLayout.OnTabSelectedListener onTabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            switch (tab.getPosition()){
                case 1:
                    getChildFragmentManager().beginTransaction().replace(R.id.friend_container, new AllFriendFragment(context)).addToBackStack(null).commit();
                    break;
                case 0:
                    getChildFragmentManager().beginTransaction().replace(R.id.friend_container, new FriendFragment(context)).addToBackStack(null).commit();
                    return;
                case 2:
                    break;

            }
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };
}
