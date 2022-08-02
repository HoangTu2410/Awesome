package com.rikkei.awesome.ui.roomchat;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.rikkei.awesome.model.Message;
import com.rikkei.awesome.model.User;
import com.rikkei.awesome.utils.FirebaseQuery;

import java.util.ArrayList;
import java.util.List;

public class RoomChatPresenter {
    private RoomChatInterface roomChatInterface;
    private Context context;
    private List<Message> messageList = new ArrayList<>();
    String Uid;
    private User user1, user2;

    public RoomChatPresenter(){}
    public RoomChatPresenter(Context context, RoomChatInterface roomChatInterface, String Uid){
        this.context = context;
        this.roomChatInterface = roomChatInterface;
        this.Uid = Uid;
        setUser1();
    }

    public void setUser2(){

    }

    public void setUser1(){
        FirebaseQuery.getUser(Uid, new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user1 = snapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
