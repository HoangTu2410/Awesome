package com.rikkei.awesome.ui.message;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.rikkei.awesome.MainActivity;
import com.rikkei.awesome.R;
import com.rikkei.awesome.adapter.RoomChatAdapter;
import com.rikkei.awesome.model.RoomChat;
import com.rikkei.awesome.ui.roomchat.RoomChatFragment;
import com.rikkei.awesome.utils.FirebaseQuery;
import com.rikkei.awesome.utils.ItemClickSupport;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessagePresenter {

    private MessageInterface messageInterface;
    private Context context;

    public MessagePresenter(MessageInterface messageInterface, Context context) {
        this.messageInterface = messageInterface;
        this.context = context;
    }

    public void getListRoom(RecyclerView recyclerView) {
        FirebaseQuery.getListRoomChat(FirebaseQuery.USERNAME, new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<RoomChat> roomChats = new ArrayList<>();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    RoomChat roomChat = dataSnapshot.getValue(RoomChat.class);
                    roomChats.add(roomChat);
                }
                recyclerView.setAdapter(new RoomChatAdapter(context, roomChats));
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                messageInterface.showListRoomChatFailed();
            }
        });

    }

}
