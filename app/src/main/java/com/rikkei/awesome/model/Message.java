package com.rikkei.awesome.model;

import java.io.Serializable;

public class Message implements Serializable {
    String content, time, sentby, id;

    public Message() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Message(String content, String time, String sentby) {
        this.content = content;
        this.time = time;
        this.sentby = sentby;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSentby() {
        return sentby;
    }

    public void setSentby(String sentby) {
        this.sentby = sentby;
    }
}
