package com.example.nhom1_messagemobileapp;

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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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


        User user1 = new User("Trần Văn Nhân", "tranvannhan1911@gmail.com", ""); // me
        User user2 = new User("Trần Văn A", "tranvannhana@gmail.com", "");
        List<Message> messages1 = new ArrayList<Message>();
        messages1.add(new Message(2, user1, user2, "Có ở đó không", LocalDateTime.now()));
        messages1.add(new Message(1, user1, user2, "Hello", LocalDateTime.of(2022, 05, 01, 11, 30)));
        user2.setMessages(messages1);

        List<User> userMessages = new ArrayList<User>();
        userMessages.add(user2);

        User user3 = new User("Trần Văn B", "tranvanb@gmail.com", "");
        List<Message> messages2 = new ArrayList<Message>();
        messages2.add(new Message(2, user1, user3, "Aloooooooooooooo", LocalDateTime.now()));
        user3.setMessages(messages2);
        userMessages.add(user3);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerAdapter = new ChatListAdapter(getContext(), userMessages);
//        System.out.println(recyclerView);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    public void getFriends(){
        getMyUser();
        Map<String, User> users = new HashMap<>();

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
                    message.setContent(content);

                    if(uidFrom.equals(uid)){
                        if(users.containsKey(uidTo)){
                            friendUser = users.get(uidTo);
                            message.setFrom(myUser);
                            message.setTo(friendUser);
                            friendUser.addMessage(message);
                        }else{
                            refUser.child(uidTo).get().addOnCompleteListener(task ->{
                                if (!task.isSuccessful()) {
                                    Log.e("firebase", "Error getting data", task.getException());
                                }
                                else {
                                    friendUser = task.getResult().getValue(User.class);
                                    message.setFrom(myUser);
                                    message.setTo(friendUser);
                                    friendUser.addMessage(message);
                                    users.put(uidTo, friendUser);
                                }
                            });
                        }

                    }else if(uidTo.equals(uid)){
                        if(users.containsKey(uidFrom)){
                            friendUser = users.get(uidFrom);
                            message.setFrom(friendUser);
                            message.setTo(myUser);
                            friendUser.addMessage(message);
                        }else{
                            refUser.child(uidFrom).get().addOnCompleteListener(task ->{
                                if (!task.isSuccessful()) {
                                    Log.e("firebase", "Error getting data", task.getException());
                                }
                                else {
                                    friendUser = task.getResult().getValue(User.class);
                                    message.setFrom(friendUser);
                                    message.setTo(myUser);
                                    friendUser.addMessage(message);
                                    users.put(uidFrom, friendUser);
                                }
                            });
                        }

                    }

                    break;
                }

//                Log.d("firebase", friendUser.toString());
                Log.d("firebase", users.toString());
                System.out.println(users);
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
}