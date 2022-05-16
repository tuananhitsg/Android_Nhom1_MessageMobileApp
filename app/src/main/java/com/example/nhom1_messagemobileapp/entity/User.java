package com.example.nhom1_messagemobileapp.entity;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@IgnoreExtraProperties
@Entity(tableName = "users")
public class User implements Serializable {
    @PrimaryKey
    @NonNull
    private String uid;
    private String name;
    private String email;
    private String avatar;
    @Ignore
    private List<Message> messages = new ArrayList<>();


    public User(String uid){
        this.uid = uid;
    }

    public User() {
        this.messages = new ArrayList<>();
    }
    public User(DataSnapshot snapshot){
        this.uid = snapshot.getKey();
        this.name = snapshot.child("name").getValue(String.class);
        this.email = snapshot.child("email").getValue(String.class);
        this.avatar = snapshot.child("avatar").getValue(String.class);
    }

    public User(String name, String email, String avatar) {
        this.name = name;
        this.email = email;
        this.avatar = avatar;
    }

    public User(String name, String email, String avatar, List<Message> messages) {
        this.name = name;
        this.email = email;
        this.avatar = avatar;
        this.messages = messages;
    }

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



    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return uid.equals(user.uid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid);
    }

    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
