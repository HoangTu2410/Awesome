package com.rikkei.awesome.ui.message;

import androidx.recyclerview.widget.RecyclerView;

import com.rikkei.awesome.utils.OnUserClickedListener;
import com.rikkei.awesome.model.RoomChat;

import java.util.ArrayList;

public interface MessageInterface {
    void showListRoomChat(RecyclerView recyclerView);
    void showListRoomChatFailed();
    void openRoomChat(String roomID);

}
