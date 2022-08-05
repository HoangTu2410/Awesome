package com.rikkei.awesome.ui.message;

import android.content.Context;
import android.os.Build;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.rikkei.awesome.adapter.RoomChatAdapter;
import com.rikkei.awesome.model.Member;
import com.rikkei.awesome.model.RoomChat;
import com.rikkei.awesome.model.User;
import com.rikkei.awesome.utils.FirebaseQuery;
import com.rikkei.awesome.utils.ItemClickSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessagePresenter {

    private final MessageInterface messageInterface;
    private final Context context;
    private final List<RoomChat> roomChats = new ArrayList<>();
    private User currentUser;
    private final List<Member> members = new ArrayList<>();
    private final List<User> users = new ArrayList<>();


    public MessagePresenter(MessageInterface messageInterface, Context context) {
        this.messageInterface = messageInterface;
        this.context = context;
    }

    public void getListRoom(RecyclerView recyclerView, String UId) {
        FirebaseQuery.getListUser( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GenericTypeIndicator<HashMap<String, User>> objGTI = new GenericTypeIndicator<HashMap<String, User>>() {
                    @Override
                    public boolean equals(@Nullable Object obj) {
                        return super.equals(obj);
                    }
                };
                Map<String, User> objHM = snapshot.getValue(objGTI);
                final List<User> objAL = new ArrayList<>(objHM.values());
                for (User tmp : objAL) {
                    users.add(tmp);
                    if (tmp.getId().equals(UId)) currentUser = tmp; //get all information of current user
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseQuery.getListMember(UId, new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GenericTypeIndicator<HashMap<String, Member>> objGTI = new GenericTypeIndicator<HashMap<String, Member>>() {
                    @Override
                    public boolean equals(@Nullable Object obj) {
                        return super.equals(obj);
                    }
                };
                Map<String, Member> objHM = snapshot.getValue(objGTI);
                final List<Member> objAL = new ArrayList<>(objHM.values());
                for (Member tmp: objAL)
                    if (tmp.getUser1().equals(currentUser.getId())||tmp.getUser2().equals(currentUser.getId()))
                        members.add(tmp);
             }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseQuery.getListRoomChatLast(UId, new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GenericTypeIndicator<HashMap<String, RoomChat>> objGTI = new GenericTypeIndicator<HashMap<String, RoomChat>>() {
                    @Override
                    public boolean equals(@Nullable Object obj) {
                        return super.equals(obj);
                    }
                };
                Map<String, RoomChat> objHM = snapshot.getValue(objGTI);
                final List<RoomChat> objAL = new ArrayList<>(objHM.values());
                roomChats.clear();
                for (RoomChat tmp: objAL)
                    for (Member mem: members)
                        if (tmp.getId().equals(mem.getId()))
                            roomChats.add(tmp);

                recyclerView.setAdapter(new RoomChatAdapter(context, roomChats, members, users, UId));
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                messageInterface.showListRoomChatFailed();
            }
        });//get all roomchat end with Uid


        ItemClickSupport.addTo(recyclerView).setOnItemClickListener((new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView1, int position, View v) {
                messageInterface.openRoomChat(roomChats.get(position).getId());
            }
        }));
    }


}
