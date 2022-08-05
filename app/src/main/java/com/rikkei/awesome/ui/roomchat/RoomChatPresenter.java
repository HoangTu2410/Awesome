package com.rikkei.awesome.ui.roomchat;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.rikkei.awesome.adapter.MessageAdapter;
import com.rikkei.awesome.model.Member;
import com.rikkei.awesome.model.Message;
import com.rikkei.awesome.model.User;
import com.rikkei.awesome.ui.message.MessagePresenter;
import com.rikkei.awesome.utils.FirebaseQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoomChatPresenter {
    private final RoomChatInterface roomChatInterface;
    private final Context context;
    private final List<Message> messageList = new ArrayList<>();
    private final String roomID, Uid;
    private String Fid;
    private User user1 = new User();
    private User user2 = new User();
    private final List<Member> members = new ArrayList<>();
    private  Member member;
    private Message message;



    public RoomChatPresenter(RoomChatInterface roomChatInterface, Context context, String roomID, String uid) {
        this.roomChatInterface = roomChatInterface;
        this.context = context;
        this.roomID = roomID;
        this.Uid = uid;
    }

    public void getListMessage(RecyclerView recyclerView){
        FirebaseQuery.getListMessage(roomID, new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Message message = snapshot.getValue(Message.class);
                messageList.add(message);
                recyclerView.smoothScrollToPosition(messageList.size());
                recyclerView.setAdapter(new MessageAdapter(context, messageList, Uid));
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

    public void InitRoomChat(String Uid, String friendName) {
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
                for (Member tmp: objAL)
                    if (tmp.getId().equals(roomID))
                        members.add(tmp);

                if (!members.get(0).getId().equals(Uid)) Fid = members.get(0).getUser2();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseQuery.getListUser(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GenericTypeIndicator<HashMap<String, User>> objGTI = new GenericTypeIndicator<HashMap<String, User>>() {
                    @Override
                    public boolean equals(@Nullable Object obj) {
                        return super.equals(obj);
                    }
                };
                Map<String, User> objHM = snapshot.getValue(objGTI);
                if (objHM != null){
                    final List<User> objAL = new ArrayList<>(objHM.values());
                    for (User tmp : objAL) {
                        if (tmp.getId().equals(Fid)) user2 = tmp;
                        roomChatInterface.setTitle(user2.getFullName());
                        if (tmp.getId().equals(Uid)) user1 = tmp;
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

//    public void setUser1(){
//        FirebaseQuery.getUser(Uid, new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                GenericTypeIndicator<HashMap<String, User>> objGTI = new GenericTypeIndicator<HashMap<String, User>>() {
//                    @Override
//                    public boolean equals(@Nullable Object obj) {
//                        return super.equals(obj);
//                    }
//                };
//                Map<String, User> objHM = snapshot.getValue(objGTI);
//                final List<User> objAL = new ArrayList<>(objHM.values());
//                for (User tmp : objAL) {
//                    if (tmp.getId().equals(Uid)) user1 = tmp; //get all information of current user
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }

    public void sendMessage(String text){
        FirebaseQuery.sendMessage(roomID, text, Uid, System.currentTimeMillis(), new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

            }
        }, "message" + (messageList.size() + 1));
    }

}
