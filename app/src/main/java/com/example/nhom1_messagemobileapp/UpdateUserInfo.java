package com.example.nhom1_messagemobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.nhom1_messagemobileapp.entity.User;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class UpdateUserInfo extends AppCompatActivity {
    private ImageView imgLogo;
    private ImageButton btnBack;
    private Button btnSave, btnCancel;
    private EditText edtEmail, edtName, edtPassword;
    private User theUser;
    private String uid;

    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_info);

        imgLogo = findViewById(R.id.updateUserInfo_imgAvatar);
        btnBack = findViewById(R.id.updateUserInfo_btnBack);
        btnSave = findViewById(R.id.updateUserInfo_btnSave);
        btnCancel = findViewById(R.id.updateUserInfo_btnCancel);
        edtEmail = findViewById(R.id.updateUserInfo_edtEmail);
        edtName = findViewById(R.id.updateUserInfo_edtName);
        edtPassword = findViewById(R.id.updateUserInfo_edtPassword);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("user");

        Intent intentUserInfo = getIntent();
        if (intentUserInfo != null) {
            theUser = (User) intentUserInfo.getSerializableExtra("user");
            Picasso.get().load(theUser.getAvatar()).into(imgLogo);
            imgLogo.setClipToOutline(true);
            edtName.setText(theUser.getName());
            edtEmail.setText(theUser.getEmail());
            uid = intentUserInfo.getStringExtra("uid");
        }

        imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                String name = edtName.getText().toString();
                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();
                if (name.isEmpty() || name.length() <= 0)
                    Toast.makeText(UpdateUserInfo.this, "Tên không được để trống", Toast.LENGTH_SHORT).show();
                else
                    theUser.setName(name);

                if (email.isEmpty() || email.length() <= 0)
                    Toast.makeText(UpdateUserInfo.this, "Email không được để trống", Toast.LENGTH_LONG).show();
                else
                    theUser.setEmail(email);
//                xác thực email + password vừa được nhập
                AuthCredential credential = EmailAuthProvider.getCredential(theUser.getEmail(), password);
                Toast.makeText(UpdateUserInfo.this, credential + "", Toast.LENGTH_SHORT).show();
//                user.reauthenticate(credential)
//                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if (task.isSuccessful()) {
//                                    user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<Void> task) {
//                                            if (task.isSuccessful()) {
//                                                Toast.makeText(getActivity(), "Cập nhật mật khẩu thành công", Toast.LENGTH_LONG).show();
//                                            } else {
//                                                Toast.makeText(getActivity(), "Cập nhật mật khẩu thất bại", Toast.LENGTH_LONG).show();
//                                            }
//                                        }
//                                    });
//                                } else {
//                                    Toast.makeText(getActivity(), "Cập nhật mật khẩu thất bại", Toast.LENGTH_LONG).show();
//                                }
//                            }
//                        });
                updateData(theUser);
            }
        });
    }

    private void updateData(User theUser) {
        myRef.child(uid).child("name").setValue(theUser.getName());
        myRef.child(uid).child("email").setValue(theUser.getEmail());
    }
}