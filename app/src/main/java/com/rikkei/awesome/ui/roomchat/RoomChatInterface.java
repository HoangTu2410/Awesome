package com.rikkei.awesome.ui.roomchat;

import com.google.firebase.storage.StorageReference;

public interface RoomChatInterface {
    void setTitle(String friendName);
    void setAvatar(StorageReference url);
}
