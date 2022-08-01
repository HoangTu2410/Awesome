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

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.core.Query;
import com.rikkei.awesome.adapter.RoomChatAdapter;
import com.rikkei.awesome.R;
import com.rikkei.awesome.model.RoomChat;
import com.rikkei.awesome.ui.roomchat.RoomChatFragment;
import com.rikkei.awesome.utils.FirebaseQuery;
import com.rikkei.awesome.utils.ItemClickSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_child_message, container, false);
        Init();
        messagePresenter = new MessagePresenter(this, context);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        messagePresenter.getListRoom(recyclerView);
//        List<RoomChat> roomChats = new ArrayList<>();
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("room_chat");
//        myRef.orderByKey().addValueEventListener( new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
//                    RoomChat roomChat = dataSnapshot.getValue(RoomChat.class);
//                    roomChats.add(roomChat);
//                }
//                recyclerView.setAdapter(new RoomChatAdapter(context, roomChats));
//                recyclerView.setLayoutManager(new LinearLayoutManager(context));
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                showListRoomChatFailed();
//            }
//        });
    }

    void Init(){
        recyclerView = view.findViewById(R.id.recycler_message);
    }


    @Override
    public void showListRoomChat(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    public void showListRoomChatFailed() {
        Toast.makeText(context, R.string.getListFailed, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void openRoomChat() {
        getParentFragmentManager().beginTransaction().replace(R.id.fragment_container_3, new RoomChatFragment(), "RoomChat").addToBackStack("RoomChat").commit();
    }
}
