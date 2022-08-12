package com.rikkei.awesome.model;

import com.google.firebase.database.PropertyName;

import java.io.Serializable;
import java.util.regex.Pattern;

public class User implements Serializable {
    @PropertyName("id")
    private String id;
    @PropertyName("email")
    private String email;
    @PropertyName("password")
    private String password;
    @PropertyName("fullName")
    private String fullName;
    @PropertyName("dob")
    private String dob;
    @PropertyName("phoneNumber")
    private String phoneNumber;
    @PropertyName("avatar")
    private String avatar;

    public User() {
    }

    public User(String id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }

    public User(String id, String fullName, String avatar) {
        this.id = id;
        this.fullName = fullName;
        this.avatar = avatar;
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

    @PropertyName("id")
    public String getId() {
        return id;
    }

    @PropertyName("id")
    public void setId(String id) {
        this.id = id;
    }

    @PropertyName("email")
    public String getEmail() {
        return email;
    }

    @PropertyName("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @PropertyName("password")
    public String getPassword() {
        return password;
    }

    @PropertyName("password")
    public void setPassword(String password) {
        this.password = password;
    }

    @PropertyName("fullName")
    public String getFullName() {
        return fullName;
    }

    @PropertyName("fullName")
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @PropertyName("dob")
    public String getDob() {
        return dob;
    }

    @PropertyName("dob")
    public void setDob(String dob) {
        this.dob = dob;
    }

    @PropertyName("phoneNumber")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @PropertyName("phoneNumber")
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @PropertyName("avatar")
    public String getAvatar() {
        return avatar;
    }

    @PropertyName("avatar")
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

}
