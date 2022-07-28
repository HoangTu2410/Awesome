package com.rikkei.awesome.ui.message;

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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.rikkei.awesome.adapter.MessageAdapter;
import com.rikkei.awesome.R;
import com.rikkei.awesome.model.RoomChat;
import com.rikkei.awesome.model.User;

import java.util.ArrayList;

public class MessageFragment extends Fragment implements MessageInterface{

    View view;
    Context context;
    RecyclerView recyclerView;
    String UId;

    public MessageFragment(){}
    public MessageFragment(Context context, String UId){
        this.context = context;
        this.UId = UId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_child_message, container, false);

        Init();

        ArrayList<RoomChat> arrayList = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new MessageAdapter(context, arrayList));

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MessagePresenter.getListRoom(UId, new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    void Init(){
        recyclerView = view.findViewById(R.id.recycler_message);
    }



    @Override
    public void onUserLongClicked(User user, int position) {

    }

    @Override
    public void onMessageClicked(User user) {

    }
}
