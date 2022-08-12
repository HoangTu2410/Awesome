package com.rikkei.awesome.model;

import java.io.Serializable;

public class RelationShip implements Serializable {
    public static final int REQUEST_FRIEND = 0;
    public static final int FRIEND = 1;

    private long id;
    private User user1, user2;
    private int status;

    public RelationShip() {
    }

    public RelationShip(long id, User user1, User user2, int status) {
        this.id = id;
        this.user1 = user1;
        this.user2 = user2;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
