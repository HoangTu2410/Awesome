package com.rikkei.awesome.ui.roomchat;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.rikkei.awesome.model.Member;
import com.rikkei.awesome.model.Message;
import com.rikkei.awesome.model.RoomChat;
import com.rikkei.awesome.model.User;
import com.rikkei.awesome.utils.FirebaseQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoomChatPresenter {
    private RoomChatInterface roomChatInterface;
    private Context context;
    private List<Message> messageList = new ArrayList<>();
    String roomID, Uid, Fid;
    private User user1, user2;
    private Member member;
    private Message message;
    private RoomChat roomChat;

    public RoomChatPresenter(){}
    public RoomChatPresenter(Context context, RoomChatInterface roomChatInterface, String roomID, String Uid){
        this.context = context;
        this.roomChatInterface = roomChatInterface;
        this.roomID = roomID;
        this.Uid = Uid;

        getMember();
        getRoomChat();
        setUser1();
        setUser2();
    }

    private void getListMessage(RecyclerView recyclerView){
        FirebaseQuery.getListMessage(roomID, new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getMember() {
        FirebaseQuery.getListMember(roomID, new ValueEventListener() {
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
                for (Member tmp : objAL) {
                    if (tmp.getId().equals(roomID)) member = tmp;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        if (member.getUser1().equals(Uid)) Fid = member.getUser2();
        else Fid = member.getUser1();
    }

    private void getRoomChat(){
        FirebaseQuery.getListRoomChatLast(roomID, new ValueEventListener() {
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
                for (RoomChat tmp: objAL)
                    if (tmp.getId().equals(roomID)) roomChat = tmp;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void setUser2(){
        FirebaseQuery.getUser(Fid, new ValueEventListener() {
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
                    if (tmp.getId().equals(Fid)) user2 = tmp; //get all information of current user
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void setUser1(){
        FirebaseQuery.getUser(Uid, new ValueEventListener() {
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
                    if (tmp.getId().equals(Uid)) user1 = tmp; //get all information of current user
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
