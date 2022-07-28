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

import com.rikkei.awesome.Adapter.MessageAdapter;
import com.rikkei.awesome.R;
import com.rikkei.awesome.model.Message;

import java.util.ArrayList;

public class MessageFragment extends Fragment {

    View view;
    Context context;
    RecyclerView recyclerView;

    public MessageFragment(){}
    public MessageFragment(Context context){
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_child_message, container, false);

        Init();

        ArrayList<Message> arrayList = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new MessageAdapter(context, arrayList));

        return view;
    }

    void Init(){
        recyclerView = view.findViewById(R.id.recycler_message);
    }
}
