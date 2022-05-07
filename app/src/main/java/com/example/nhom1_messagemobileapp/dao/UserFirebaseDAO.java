package com.example.nhom1_messagemobileapp.dao;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.nhom1_messagemobileapp.entity.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import okhttp3.Response;

public class UserFirebaseDAO {
    private final DatabaseReference mDatabase;

    public UserFirebaseDAO(){
        mDatabase = FirebaseDatabase.getInstance().getReference().child("user");
    }
    
//    public User getUserByUid(String uid){
//        AtomicReference<User> user = new AtomicReference<>();
//        CountDownLatch latch = new CountDownLatch(1);
//        mDatabase.child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                if (!task.isSuccessful()) {
//                    Log.e("firebase", "Error getting data", task.getException());
//                }
//                else {
//                    user.set(task.getResult().getValue(User.class));
//                    System.out.println(user.get());
//                }
//                latch.countDown();
//            }
//        });
//        try {
//            latch.await();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        return user.get();
//    }

}
