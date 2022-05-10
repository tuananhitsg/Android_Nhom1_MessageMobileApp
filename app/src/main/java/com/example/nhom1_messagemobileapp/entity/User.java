package com.example.nhom1_messagemobileapp.entity;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// tạo tạm =))
@IgnoreExtraProperties
public class User implements Serializable {
    private String uid;
    private String name;
    private String email;
    private String avatar;
    private List<Message> messages;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public void addMessage(Message msg){
        this.messages.add(msg);
    }

    public User() {
        this.messages = new ArrayList<>();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public User(String name, String email, String avatar) {
        this.name = name;
        this.email = email;
        this.avatar = avatar;
        this.messages = new ArrayList<>();
    }

    public User(String name, String email, String avatar, List<Message> messages) {
        this.name = name;
        this.email = email;
        this.avatar = avatar;
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
