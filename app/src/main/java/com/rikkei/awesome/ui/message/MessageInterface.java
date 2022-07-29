package com.rikkei.awesome.ui.message;

import com.rikkei.awesome.utils.OnUserClickedListener;
import com.rikkei.awesome.model.RoomChat;

import java.util.ArrayList;

public interface MessageInterface {
    void showListRoomChat(ArrayList<RoomChat> roomChats);
    void showListRoomChatFailed();
}
