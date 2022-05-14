package com.example.nhom1_messagemobileapp.database;


import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.nhom1_messagemobileapp.MainActivity;
import com.example.nhom1_messagemobileapp.dao.MessageSqlDAO;
import com.example.nhom1_messagemobileapp.dao.UserSqlDAO;
import com.example.nhom1_messagemobileapp.entity.Message;
import com.example.nhom1_messagemobileapp.entity.User;
import com.example.nhom1_messagemobileapp.utils.converter.TimestampConverter;

@androidx.room.Database(
        entities = {
                User.class, Message.class
        },
        version = 6
)
@TypeConverters({TimestampConverter.class})
public abstract class Database extends RoomDatabase {
    private static Database instance;
    public abstract UserSqlDAO getUserSqlDAO();
    public abstract MessageSqlDAO getMessageSqlDAO();

    public static Database getInstance(Context context){
        if(instance == null)
            instance = Room.databaseBuilder(context, Database.class, "mydb")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        return instance;
    }
}
