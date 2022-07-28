package com.rikkei.awesome.ui.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.rikkei.awesome.R;
import com.rikkei.awesome.ui.FriendListFragment;
import com.rikkei.awesome.ui.MessageFragment;
import com.rikkei.awesome.ui.PersonalFragment;
import com.rikkei.awesome.ui.SearchFriendFragment;
import com.rikkei.awesome.ui.SearchMessageFragment;

public class MainFragment extends Fragment {
    View view;
    BottomNavigationView nav_bottom;
    RelativeLayout block_personal, block_special, searchView;
    RecyclerView recyclerView;
    Toolbar toolbar;
    TextView title, search_title;
    Context context;
    int id_nav = 1;

    FrameLayout fragment_container;
    ImageView img_icon;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        init();

        getChildFragmentManager().beginTransaction().replace(R.id.fragment_container, new MessageFragment(context)).addToBackStack("message").commit();

        searchView.setOnClickListener(searchListener);

        nav_bottom.setItemHorizontalTranslationEnabled(true);
        nav_bottom.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        hideBlock();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener(){
        @SuppressLint({"NonConstantResourceId", "UseCompatLoadingForDrawables"})
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Drawable res;
            switch (item.getItemId()){
                case R.id.message_view:
                    res = getResources().getDrawable(getResources().getIdentifier("@drawable/ic_create_message", null, getActivity().getPackageName()));
                    img_icon.setImageDrawable(res);
                    title.setText("Tin nhắn");
                    hideBlock();
                    search_title.setText("Tìm kiếm tin nhắn...");
                    id_nav = 1;
                    getChildFragmentManager().beginTransaction().replace(R.id.fragment_container, new MessageFragment(context)).addToBackStack(null).commit();
                    return true;
                case R.id.friend_view:
                    hideBlock();
                    title.setText("Bạn bè   ");
                    search_title.setText("Tìm kiếm bạn bè...");
                    res = getResources().getDrawable(getResources().getIdentifier("@drawable/ic_add_friend", null, getActivity().getPackageName()));
                    img_icon.setImageDrawable(res);
                    id_nav = 2;
                    getChildFragmentManager().beginTransaction().replace(R.id.fragment_container, new FriendListFragment(context)).addToBackStack(null).commit();
                    return true;
                case R.id.personal_view:
                    callBlock();
                    id_nav = 3;
                    getChildFragmentManager().beginTransaction().replace(R.id.fragment_container_2, new PersonalFragment(context, nav_bottom)).addToBackStack(null).commit();
                    return true;
            }
            return false;
        }
    };

    private RelativeLayout.OnClickListener searchListener = new RelativeLayout.OnClickListener() {
        @Override
        public void onClick(View v) {
            block_personal.setVisibility(View.VISIBLE);
            if (id_nav == 1){
                getChildFragmentManager().beginTransaction().replace(R.id.fragment_container_2, new SearchMessageFragment(context)).addToBackStack(null).commit();
            } else if (id_nav == 2){
                getChildFragmentManager().beginTransaction().replace(R.id.fragment_container_2, new SearchFriendFragment(context)).addToBackStack(null).commit();
            }
        }
    };

    void callBlock(){
        block_personal.setVisibility(View.VISIBLE);

        title.setVisibility(View.GONE);
        img_icon.setVisibility(View.GONE);
        searchView.setVisibility(View.GONE);
    }

    public void hideBlock(){
        block_personal.setVisibility(View.GONE);
        title.setVisibility(View.VISIBLE);
        img_icon.setVisibility(View.VISIBLE);
        searchView.setVisibility(View.VISIBLE);
    }

    void init(){
        nav_bottom = view.findViewById(R.id.nav_bottom);
        recyclerView = view.findViewById(R.id.recycler_message);
        toolbar = view.findViewById(R.id.toolbar);
        title = view.findViewById(R.id.toolbar_title);
        img_icon = view.findViewById(R.id.img_icon);
        searchView = view.findViewById(R.id.search);
        fragment_container = view.findViewById(R.id.fragment_container);
        block_personal = view.findViewById(R.id.block_personal);
        block_special = view.findViewById(R.id.block_special);
        search_title = view.findViewById(R.id.searchbar_title);
        block_personal.setVisibility(View.GONE);

    }
}
