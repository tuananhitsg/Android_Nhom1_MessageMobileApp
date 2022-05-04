package com.example.nhom1_messagemobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.nhom1_messagemobileapp.entity.UserDemoFirebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class DemoFirebase extends AppCompatActivity {
    private Button btnLogin, btnUploadStorage, btnDownloadStorage;
    private ImageView imageView;
    private FirebaseAuth mAuth;
    private Context context;
    private FirebaseStorage rootRef;
    private StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_firebase);
        context = this;

        rootRef = FirebaseStorage.getInstance();
        storageRef = rootRef.getReference();

        btnLogin = findViewById(R.id.btnLogin);
        btnUploadStorage = findViewById(R.id.btnUploadStorage);
        btnDownloadStorage = findViewById(R.id.btnDownloadStorage);
        imageView = findViewById(R.id.imageView);

        mAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dangKy();
            }
        });

        btnUploadStorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StorageReference mountainsRef = storageRef.child("pet_shop.png");
                StorageReference mountainImagesRef = storageRef.child("images/pet_shop.png");
                mountainsRef.getName().equals(mountainImagesRef.getName());
                mountainsRef.getPath().equals(mountainImagesRef.getPath());

                imageView.setDrawingCacheEnabled(true);
                imageView.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] data = baos.toByteArray();

                UploadTask uploadTask = mountainsRef.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(context, "upload that bai", Toast.LENGTH_LONG).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(context, "upload thanh cong", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        btnDownloadStorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StorageReference pathReference = storageRef.child("pet_shop.png");

                try {
                    File localFile = File.createTempFile("images", "png");
                    pathReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(context, "download thành công", Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, "download that bai", Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void dangKy() {
        Random random = new Random();
        String email = "demo" + String.valueOf(random.nextInt(1000000) + 1) + "@gmail.com";
        String password = "123456";
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String uid = mAuth.getCurrentUser().getUid();
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference("user");
                            UserDemoFirebase user = new UserDemoFirebase(email, password);
                            myRef.child(uid).setValue(user);
                        }
                    }
                });
    }
}