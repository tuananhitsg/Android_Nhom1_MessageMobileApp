package com.example.nhom1_messagemobileapp.entity;


import static androidx.room.ForeignKey.CASCADE;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.nhom1_messagemobileapp.utils.converter.TimestampConverter;
import com.google.firebase.database.DataSnapshot;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Entity(tableName = "messages")
public class Message implements Serializable {
    @PrimaryKey
    @NonNull
    private String id;

    @ForeignKey(entity = User.class, parentColumns = "uid", childColumns = "fromUid", onDelete = CASCADE)
    private String fromUid;

    @ForeignKey(entity = User.class, parentColumns = "uid", childColumns = "toUid", onDelete = CASCADE)
    private String toUid;
    private String content;

    @TypeConverters({TimestampConverter.class})
    private Date time;

    private String type;

    public Message() {
    }

    public Message(DataSnapshot snapshot) {
        this.id = snapshot.getKey();
        this.content = snapshot.child("content").getValue(String.class);
        this.fromUid = snapshot.child("fromUid").getValue(String.class);
        this.toUid = snapshot.child("toUid").getValue(String.class);
//        this.time = TimestampConverter.fromTimestamp(
//                Long.parseLong(snapshot.child("time").getValue(String.class)));
    }

    public Message(String id, String from, String to, String content, Date time, String type) {
        this.id = id;
        this.fromUid = from;
        this.toUid = to;
        this.content = content;
        this.time = time;
        this.type = type;
    }

    public Message(String from, String to, String content, Date time, String type) {
        this.fromUid = from;
        this.toUid = to;
        this.content = content;
        this.time = time;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFromUid() {
        return fromUid;
    }

    public void setFromUid(String fromUid) {
        this.fromUid = fromUid;
    }

    public String getToUid() {
        return toUid;
    }

    public void setToUid(String toUid) {
        this.toUid = toUid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", from=" + fromUid +
                ", to=" + toUid +
                ", content='" + content + '\'' +
                ", time=" + time +
                ", type=" + type +
                '}';
    }
}
