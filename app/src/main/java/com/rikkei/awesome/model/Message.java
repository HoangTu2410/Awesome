package com.rikkei.awesome.model;

import java.io.Serializable;

public class Message implements Serializable {

    private String user1;
    private String message;
    private String date;

    public Message(String user1, String message, String date) {
        this.user1 = user1;
        this.message = message;
        this.date = date;
    }

    public String getUser1() {
        return user1;
    }

    public void setUser1(String user1) {
        this.user1 = user1;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
