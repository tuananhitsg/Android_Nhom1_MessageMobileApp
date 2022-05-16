package com.example.nhom1_messagemobileapp;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.nhom1_messagemobileapp.adapter.ChatListAdapter;
import com.example.nhom1_messagemobileapp.dao.UserSqlDAO;
import com.example.nhom1_messagemobileapp.database.Database;
import com.example.nhom1_messagemobileapp.entity.Message;
import com.example.nhom1_messagemobileapp.entity.StickerPackage;
import com.example.nhom1_messagemobileapp.entity.User;
import com.example.nhom1_messagemobileapp.utils.FlaticonAPI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;


public class HomeFragment extends Fragment {

    private String uid = "";
    private RecyclerView recyclerView;
    private ChatListAdapter recyclerAdapter;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private DatabaseReference refMessage;
    private DatabaseReference refUser;
    private User myUser;
    private User friendUser;
    private Database sqlDatabase;
    private UserSqlDAO userSqlDAO;

    public HomeFragment() {
        uid = FirebaseAuth.getInstance().getUid();
    }

    public HomeFragment(String uid) {
        this.uid = uid;
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        refMessage = firebaseDatabase.getReference("message");
        refUser = firebaseDatabase.getReference("user");

        sqlDatabase = Database.getInstance(null);
        userSqlDAO = sqlDatabase.getUserSqlDAO();

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerAdapter = new ChatListAdapter(getContext());
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ImageView imgAvatar = view.findViewById(R.id.img_avatar);

        refUser.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User theUser = dataSnapshot.getValue(User.class);
                Picasso.get().load(theUser.getAvatar()).into(imgAvatar);
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

        getFriends();


        return view;
    }

    public void getFriends(){
        Log.e("getFriends", "getFriends");
        ShowListUserTask showListUserTask = new ShowListUserTask(true);
        showListUserTask.execute();

        refMessage.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ShowListUserTask showListUserTask = new ShowListUserTask(false);
                showListUserTask.execute();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

    }

    public class ShowListUserTask extends AsyncTask<Void, Void, List<User>> {
        private boolean sql;
        public ShowListUserTask(boolean sql){
            this.sql = sql;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<User> doInBackground (Void...voids){
            List<User> friends = userSqlDAO.getFriends(uid, sql);
            return friends;
        }

        @Override
        protected void onPostExecute (List<User> friends){
            Log.e("get friendsssssssssssssss", friends.toString());
            recyclerAdapter.setFriends(friends);
        }
    }

    public class LoadPackagesTask extends AsyncTask<String, String, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground (String...params){
            if(FlaticonAPI.packages.size() == 0){
                FlaticonAPI flaticonApi = new FlaticonAPI();
                flaticonApi.getPackages();
            }
            return null;
        }

        @Override
        protected void onPostExecute (Void v){

        }
    }
}