package com.example.nhom1_messagemobileapp.database;


import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.nhom1_messagemobileapp.dao.MessageSqlDAO;
import com.example.nhom1_messagemobileapp.dao.UserSqlDAO;
import com.example.nhom1_messagemobileapp.entity.Message;
import com.example.nhom1_messagemobileapp.entity.User;
import com.example.nhom1_messagemobileapp.utils.converter.TimestampConverter;

@androidx.room.Database(
        entities = {
                User.class, Message.class
        },
        version = 2
)
@TypeConverters({TimestampConverter.class})
public abstract class Database extends RoomDatabase {
    public abstract UserSqlDAO getUserSqlDAO();
    public abstract MessageSqlDAO getMessageSqlDAO();
}
