package com.example.nhom1_messagemobileapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.nhom1_messagemobileapp.adapter.ChatListAdapter;
import com.example.nhom1_messagemobileapp.adapter.MessageListAdapter;
import com.example.nhom1_messagemobileapp.entity.Friend;
import com.example.nhom1_messagemobileapp.entity.Message;
import com.example.nhom1_messagemobileapp.entity.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MessageListAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        ImageButton btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> {
            finish();
        });
        Bundle bundle = getIntent().getExtras();
        //Friend friend = (Friend) bundle.getSerializable("friend");
        //System.out.println(friend);


//        User user1 = new User(1, "Trần Văn Nhân", "");
//        User user2 = new User(2, "Trần Văn A", "");
//        List<Message> messages1 = new ArrayList<Message>();
//        messages1.add(new Message(1, user2, user1, "Hello", LocalDateTime.of(2022, 05, 01, 11, 30)));
//        messages1.add(new Message(2, user2, user1, "Có ở đó không? Có ở đó không? Có ở đó không? Có ở đó không? Có ở đó không?",
//                                    LocalDateTime.of(2022, 05, 01, 11, 40)));
//        messages1.add(new Message(3, user1, user2, "Có việc gì vậy?", LocalDateTime.now()));
//        messages1.add(new Message(1, user2, user1, "Hello", LocalDateTime.of(2022, 05, 01, 11, 30)));
//        messages1.add(new Message(2, user2, user1, "Có ở đó không? Có ở đó không? Có ở đó không? Có ở đó không? Có ở đó không?",
//                LocalDateTime.of(2022, 05, 01, 11, 40)));
//        messages1.add(new Message(3, user1, user2, "Có việc gì vậy?", LocalDateTime.now()));
//        messages1.add(new Message(1, user2, user1, "Hello", LocalDateTime.of(2022, 05, 01, 11, 30)));
//        messages1.add(new Message(2, user2, user1, "Có ở đó không? Có ở đó không? Có ở đó không? Có ở đó không? Có ở đó không?",
//                LocalDateTime.of(2022, 05, 01, 11, 40)));
//        messages1.add(new Message(3, user1, user2, "Có việc gì vậy?", LocalDateTime.now()));
//        messages1.add(new Message(1, user2, user1, "Hello", LocalDateTime.of(2022, 05, 01, 11, 30)));
//        messages1.add(new Message(2, user2, user1, "Có ở đó không? Có ở đó không? Có ở đó không? Có ở đó không? Có ở đó không?",
//                LocalDateTime.of(2022, 05, 01, 11, 40)));
//        messages1.add(new Message(3, user1, user2, "Có việc gì vậy?", LocalDateTime.now()));
//        messages1.add(new Message(1, user2, user1, "Hello", LocalDateTime.of(2022, 05, 01, 11, 30)));
//        messages1.add(new Message(2, user2, user1, "Có ở đó không? Có ở đó không? Có ở đó không? Có ở đó không? Có ở đó không?",
//                LocalDateTime.of(2022, 05, 01, 11, 40)));
//        messages1.add(new Message(3, user1, user2, "Có việc gì vậy?", LocalDateTime.now()));
//        messages1.add(new Message(1, user2, user1, "Hello", LocalDateTime.of(2022, 05, 01, 11, 30)));
//        messages1.add(new Message(2, user2, user1, "Có ở đó không? Có ở đó không? Có ở đó không? Có ở đó không? Có ở đó không?",
//                LocalDateTime.of(2022, 05, 01, 11, 40)));
//        messages1.add(new Message(3, user1, user2, "Có việc gì vậy?", LocalDateTime.now()));
//
//        recyclerView = findViewById(R.id.recyclerView);
//        recyclerAdapter = new MessageListAdapter(this, messages1, user1);
//        recyclerView.setAdapter(recyclerAdapter);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
////        linearLayoutManager.setReverseLayout(true);
////        linearLayoutManager.setStackFromEnd(true);
//        recyclerView.setLayoutManager(linearLayoutManager);
    }
}