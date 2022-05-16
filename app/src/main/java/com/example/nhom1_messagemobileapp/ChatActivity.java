package com.example.nhom1_messagemobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Process;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
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
import com.example.nhom1_messagemobileapp.entity.StickerPackage;
import com.example.nhom1_messagemobileapp.entity.User;
import com.example.nhom1_messagemobileapp.utils.CustomeDateTime;
import com.example.nhom1_messagemobileapp.utils.FlaticonAPI;
import com.example.nhom1_messagemobileapp.utils.Random;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

public class ChatActivity extends AppCompatActivity {

    private String uid = "";
    private RecyclerView recyclerView;
    private MessageListAdapter recyclerAdapter;
    private ImageButton btnCamera;
    private ImageButton btnMedia;
    private ImageButton btnAction;
    private DatabaseReference refMessage;
    private DatabaseReference refUser;
    private User myUser;
    private User friend;
    private Database sqlDatabase;
    private MessageSqlDAO messageSqlDAO;
    private boolean isBtnSend = false;
    private int SELECT_PICTURE = 200;
    private Uri filePath;
    private FirebaseStorage rootRef;
    private StorageReference storageRef;
    private EditText edtMessage;

    public ChatActivity(String uid) {
        this.uid = uid;
    }

    public ChatActivity() {
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        sqlDatabase = Database.getInstance(null);
        messageSqlDAO = sqlDatabase.getMessageSqlDAO();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        refMessage = database.getReference("message");
        refUser = database.getReference("user");

        rootRef = FirebaseStorage.getInstance();
        storageRef = rootRef.getReference();

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


        edtMessage = findViewById(R.id.input);
        edtMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")) {
                    Resources resource = getResources();
                    final int resourceId = resource.getIdentifier("ic_love", "drawable", getPackageName());
                    btnAction.setImageDrawable(resource.getDrawable(resourceId));
                    isBtnSend = false;
                } else {
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

        edtMessage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (edtMessage.getRight() - edtMessage.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        StickerBottomSheetFragment bottomSheetFragment = new StickerBottomSheetFragment(ChatActivity.this);
                        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());

                    }
                }
                return false;
            }
        });

        btnCamera = findViewById(R.id.btn_camera);
        btnCamera.setOnClickListener(v -> {
            Toast.makeText(this, "Đang phát triển", Toast.LENGTH_SHORT).show();
        });

        btnMedia = findViewById(R.id.btn_media);
        btnMedia.setOnClickListener(v -> {
            if(!MainActivity.isNetworkConnected()) {
                Toast.makeText(this, "Không có kết nối mạng!", Toast.LENGTH_SHORT).show();
                return;
            }
            imageChooser();
        });

        btnAction = findViewById(R.id.btn_action);
        btnAction.setOnClickListener(v -> {
            if(!MainActivity.isNetworkConnected()) {
                Toast.makeText(this, "Không có kết nối mạng!", Toast.LENGTH_SHORT).show();
                return;
            }
            String msg = "";
            String type = "";
            if (isBtnSend) {
                msg = edtMessage.getText().toString();
                type = "text";
            } else {
                msg = "https://i.imgur.com/6YgyNCv.png";
                type = "image";
            }

            Message message = new Message(uid, friend.getUid(), msg, new Date(), type);
            addNewMessage(message);
        });

        ShowListMessageTask showListMessageTask = new ShowListMessageTask();
        showListMessageTask.execute();

        refMessage.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ShowListMessageTask showListMessageTask = new ShowListMessageTask();
                showListMessageTask.execute();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }


    public class ShowListMessageTask extends AsyncTask<String, String, List<Message>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<Message> doInBackground(String... params) {
            List<Message> messages = messageSqlDAO.findAllByUsers(uid, friend.getUid());
            return messages;
        }

        @Override
        protected void onPostExecute(List<Message> messages) {
            Log.e("reload message", messages.toString());
            recyclerAdapter.setMessages(messages);
            scrool();
        }
    }



    public void scrool(){
        if(recyclerAdapter.getMessages().size() != 0)
            recyclerView.smoothScrollToPosition(recyclerAdapter.getMessages().size() - 1);
    }

    public void addNewMessage(Message message) {
        Long timestamp = System.currentTimeMillis();
        String key = timestamp.toString() + "_" + Random.generateTicketNumber(0, 10000);
        message.setId(key);

        if(message.getFromUid() == null)message.setFromUid(uid);
        if(message.getToUid() == null)message.setToUid(friend.getUid());
        Log.e("new msg", message.toString());
        // me
        refMessage.child(uid).child(message.getId()).setValue(message).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                refMessage.child(friend.getUid()).child(message.getId()).setValue(message).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        edtMessage.setText("");
                        scrool();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ChatActivity.this, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ChatActivity.this, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void imageChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                filePath = selectedImageUri;
                if (null != selectedImageUri) {
                    Log.e("image", selectedImageUri.toString());
                    uploadImage();
//                    imgLogo.setImageURI(selectedImageUri);
                }
            }
        }
    }

    private void uploadImage() {
        if (filePath != null) {
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageRef.child("chat_images/" + UUID.randomUUID().toString());

            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            ref.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    String image = task.getResult().toString();
//                                    theUser.setAvatar(avatar);
//                                    myRef.child(uid).child("avatar").setValue(avatar);
                                    Log.e("image", image);
                                    Message message = new Message(uid, friend.getUid(), image, new Date(), "image");
                                    addNewMessage(message);
                                    Toast.makeText(ChatActivity.this, "Gửi ảnh thành công ", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(ChatActivity.this, "Gửi ảnh thất bại ", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage("Uploaded " + (int) progress + "%");
                                }
                            });
        }
    }
}