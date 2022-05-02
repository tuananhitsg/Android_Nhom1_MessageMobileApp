package com.example.nhom1_messagemobileapp.entity;

import java.io.Serializable;
import java.util.List;

// tạo tạm =))
public class Friend implements Serializable {
    private int id;
    private User user;
    List<Message> messages;

    public Friend(int id, User user, List<Message> messages) {
        this.id = id;
        this.user = user;
        this.messages = messages;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "Friend{" +
                "id=" + id +
                ", user=" + user +
                "}";
    }
}
