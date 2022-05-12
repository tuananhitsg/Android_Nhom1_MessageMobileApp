package com.example.nhom1_messagemobileapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nhom1_messagemobileapp.adapter.MessageListAdapter;
import com.example.nhom1_messagemobileapp.dao.UserSqlDAO;
import com.example.nhom1_messagemobileapp.entity.Message;
import com.example.nhom1_messagemobileapp.entity.User;
import com.example.nhom1_messagemobileapp.utils.converter.TimestampConverter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

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
    private User friend;

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
        friend = (User) bundle.getSerializable("friend");
        System.out.println(friend);

        ImageView imgAvt = findViewById(R.id.img_avatar);
        Picasso.get().load(friend.getAvatar()).into(imgAvt);

        TextView txtNameFriend = findViewById(R.id.txt_name_friend);
        txtNameFriend.setText(friend.getName());


        recyclerView = findViewById(R.id.recyclerView);
        recyclerAdapter = new MessageListAdapter(this, new ArrayList<>(), myUser);
        recyclerView.setAdapter(recyclerAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        ShowListMessageTask showListMessageTask = new ShowListMessageTask();
        showListMessageTask.execute();

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
                myUser.setUid(uid);
            }
        });
    }

    public class ShowListMessageTask extends AsyncTask<String, String, List<Message>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<Message> doInBackground (String...params){
            List<Message> messages = new ArrayList<>();
            ChatActivity.this.refMessage.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                        Message message = new Message();
                        String content = snapshot.child("content").getValue(String.class);
                        String uidFrom = snapshot.child("from").getValue(String.class);
                        String uidTo = snapshot.child("to").getValue(String.class);
                        Long timestamp = snapshot.child("time").getValue(Long.class);
//                    Log.d("date", timestamp.toString());
                        Date time = TimestampConverter.fromTimestamp(timestamp);
                        message.setContent(content);
                        message.setTime(time);
                        if(uidFrom.equals(myUser.getUid())){

                        }else if(uidTo .equals(myUser.getUid())){

                        }

                    }
//                    HomeFragment.ShowListUserTask showListUserTask = new HomeFragment.ShowListUserTask(userLastMessages);
//                    showListUserTask.execute();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });
            return messages;
        }

        @Override
        protected void onPostExecute (List<Message> messages){
            Log.d("firebase messages", messages.toString());
        }
    }
}