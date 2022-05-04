package com.example.nhom1_messagemobileapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nhom1_messagemobileapp.adapter.ChatListAdapter;
import com.example.nhom1_messagemobileapp.entity.Friend;
import com.example.nhom1_messagemobileapp.entity.Message;
import com.example.nhom1_messagemobileapp.entity.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private ChatListAdapter recyclerAdapter;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
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

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_home, container, false);


//        User user1 = new User(1, "Trần Văn Nhân", "");
//        User user2 = new User(2, "Trần Văn A", "");
//        List<Message> messages1 = new ArrayList<Message>();
//        messages1.add(new Message(2, user1, user2, "Có ở đó không", LocalDateTime.now()));
//        messages1.add(new Message(1, user1, user2, "Hello", LocalDateTime.of(2022, 05, 01, 11, 30)));
//
//        List<Friend> friendMessages = new ArrayList<Friend>();
//        friendMessages.add(new Friend(1, user2, messages1));
//
//
//        User user3 = new User(2, "Trần Văn B", "");
//        List<Message> messages2 = new ArrayList<Message>();
//        messages2.add(new Message(2, user1, user3, "Aloooooooooooooo", LocalDateTime.now()));
//        friendMessages.add(new Friend(2, user3, messages2));
//
//        recyclerView = view.findViewById(R.id.recyclerView);
//        recyclerAdapter = new ChatListAdapter(getContext(), friendMessages);
////        System.out.println(recyclerView);
//        recyclerView.setAdapter(recyclerAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        return view;
//    }
}