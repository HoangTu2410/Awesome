package com.rikkei.awesome.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class RoomChat implements Serializable {

    private String sendBy, id;
    private String lastMessage, description;
    private String time;

    public RoomChat(){}

    public RoomChat(String id, String sendBy, String lastMessage, String time, String description) {
        this.sendBy = sendBy;
        this.lastMessage = lastMessage;
        this.time = time;
        this.id = id;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public RoomChat(String sendBy, String message, String time) {
        this.sendBy = sendBy;
        this.lastMessage = message;
        this.time = time;
    }

    public String getSendBy() {
        return sendBy;
    }

    public void setSendBy(String sendBy) {
        this.sendBy = sendBy;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

}
