package com.rikkei.awesome.ui.message;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.rikkei.awesome.MainActivity;
import com.rikkei.awesome.R;
import com.rikkei.awesome.adapter.RoomChatAdapter;
import com.rikkei.awesome.model.Member;
import com.rikkei.awesome.model.RoomChat;
import com.rikkei.awesome.model.User;
import com.rikkei.awesome.ui.roomchat.RoomChatFragment;
import com.rikkei.awesome.utils.FirebaseQuery;
import com.rikkei.awesome.utils.ItemClickSupport;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MessagePresenter {

    private MessageInterface messageInterface;
    private Context context;
    private List<RoomChat> roomChats = new ArrayList<>();
    private User currentUser = new User();
    private List<Member> members = new ArrayList<>();


    public MessagePresenter(MessageInterface messageInterface, Context context) {
        this.messageInterface = messageInterface;
        this.context = context;
    }

    public void getListRoom(RecyclerView recyclerView, String UId) {
//        FirebaseQuery.getListRoomChatFirst(UId, new ValueEventListener() {
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
//                messageInterface.showListRoomChatFailed();
//            }
//        }); //get all roomchat start with Uid //lost query
        FirebaseQuery.getUser(UId, new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                currentUser = snapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseQuery.getListMember(UId, new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                members.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Member member = dataSnapshot.getValue(Member.class);
                    if (member.getUser1() == currentUser.getId() || member.getUser2() == currentUser.getId())
                    members.add(member);
                }
             }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseQuery.getListRoomChatLast(UId, new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                roomChats.clear();
                if (snapshot.exists()){
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                        RoomChat roomChat = dataSnapshot.getValue(RoomChat.class);
                        for (Member member : members) {
                            if (member.getId() == roomChat.getId()) roomChats.add(roomChat);
                        }

                    }
                }
                //getListUser();

                recyclerView.setAdapter(new RoomChatAdapter(context, roomChats, members));
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                messageInterface.showListRoomChatFailed();
            }
        });//get all roomchat end with Uid


        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(((recyclerView1, position, v) -> {
            messageInterface.openRoomChat();
        }));
    }

}
