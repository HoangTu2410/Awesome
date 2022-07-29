package com.rikkei.awesome.utils;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseQuery<T> {

    public static final String USERS = "users";
    public static final String ROOMCHATS = "room_chat";
    public static final String MESSAGES = "message_text";
    public static String username = "";

    public static void getListRoomChat(String userid, ValueEventListener valueEventListener){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(ROOMCHATS);
        myRef.orderByKey().startAt(username).endAt(username.concat("\uf8ff")).addValueEventListener(valueEventListener);
    }

    public static void getListMessage(String path, ChildEventListener childEventListener){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(MESSAGES).child(path);
        myRef.addChildEventListener(childEventListener);
    }
}
