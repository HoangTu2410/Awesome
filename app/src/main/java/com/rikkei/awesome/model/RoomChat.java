package com.rikkei.awesome.model;

import java.io.Serializable;

public class RoomChat implements Serializable {

    private String sendBy;
    private String lastMessage, description;
    private int time;

    public RoomChat(String sendBy, String lastMessage, int time, String description) {
        this.sendBy = sendBy;
        this.lastMessage = lastMessage;
        this.time = time;
        this.description = description;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RoomChat(String sendBy, String message, int time) {
        this.sendBy = sendBy;
        this.lastMessage = message;
        this.time = time;
    }

    public String getUser1() {
        return sendBy;
    }

    public void setUser1(String sendBy) {
        this.sendBy = sendBy;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

}
