package com.example.nhom1_messagemobileapp;

import androidx.annotation.NonNull;
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
import com.example.nhom1_messagemobileapp.dao.MessageSqlDAO;
import com.example.nhom1_messagemobileapp.dao.UserSqlDAO;
import com.example.nhom1_messagemobileapp.database.Database;
import com.example.nhom1_messagemobileapp.entity.Message;
import com.example.nhom1_messagemobileapp.entity.User;
import com.example.nhom1_messagemobileapp.utils.CustomeDateTime;
import com.example.nhom1_messagemobileapp.utils.converter.TimestampConverter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
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
    private Database sqlDatabase;
    private MessageSqlDAO messageSqlDAO;
    private boolean isBtnSend = false;

    public ChatActivity(String uid){
        this.uid = uid;
    }

    public ChatActivity(){
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        sqlDatabase = Database.getInstance(null);
        messageSqlDAO = sqlDatabase.getMessageSqlDAO();

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
        recyclerAdapter = new MessageListAdapter(this, friend);
        recyclerView.setAdapter(recyclerAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);



        EditText edtMessage = findViewById(R.id.input);
        edtMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){
                    Resources resource = getResources();
                    final int resourceId = resource.getIdentifier("ic_love", "drawable", getPackageName());
                    btnAction.setImageDrawable(resource.getDrawable(resourceId));
                    isBtnSend = false;
                }else{
                    Resources resource = getResources();
                    final int resourceId = resource.getIdentifier("ic_send", "drawable", getPackageName());
                    btnAction.setImageDrawable(resource.getDrawable(resourceId));
                    isBtnSend = true;
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
            if(isBtnSend){
                String msg = edtMessage.getText().toString();
                Long timestamp = System.currentTimeMillis();
                String key = timestamp.toString()+"_"+ (int)Math.random()*1000000;

                Message message = new Message(key, uid, friend.getUid(), msg, new Date());
                Log.e("new msg", message.toString());
                refMessage.child(uid).child(key).setValue(message).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        edtMessage.setText("");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
        });

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        refMessage = database.getReference("message");
        refUser = database.getReference("user");

        refMessage.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ShowListMessageTask showListMessageTask = new ShowListMessageTask();
                showListMessageTask.execute();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }


    public class ShowListMessageTask extends AsyncTask<String, String, List<Message>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<Message> doInBackground (String...params){
            Log.e("chat", uid+" "+friend.getUid());
            List<Message> messages = messageSqlDAO.findAllByUsers(uid, friend.getUid());
            return messages;
        }

        @Override
        protected void onPostExecute (List<Message> messages){
            Log.d("->>> sqll messages", messages.toString());
            recyclerAdapter.setMessages(messages);
        }
    }
}