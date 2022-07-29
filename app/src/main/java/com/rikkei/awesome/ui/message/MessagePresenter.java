package com.rikkei.awesome.ui.message;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.rikkei.awesome.MainActivity;
import com.rikkei.awesome.model.RoomChat;
import com.rikkei.awesome.utils.FirebaseQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessagePresenter {

    private MessageInterface messageInterface;

    public MessagePresenter(MessageInterface messageInterface) {
        this.messageInterface = messageInterface;
    }

    public void getListRoom(String UId) {
        FirebaseQuery.getListRoomChat(UId, new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GenericTypeIndicator<HashMap<String, RoomChat>> objectsGTypeInd = new GenericTypeIndicator<HashMap<String, RoomChat>>() {
                };
                Map<String, RoomChat> objectHashMap = snapshot.getValue(objectsGTypeInd);
                if (objectHashMap != null){
                    final ArrayList<RoomChat> roomChatArrayList = new ArrayList<>(objectHashMap.values());
                    messageInterface.showListRoomChat(roomChatArrayList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                messageInterface.showListRoomChatFailed();
            }
        });

    }
}
