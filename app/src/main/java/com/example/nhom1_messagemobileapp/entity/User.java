package com.example.nhom1_messagemobileapp.entity;

import java.io.Serializable;
import java.util.List;

// tạo tạm =))
public class User implements Serializable {
    private String handleName;
    private String email;
    private String password;
    private String avatar;
    private List<Message> messages;

    public String getHandleName() {
        return handleName;
    }

    public void setHandleName(String handleName) {
        this.handleName = handleName;
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

    public User() {

    }

    public User(String handleName, String email, String passwordr) {
        this.handleName = handleName;
        this.email = email;
        this.password = password;

    }

    @Override
    public String toString() {
        return "User{" +
                "handleName='" + handleName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", avatar='" + avatar + '\'' +
                ", messages=" + messages +
                '}';
    }
}
