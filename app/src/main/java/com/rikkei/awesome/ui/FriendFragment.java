package com.rikkei.awesome.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rikkei.awesome.Adapter.FriendAdapter;
import com.rikkei.awesome.R;
import com.rikkei.awesome.model.User;

import java.util.ArrayList;

public class FriendFragment extends Fragment {

    View view;
    Context context;
    RecyclerView recyclerView;

    public FriendFragment() {
    }

    public FriendFragment(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_child_friend_list, container, false);
        Init();

        ArrayList<User> arrayList = new ArrayList<>();
        arrayList.add(new User("Nguyen Van A"));
        arrayList.add(new User("Trinh Van A"));
        arrayList.add(new User("Tu Van A"));
        arrayList.add(new User("Nga Van A"));
        arrayList.add(new User("Ba Van A"));
        arrayList.add(new User("Bo Van A"));
        arrayList.add(new User("Nguyen Van A"));
        arrayList.add(new User("An Van A"));
        arrayList.add(new User("Kacr Van A"));
        arrayList.add(new User("Virl Van A"));
        arrayList.add(new User("Nuhn Van A"));
        arrayList.add(new User("Tin Van A"));
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new FriendAdapter(context, arrayList));

        return view;
    }

    void Init(){
        recyclerView = view.findViewById(R.id.recycler_friend_list);
    }
}
