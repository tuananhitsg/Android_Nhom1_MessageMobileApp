package com.example.nhom1_messagemobileapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom1_messagemobileapp.ChatActivity;
import com.example.nhom1_messagemobileapp.R;
import com.example.nhom1_messagemobileapp.entity.User;
import com.example.nhom1_messagemobileapp.utils.CustomeDateTime;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ViewHolder>{

    private static final String TAG = "ChatListAdapter";
    private Context context;

    List<User> friends;

    public ChatListAdapter(Context context) {
        this.context = context;
        friends = new ArrayList<>();
    }

    public ChatListAdapter(Context context, List<User> userMessages) {
        this.context = context;
        this.friends = userMessages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_friend_message, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User userMessage = friends.get(position);
        holder.txt_name.setText(userMessage.getName());
        holder.txt_message.setText(userMessage.getMessages().get(0).getContent());
        holder.txt_time.setText(CustomeDateTime.HMFormat(userMessage.getMessages().get(0).getTime()));
        Picasso.get().load(userMessage.getAvatar()).into(holder.img_avatar_friend);
        holder.itemView.setOnClickListener(view1 -> {
            Intent intent = new Intent(context, ChatActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("friend", userMessage);
            intent.putExtras(bundle);
            context.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return friends.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView img_avatar_friend;
        TextView txt_name, txt_message, txt_time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_avatar_friend = itemView.findViewById(R.id.img_avatar_friend);
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_message = itemView.findViewById(R.id.txt_message);
            txt_time = itemView.findViewById(R.id.txt_time);
        }

    }

//    public List<CardComponent> getComponentList() {
//        return componentList;
//    }
//
    public void setFriends(List<User> friends) {
        this.friends = friends;
        notifyDataSetChanged();
    }
}