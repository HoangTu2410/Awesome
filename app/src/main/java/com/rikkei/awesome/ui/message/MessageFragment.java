package com.rikkei.awesome.ui.message;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.rikkei.awesome.MainActivity;
import com.rikkei.awesome.adapter.MessageAdapter;
import com.rikkei.awesome.R;
import com.rikkei.awesome.model.RoomChat;
import com.rikkei.awesome.model.User;

import java.util.ArrayList;

public class MessageFragment extends Fragment implements MessageInterface{

    View view;
    Context context;
    RecyclerView recyclerView;
    MessagePresenter messagePresenter;
    String UId;

    public MessageFragment(){}
    public MessageFragment(Context context, String UId){
        this.context = context;
        this.UId = UId;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        messagePresenter = new MessagePresenter(this, (MainActivity) getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_child_message, container, false);
        Init();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


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

    @Override
    public void showListRoomChat(ArrayList<RoomChat> roomChats) {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new MessageAdapter(context, roomChats));
    }

    @Override
    public void showListRoomChatFailed() {
        Toast.makeText(context, R.string.getListFailed, Toast.LENGTH_SHORT).show();
    }
}
