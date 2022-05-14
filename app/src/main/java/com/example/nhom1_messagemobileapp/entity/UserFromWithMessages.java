package com.example.nhom1_messagemobileapp.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.io.Serializable;
import java.util.List;

public class UserFromWithMessages implements Serializable {
    @Embedded
    public User user;
    @Relation(
            parentColumn = "uid",
            entityColumn = "from"
    )
    public List<Message> messages;
}