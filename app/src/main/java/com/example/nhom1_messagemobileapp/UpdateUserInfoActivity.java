package com.example.nhom1_messagemobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.nhom1_messagemobileapp.entity.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import android.net.Uri;

import java.util.UUID;

public class UpdateUserInfoActivity extends AppCompatActivity {
    private ImageView imgAvatar;
    private ImageButton btnBack;
    private Button btnSave, btnCancel;
    private EditText edtEmail, edtName, edtPassword;
    private User theUser;
    private String uid;
    private boolean isHiddenPassword = true;

    private final int SELECT_PICTURE = 200;
    private Uri filePath;
    private Context context;
    private FirebaseAuth mAuth;
    private FirebaseUser account;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseStorage rootRef;
    private StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_info);
        context = this;

        imgAvatar = findViewById(R.id.updateUserInfo_imgAvatar);
        btnBack = findViewById(R.id.updateUserInfo_btnBack);
        btnSave = findViewById(R.id.updateUserInfo_btnSave);
        btnCancel = findViewById(R.id.updateUserInfo_btnCancel);
        edtEmail = findViewById(R.id.updateUserInfo_edtEmail);
        edtName = findViewById(R.id.updateUserInfo_edtName);
        edtPassword = findViewById(R.id.updateUserInfo_edtPassword);

        mAuth = FirebaseAuth.getInstance();
        account = mAuth.getCurrentUser();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("user");

        rootRef = FirebaseStorage.getInstance();
        storageRef = rootRef.getReference();

        Intent intentUserInfo = getIntent();
        if (intentUserInfo != null) {
            theUser = (User) intentUserInfo.getSerializableExtra("user");
            Picasso.get().load(theUser.getAvatar()).into(imgAvatar);
            imgAvatar.setClipToOutline(true);

            edtName.setText(theUser.getName());
            edtEmail.setText(theUser.getEmail());
        }
        uid = account.getUid();

        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooser();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtName.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                if (name.isEmpty() || name.length() <= 0)
                    Toast.makeText(UpdateUserInfoActivity.this, "Tên không được để trống", Toast.LENGTH_SHORT).show();
                else
                    theUser.setName(name);

                if (email.isEmpty() || email.length() <= 0)
                    Toast.makeText(UpdateUserInfoActivity.this, "Email không được để trống", Toast.LENGTH_LONG).show();

                if (password.isEmpty() || password.length() <= 0)
                    Toast.makeText(UpdateUserInfoActivity.this, "Mật khẩu không được để trống", Toast.LENGTH_LONG).show();
                else {
//                xác thực email + password vừa được nhập
                    AuthCredential credential = EmailAuthProvider.getCredential(theUser.getEmail(), password);
                    account.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                account.updateEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            theUser.setEmail(email);
                                            updateData(theUser);
                                            uploadImage();
                                            Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(context, "Mật khẩu không chính xác", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }

    private void updateData(User theUser) {
        myRef.child(uid).child("name").setValue(theUser.getName());
        myRef.child(uid).child("email").setValue(theUser.getEmail());
    }

    private void imageChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == SELECT_PICTURE) {
            Uri selectedImageUri = data.getData();
            filePath = selectedImageUri;
            if (null != selectedImageUri) {
                imgAvatar.setImageURI(selectedImageUri);
            }
        }
    }

    private void uploadImage() {
        if (filePath != null) {
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageRef.child("images/" + UUID.randomUUID().toString());

            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            ref.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    String avatar = task.getResult().toString();
                                    theUser.setAvatar(avatar);
                                    myRef.child(uid).child("avatar").setValue(avatar);
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(context, "Cập nhật ảnh thất bại ", Toast.LENGTH_SHORT).show();
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