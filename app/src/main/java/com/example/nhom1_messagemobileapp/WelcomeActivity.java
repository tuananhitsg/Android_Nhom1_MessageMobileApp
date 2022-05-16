package com.example.nhom1_messagemobileapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.nhom1_messagemobileapp.utils.FlaticonAPI;
import com.example.nhom1_messagemobileapp.utils.SharedPreference;

public class WelcomeActivity extends AppCompatActivity {
    public static Activity fa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fa=this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        boolean isDarkMode = SharedPreference.getInstance(this).getData("isDarkMode", false);
        setNightMode(isDarkMode);

        Button btnStartPage = findViewById(R.id.btn_start);
        btnStartPage.setOnClickListener((v) -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });


    }

    public void setNightMode(boolean isDarkMode) {
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        SharedPreference.getInstance(this).saveData("isDarkMode", isDarkMode);
    }
}