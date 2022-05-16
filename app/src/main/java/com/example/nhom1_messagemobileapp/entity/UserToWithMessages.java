package com.example.nhom1_messagemobileapp.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.io.Serializable;
import java.util.List;

public class UserToWithMessages implements Serializable {
    @Embedded
    public User user;
    @Relation(
            parentColumn = "uid",
            entityColumn = "to"
    )
    public List<Message> messages;
}