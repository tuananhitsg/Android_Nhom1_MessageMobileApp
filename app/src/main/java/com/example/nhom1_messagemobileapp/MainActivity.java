package com.example.nhom1_messagemobileapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.nhom1_messagemobileapp.database.Database;
import com.example.nhom1_messagemobileapp.entity.User;
import com.example.nhom1_messagemobileapp.service.SyncDatabaseService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private ViewPager mPager;
    private BottomNavigationView navigationView;
    private ScreenSlidePagerAdapter pagerAdapter;
    private String uid = "";
    private Database database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            uid = getIntent().getExtras().getString("uid");
            System.out.println(uid);
        }catch (Exception e){

        }
        database = Room.databaseBuilder(this, Database.class, "mydb")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        
//        database.getUserSqlDAO().insert();
        Log.d("sqlite", database.getUserSqlDAO().findAll().toString());
        Intent intent = new Intent(this, SyncDatabaseService.class);
        Bundle bundle = new Bundle();
        bundle.putString("uid", uid);
        intent.putExtras(bundle);
        startService(intent);

        mPager = findViewById(R.id.pager);
        navigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);

        navigationView.setOnNavigationItemSelectedListener((MenuItem item) -> {
            switch (item.getItemId()) {
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
            public void onPageScrollStateChanged(int state) {
            }

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            public void onPageSelected(int position) {
                if (position == 0) {
                    navigationView.setSelectedItemId(R.id.tinnhan);
                }
                if (position == 1) {
                    navigationView.setSelectedItemId(R.id.danhba);
                }
                if (position == 2) {
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
            if (position == 0) {
                return new HomeFragment();
            }
            if (position == 1) {
                return new DanhBaFragment();
            }
            if (position == 2) {
                return new UserInfoFragment(uid);
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}