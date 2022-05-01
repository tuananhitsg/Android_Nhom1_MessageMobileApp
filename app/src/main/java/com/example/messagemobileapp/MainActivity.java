package com.example.messagemobileapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.messagemobileapp.adapter.ChatListAdapter;
import com.example.messagemobileapp.entity.Friend;
import com.example.messagemobileapp.entity.Message;
import com.example.messagemobileapp.entity.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager mPager;
    private BottomNavigationView navigationView;
    private ScreenSlidePagerAdapter pagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPager = findViewById(R.id.pager);
        navigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);

        navigationView.setOnNavigationItemSelectedListener((MenuItem item) -> {
            switch (item.getItemId()){
                case R.id.tinnhan:
                    mPager.setCurrentItem(0);
                    return true;
                case R.id.danhba:
                    mPager.setCurrentItem(1);
                    return true;
                case R.id.canhan:
                    mPager.setCurrentItem(2);
                    return true;
            }
            return false;
        });

        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(pagerAdapter);

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {}
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            public void onPageSelected(int position) {
                if(position == 0) {
                    navigationView.setSelectedItemId(R.id.tinnhan);
                }if(position == 1) {
                    navigationView.setSelectedItemId(R.id.danhba);
                }if(position == 2) {
                    navigationView.setSelectedItemId(R.id.canhan);
                }
            }
        });


    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 0) {
                return new HomeFragment();
            }if(position == 1) {
                return new DanhBaFragment();
            }if(position == 2) {
                return new CaNhanFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}