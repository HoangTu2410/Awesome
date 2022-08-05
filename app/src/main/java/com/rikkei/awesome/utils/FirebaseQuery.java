package com.rikkei.awesome.utils;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rikkei.awesome.model.Message;
import com.rikkei.awesome.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FirebaseQuery<T> {

    public static final String USERS = "users";
    public static final String MEMBERS = "member";
    public static final String ROOMCHATS = "room_chat";
    public static final String MESSAGES = "message_text";
    public static String USERNAME = "";

    public static void getListRoomChatFirst(String username, ValueEventListener valueEventListener){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(ROOMCHATS);
        myRef.orderByChild("id").addValueEventListener(valueEventListener);//get all roomchat
    }
    public static void getListRoomChatLast(String username, ValueEventListener valueEventListener){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(ROOMCHATS);
        myRef.orderByKey().addValueEventListener(valueEventListener);//get all roomchat
    }

    public static void getListRoomChat(String username, ValueEventListener valueEventListener){
        getListRoomChatFirst(username, valueEventListener);
        getListRoomChatLast(username, valueEventListener);
    }

    public static void getListMessage(String path, ChildEventListener childEventListener){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(MESSAGES).child(path);
        myRef.addChildEventListener(childEventListener);
    }

    public static void getUser(String Uid, ValueEventListener valueEventListener){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(USERS);
        myRef.orderByChild("id").equalTo(Uid).addListenerForSingleValueEvent(valueEventListener);
    }

    public static void getListUser(ValueEventListener valueEventListener){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(USERS);
        myRef.orderByChild("id").addValueEventListener(valueEventListener);
    }

    public static void getListMember(String Uid, ValueEventListener valueEventListener){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(MEMBERS);
        myRef.addValueEventListener(valueEventListener);
    }

    public static void sendMessage(String roomId, String text, String userid, long currentTimeMillis, DatabaseReference.CompletionListener completionListener){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRefGroup = database.getReference(MESSAGES).child(roomId);

        Map<String, Object> writeMes = new HashMap<>();
        writeMes.put("description", "");
        writeMes.put("id", roomId);
        writeMes.put("lastMessage", text);
        writeMes.put("sendBy", userid);
        writeMes.put("time", Long.toString(currentTimeMillis));

        myRefGroup.updateChildren(writeMes);

        DatabaseReference myRefMes = database.getReference(MESSAGES).child(roomId);
        Message message = new Message(text, Long.toString(currentTimeMillis), userid);
        myRefMes.push().setValue(message, completionListener);
    }
}
