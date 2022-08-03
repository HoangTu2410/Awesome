package com.rikkei.awesome.ui.home.friend;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.rikkei.awesome.R;
import com.rikkei.awesome.adapter.FriendHomeAdapter;

public class FriendHomeFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SearchView searchView;
    private TextView txtCancel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_friend_home,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabLayout = view.findViewById(R.id.tab_layout_friend);
        viewPager = view.findViewById(R.id.view_pager_friend);
        searchView = view.findViewById(R.id.searchView);
        txtCancel = view.findViewById(R.id.txtCancel);
        FragmentManager manager = getChildFragmentManager();
        FriendHomeAdapter adapter = new FriendHomeAdapter(manager, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                txtCancel.setVisibility(TextView.VISIBLE);
            }
        });

        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtCancel.setVisibility(TextView.GONE);
            }
        });
    }
}
