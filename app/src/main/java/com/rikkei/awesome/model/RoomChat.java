package com.rikkei.awesome.model;

import java.io.Serializable;

public class RoomChat implements Serializable {

    private String user1;
    private String lastMessage;
    private String time, description;

    public RoomChat(String user1, String lastMessage, String time, String description) {
        this.user1 = user1;
        this.lastMessage = lastMessage;
        this.time = time;
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RoomChat(String user1, String message, String time) {
        this.user1 = user1;
        this.lastMessage = message;
        this.time = time;
    }

    public String getUser1() {
        return user1;
    }

    public void setUser1(String user1) {
        this.user1 = user1;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public void setDate(String time) {
        this.time = time;
    }
}
