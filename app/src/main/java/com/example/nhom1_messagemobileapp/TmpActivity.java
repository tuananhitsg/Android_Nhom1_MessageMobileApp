package com.example.nhom1_messagemobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class TmpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tmp);

        Button btnToHomePage = findViewById(R.id.btn_to_home_page);
        btnToHomePage.setOnClickListener((v) -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }
}