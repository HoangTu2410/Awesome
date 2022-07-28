package com.rikkei.awesome.ui.message;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MessagePresenter {



    public static void getListRoom(String UId, ValueEventListener valueEventListener) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("room_chat");
        myRef.orderByKey().startAt(UId).endAt(UId.concat("\uf8ff")).addValueEventListener(valueEventListener);
    }
}
