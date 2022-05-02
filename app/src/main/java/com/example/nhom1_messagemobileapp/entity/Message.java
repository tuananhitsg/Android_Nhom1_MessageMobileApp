package com.example.nhom1_messagemobileapp.entity;

import java.time.LocalDateTime;

public class Message {
    private int id;
    private User from;
    private User to;
    private String content;
    private LocalDateTime time;

    public Message(int id, User from, User to, String content, LocalDateTime time) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.content = content;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public User getTo() {
        return to;
    }

    public void setTo(User to) {
        this.to = to;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
