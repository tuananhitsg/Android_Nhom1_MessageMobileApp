package com.example.nhom1_messagemobileapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom1_messagemobileapp.ChatActivity;
import com.example.nhom1_messagemobileapp.R;
import com.example.nhom1_messagemobileapp.entity.Message;
import com.example.nhom1_messagemobileapp.entity.User;
import com.example.nhom1_messagemobileapp.utils.CustomeDateTime;

import java.util.ArrayList;
import java.util.List;

public class MessageListAdapter  extends RecyclerView.Adapter<MessageListAdapter.ViewHolder>{

    private static final String TAG = "ChatListAdapter";
    private User user;
    private Context context;

    List<Message> messages;

    public MessageListAdapter(Context context) {
        this.context = context;
        messages = new ArrayList<>();
    }

    public MessageListAdapter(Context context, List<Message> messages, User user) {
        this.context = context;
        this.messages = messages;
        this.user = user;
    }

    @NonNull
    @Override
    public MessageListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_message, parent, false);
        MessageListAdapter.ViewHolder viewHolder = new MessageListAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MessageListAdapter.ViewHolder holder, int position) {
        Message message = messages.get(position);
        holder.txt_message.setText(message.getContent());
        System.out.println(user.equals(message.getFrom()));
        if(user.equals(message.getFrom())){
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(holder.container_message);
            constraintSet.connect(holder.txt_message.getId(),ConstraintSet.RIGHT,holder.container_message.getId(),ConstraintSet.RIGHT,0);
            constraintSet.applyTo(holder.container_message);

            Resources resource = context.getResources();
            final int resourceId = resource.getIdentifier("bg_my_message", "drawable",
                    context.getPackageName());
            holder.txt_message.setBackground(resource.getDrawable(resourceId));

            int colorId = resource.getIdentifier("white", "color", context.getPackageName());
            int desiredColor = resource.getColor(colorId);
            holder.txt_message.setTextColor(desiredColor);
        }else{

            holder.card_avatar_friend.setVisibility(View.VISIBLE);
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(holder.container_message);
            constraintSet.connect(holder.txt_message.getId(),ConstraintSet.LEFT,holder.card_avatar_friend.getId(), ConstraintSet.RIGHT,0);
            constraintSet.applyTo(holder.container_message);
        }
//        Picasso.get().load(friendMessage.getUser().getImage()).into(holder.img_avatar_friend);
    }


    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        CardView card_avatar_friend;
        ImageView img_avatar_friend;
        TextView txt_message;
        ConstraintLayout container_message;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            card_avatar_friend = itemView.findViewById(R.id.card_avatar_friend);
            img_avatar_friend = itemView.findViewById(R.id.img_avatar_friend);
            txt_message = itemView.findViewById(R.id.txt_message);
            container_message = itemView.findViewById(R.id.container_message);
        }

    }

}