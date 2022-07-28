package com.rikkei.awesome.model;

import java.io.Serializable;
import java.util.regex.Pattern;

public class User implements Serializable {
    private String id, email, password, fullName, dob, phoneNumber, avatar;

    public User() {
    }

    public User(String fullName) {
        this.fullName = fullName;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String id, String email, String password, String fullName, String dob, String phoneNumber, String avatar) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.dob = dob;
        this.phoneNumber = phoneNumber;
        this.avatar = avatar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

}
