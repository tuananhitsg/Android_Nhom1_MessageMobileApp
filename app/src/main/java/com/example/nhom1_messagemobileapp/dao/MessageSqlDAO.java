package com.example.nhom1_messagemobileapp.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.nhom1_messagemobileapp.entity.Message;
import com.example.nhom1_messagemobileapp.entity.User;

import java.util.ArrayList;
import java.util.List;

@Dao
public abstract class MessageSqlDAO {
    @Insert
    public abstract long insert(Message message);

    @Update
    public abstract int update(Message message);

    @Delete
    public abstract int delete(Message message);

    @Query("SELECT * FROM messages")
    public abstract List<Message> findAll();

    @Query("SELECT * FROM messages WHERE id = :id")
    public abstract Message findById(String id);

    @Query("SELECT * FROM messages WHERE fromUid = :uid or toUid = :uid order by time")
    public abstract List<Message> findAllByUser(String uid);

    @Query("SELECT * FROM messages WHERE (fromUid = :uid1 and toUid = :uid2) or (fromUid = :uid2 and toUid = :uid1) order by time")
    public abstract List<Message> findAllByUsers(String uid1, String uid2);

    @Query("SELECT * FROM messages WHERE fromUid = :uid or toUid = :uid order by time desc")
    public abstract List<Message> findAllByUserOrderByTimeDesc(String uid);

    public boolean checkExits(String id){
        return findById(id) != null;
    }



}

