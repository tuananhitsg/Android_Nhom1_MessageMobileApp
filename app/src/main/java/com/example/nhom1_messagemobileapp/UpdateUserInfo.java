package com.example.nhom1_messagemobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

public class UpdateUserInfo extends AppCompatActivity {
    private ImageView imgLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_info);

        imgLogo = findViewById(R.id.updateUserInfo_imgAvatar);

        imgLogo.setImageResource(R.drawable.logo);
        imgLogo.setClipToOutline(true);
    }
}