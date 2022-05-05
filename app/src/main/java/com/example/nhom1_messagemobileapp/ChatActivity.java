package com.example.nhom1_messagemobileapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.nhom1_messagemobileapp.adapter.ChatListAdapter;
import com.example.nhom1_messagemobileapp.adapter.MessageListAdapter;
import com.example.nhom1_messagemobileapp.dao.UserFirebaseDAO;
import com.example.nhom1_messagemobileapp.entity.Message;
import com.example.nhom1_messagemobileapp.entity.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    private String uid = "6kBO5yFQu1Y355mxKuc1YSaLtSZ2";
    private RecyclerView recyclerView;
    private MessageListAdapter recyclerAdapter;
    private ImageButton btnCamera;
    private ImageButton btnMedia;
    private ImageButton btnAction;
    private String uidFriend;
    private DatabaseReference refMessage;
    private DatabaseReference refUser;
    private User myUser;
    private User friendUser;

    public ChatActivity(String uid){
        this.uid = uid;
    }

    public ChatActivity(){
    }

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

        EditText edtMessage = findViewById(R.id.input);
        edtMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){
                    Resources resource = getResources();
                    final int resourceId = resource.getIdentifier("ic_love", "drawable", getPackageName());
                    btnAction.setImageDrawable(resource.getDrawable(resourceId));
                }else{
                    Resources resource = getResources();
                    final int resourceId = resource.getIdentifier("ic_send", "drawable", getPackageName());
                    btnAction.setImageDrawable(resource.getDrawable(resourceId));
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });

        btnCamera = findViewById(R.id.btn_camera);
        btnCamera.setOnClickListener(v -> {
            Toast.makeText(this, "Đang phát triển", Toast.LENGTH_SHORT).show();
        });

        btnMedia = findViewById(R.id.btn_media);
        btnMedia.setOnClickListener(v -> {
            Toast.makeText(this, "Đang phát triển", Toast.LENGTH_SHORT).show();
        });

        btnAction = findViewById(R.id.btn_action);
        btnAction.setOnClickListener(v -> {

        });
        System.out.println("hello111111111111");
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        refMessage = database.getReference("message");
        refUser = database.getReference("user");

    }

    public void getMyUser(){
        refUser.child(uid).get().addOnCompleteListener(task ->{
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            }
            else {
                myUser = task.getResult().getValue(User.class);
            }
        });
    }
}