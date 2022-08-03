package com.rikkei.awesome.utils;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rikkei.awesome.model.User;

public class FirebaseQuery<T> {

    public static final String USERS = "users";
    public static final String ROOMCHATS = "room_chat";
    public static final String MESSAGES = "message_text";
    public static String USERNAME = "";

    public static void getListRoomChatFirst(String username, ValueEventListener valueEventListener){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(ROOMCHATS);
        myRef.orderByKey().startAt(username).addValueEventListener(valueEventListener);//get all roomchat
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
        myRef.orderByKey().startAt(Uid).addValueEventListener(valueEventListener);
    }

    public static void getListUser(ValueEventListener valueEventListener){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(USERS);
        myRef.orderByKey().addValueEventListener(valueEventListener);
    }
}
