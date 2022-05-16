package com.example.nhom1_messagemobileapp.dao;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.nhom1_messagemobileapp.MainActivity;
import com.example.nhom1_messagemobileapp.database.Database;
import com.example.nhom1_messagemobileapp.entity.Message;
import com.example.nhom1_messagemobileapp.entity.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

@Dao
public abstract class UserSqlDAO {

    private Database database = Database.getInstance(null);

    @Insert
    public abstract long insert(User user);

    @Update
    public abstract int update(User user);

    @Delete
    public abstract int delete(User user);

    public void insertOrUpdate(User user){
        try{
            insert(user);
        }catch (Exception e){
            update(user);
        }
    }

    @Query("SELECT * FROM users")
    public abstract List<User> findAll();

    @Query("SELECT * FROM users WHERE uid = :uid")
    public abstract User findById(String uid);

    public boolean checkExits(String id){
        return findById(id) != null;
    }

    public List<User> getFriends(String uid, boolean sql){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference refUser = firebaseDatabase.getReference("user");

        List<User> friends = new ArrayList<>();
        List<Message> messages = database.getMessageSqlDAO().findAllByUserOrderByTimeDesc(uid);
        messages.forEach(message -> {
            if(message.getType().equals("image")){
                message.setContent("Đã gửi một ảnh");
            }

            String friendUid = message.getFromUid();
            if(uid.equals(message.getFromUid()))
                friendUid = message.getToUid();

            if(MainActivity.isNetworkConnected() && sql == false) {
                refUser.child(friendUid).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User friend = new User(snapshot);
                        if (friend != null) {
                            if (!friends.contains(friend)) {
                                friend.addMessage(message);
                                friends.add(friend);
                            }
                            insertOrUpdate(friend);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }else{
                User friend = findById(friendUid);
                if (friend != null) {
                    if (!friends.contains(friend)) {
                        friend.addMessage(message);
                        friends.add(friend);
                    }
                }
            }

        });
        System.out.println(friends);
        return friends;
    }
}
