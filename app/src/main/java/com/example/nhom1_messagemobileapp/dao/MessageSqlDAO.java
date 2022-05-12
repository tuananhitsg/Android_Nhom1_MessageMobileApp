package com.example.nhom1_messagemobileapp.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.nhom1_messagemobileapp.entity.Message;
import com.example.nhom1_messagemobileapp.entity.User;

import java.util.List;

@Dao
public abstract class MessageSqlDAO {
    @Insert
    public abstract long insert(Message message);

    @Update
    public abstract int update(Message message);

    @Delete
    abstract int delete(Message message);

    @Query("SELECT * FROM messages")
    public abstract List<Message> findAll();

    @Query("SELECT * FROM messages WHERE id = :id")
    public abstract List<Message> findById(int id);

}

