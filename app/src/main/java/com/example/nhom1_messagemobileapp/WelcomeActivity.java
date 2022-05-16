package com.example.nhom1_messagemobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.nhom1_messagemobileapp.utils.FlaticonAPI;

public class WelcomeActivity extends AppCompatActivity {
    public static Activity fa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fa=this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Button btnStartPage = findViewById(R.id.btn_start);
        btnStartPage.setOnClickListener((v) -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });


    }
}