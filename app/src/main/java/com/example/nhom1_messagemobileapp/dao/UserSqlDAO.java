package com.example.nhom1_messagemobileapp.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.nhom1_messagemobileapp.entity.User;

import java.util.List;

@Dao
public abstract class UserSqlDAO {
    @Insert
    public abstract long insert(User user);

    @Update
    public abstract int update(User user);

    @Delete
    abstract int delete(User user);

    @Query("SELECT * FROM users")
    public abstract List<User> findAll();

    @Query("SELECT * FROM users WHERE uid = :uid")
    public abstract List<User> findById(String uid);
}
