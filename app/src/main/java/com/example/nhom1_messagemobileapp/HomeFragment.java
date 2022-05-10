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

import com.example.nhom1_messagemobileapp.adapter.ChatListAdapter;
import com.example.nhom1_messagemobileapp.dao.UserFirebaseDAO;
import com.example.nhom1_messagemobileapp.entity.Message;
import com.example.nhom1_messagemobileapp.entity.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.CountDownLatch;


public class HomeFragment extends Fragment {

    private String uid = "6kBO5yFQu1Y355mxKuc1YSaLtSZ2";
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

    public HomeFragment() {
        // Required empty public constructor
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


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        refMessage = database.getReference("message");
        refUser = database.getReference("user");
        getFriends();


//        User user1 = new User("Trần Văn Nhân", "tranvannhan1911@gmail.com", ""); // me
//        User user2 = new User("Trần Văn A", "tranvannhana@gmail.com", "");
//        List<Message> messages1 = new ArrayList<Message>();
//        messages1.add(new Message(2, user1, user2, "Có ở đó không", LocalDateTime.now()));
//        messages1.add(new Message(1, user1, user2, "Hello", LocalDateTime.of(2022, 05, 01, 11, 30)));
//        user2.setMessages(messages1);

        List<User> userMessages = new ArrayList<User>();
//        userMessages.add(user2);
//
//        User user3 = new User("Trần Văn B", "tranvanb@gmail.com", "");
//        List<Message> messages2 = new ArrayList<Message>();
//        messages2.add(new Message(2, user1, user3, "Aloooooooooooooo", LocalDateTime.now()));
//        user3.setMessages(messages2);
//        userMessages.add(user3);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerAdapter = new ChatListAdapter(getContext(), userMessages);
//        System.out.println(recyclerView);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    public void getFriends(){
        getMyUser();
        Map<String, Message> userLastMessages = new HashMap<>();

        refMessage.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserFirebaseDAO userFirebaseDAO = new UserFirebaseDAO();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Message message = new Message();
                    System.out.println(snapshot);
                    String content = snapshot.child("content").getValue(String.class);
                    String uidFrom = snapshot.child("from").getValue(String.class);
                    String uidTo = snapshot.child("to").getValue(String.class);
                    Long timestamp = snapshot.child("time").getValue(Long.class);
//                    Log.d("date", timestamp.toString());
                    LocalDateTime time = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp),
                                    TimeZone.getDefault().toZoneId());
                    message.setContent(content);
                    message.setTime(time);
                    if(uidFrom.equals(uid)){
                        userLastMessages.put(uidTo, message);
                    }else if(uidTo.equals(uid)){
                        userLastMessages.put(uidFrom, message);
                    }
                }
                ShowListUserTask  showListUserTask = new ShowListUserTask(userLastMessages);
                showListUserTask.execute();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    public void getMyUser(){
        refUser.child(uid).get().addOnCompleteListener(task ->{
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            }
            else {
                myUser = task.getResult().getValue(User.class);
            }
        });
    }


    public class ShowListUserTask extends AsyncTask<String, String, List<User>> {
        Map<String, Message> userLastMessages;

        public ShowListUserTask(Map<String, Message> userLastMessages){
            this.userLastMessages = userLastMessages;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<User> doInBackground (String...params){
            List<User> users = new ArrayList<>();
            CountDownLatch latch = new CountDownLatch(userLastMessages.size());

            userLastMessages.forEach((uid, message)-> {
                refUser.child(uid).get().addOnCompleteListener(task ->{
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                    }
                    else {
                        User user = task.getResult().getValue(User.class);

                        try {
                            Log.d("firebase user", user.toString());
                            user.addMessage(message);
                            users.add(user);
                            Log.d("uid", uid);
                        }catch (Exception e){

                        }
                    }
                    latch.countDown();
                });
            });
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return users;
        }

        @Override
        protected void onPostExecute (List<User> users){
            Log.d("firebase users", users.toString());
            recyclerAdapter.setUserMessages(users);
        }
    }
}