package com.example.nhom1_messagemobileapp.entity;


import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.nhom1_messagemobileapp.utils.converter.TimestampConverter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Entity(tableName = "messages")
public class Message implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ForeignKey(entity = User.class, parentColumns = "uid", childColumns = "from", onDelete = CASCADE)
    private String from;

    @ForeignKey(entity = User.class, parentColumns = "uid", childColumns = "to", onDelete = CASCADE)
    private String to;
    private String content;

    @TypeConverters({TimestampConverter.class})
    private Date time;

    public Message() {
    }

    public Message(int id, String from, String to, String content, Date time) {
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

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
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

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", from=" + from +
                ", to=" + to +
                ", content='" + content + '\'' +
                ", time=" + time +
                '}';
    }
}
