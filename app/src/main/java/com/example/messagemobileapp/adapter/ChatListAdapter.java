package com.example.messagemobileapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messagemobileapp.ChatActivity;
import com.example.messagemobileapp.R;
import com.example.messagemobileapp.entity.Friend;
import com.example.messagemobileapp.utils.CustomeDateTime;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ViewHolder>{

    private static final String TAG = "ChatListAdapter";
    private Context context;

    List<Friend> friendMessages;

    public ChatListAdapter(Context context) {
        this.context = context;
        friendMessages = new ArrayList<>();
    }

    public ChatListAdapter(Context context, List<Friend> friendMessages) {
        this.context = context;
        this.friendMessages = friendMessages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_friend_message, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.itemView.setOnClickListener(view1 -> {
            Intent intent = new Intent(context, ChatActivity.class);
            context.startActivity(intent);
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Friend friendMessage = friendMessages.get(position);
        holder.txt_name.setText(friendMessage.getUser().getName());
        holder.txt_message.setText(friendMessage.getMessages().get(0).getContent());
        holder.txt_time.setText(CustomeDateTime.HMFormat(friendMessage.getMessages().get(0).getTime()));
//        Picasso.get().load(friendMessage.getUser().getImage()).into(holder.img_avatar_friend);
    }


    @Override
    public int getItemCount() {
        return friendMessages.size();
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
//    public void setComponentList(List<CardComponent> componentList) {
//        this.componentList = componentList;
//    }
}