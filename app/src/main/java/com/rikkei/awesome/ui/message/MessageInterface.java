package com.rikkei.awesome.ui.message;

import com.google.firebase.database.ValueEventListener;
import com.rikkei.awesome.OnUserClickedListener;
import com.rikkei.awesome.model.RoomChat;

import java.util.ArrayList;

public interface MessageInterface extends OnUserClickedListener {
    void showListRoomChat(ArrayList<RoomChat> roomChats);
}
