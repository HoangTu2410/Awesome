package com.rikkei.awesome.ui.message;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rikkei.awesome.MainActivity;

public class MessagePresenter {

    private MessageInterface messageInterface;
    private MainActivity mainActivity;

    public MessagePresenter(MessageInterface messageInterface, MainActivity mainActivity) {
        this.messageInterface = messageInterface;
        this.mainActivity = mainActivity;
    }

    public static void getListRoom(String UId) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("room_chat");
        myRef.orderByKey().startAt(UId).endAt(UId.concat("\uf8ff")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
