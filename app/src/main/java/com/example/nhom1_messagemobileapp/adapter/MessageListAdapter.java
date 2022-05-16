package com.example.nhom1_messagemobileapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom1_messagemobileapp.ChatActivity;
import com.example.nhom1_messagemobileapp.DeleteMessageFragment;
import com.example.nhom1_messagemobileapp.R;
import com.example.nhom1_messagemobileapp.StickerBottomSheetFragment;
import com.example.nhom1_messagemobileapp.entity.Message;
import com.example.nhom1_messagemobileapp.entity.User;
import com.example.nhom1_messagemobileapp.utils.CustomeDateTime;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MessageListAdapter  extends RecyclerView.Adapter<MessageListAdapter.ViewHolder>{

    private static final String TAG = "ChatListAdapter";
    private User friend;
    private String uid;
    private Context context;

    List<Message> messages;

    public MessageListAdapter(Context context) {
        this.context = context;
        messages = new ArrayList<>();
        uid = FirebaseAuth.getInstance().getUid();
    }

    public MessageListAdapter(Context context, User friend) {
        this.context = context;
        this.friend = friend;
        messages = new ArrayList<>();
        uid = FirebaseAuth.getInstance().getUid();
    }

    public MessageListAdapter(Context context, User friend, List<Message> messages) {
        this.context = context;
        this.friend = friend;
        this.messages = messages;
        uid = FirebaseAuth.getInstance().getUid();
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
        holder.setIsRecyclable(false);
        Message message = messages.get(position);
        holder.txt_message.setText(message.getContent());
        Picasso.get().load(friend.getAvatar()).into(holder.img_avatar_friend);
        if(uid.equals(message.getFromUid())){
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(holder.container_message);
            constraintSet.connect(holder.txt_message.getId(),ConstraintSet.RIGHT,holder.container_message.getId(),ConstraintSet.RIGHT,0);
            constraintSet.connect(holder.image_message.getId(),ConstraintSet.RIGHT,holder.container_message.getId(),ConstraintSet.RIGHT,0);
            constraintSet.applyTo(holder.container_message);

            if(message.getType() != null && message.getType().equals("image")){
                holder.image_message.setVisibility(View.VISIBLE);
                holder.txt_message.setVisibility(View.INVISIBLE);

                Picasso.get().load(message.getContent()).placeholder(R.drawable.gif_loading).into(holder.image_message);
            }else {
                holder.image_message.setVisibility(View.INVISIBLE);

                Resources resource = context.getResources();
                final int resourceId = resource.getIdentifier("bg_my_message", "drawable",
                        context.getPackageName());
                holder.txt_message.setBackground(resource.getDrawable(resourceId));

                int colorId = resource.getIdentifier("white", "color", context.getPackageName());
                int desiredColor = resource.getColor(colorId);
                holder.txt_message.setTextColor(desiredColor);
            }
        }else{
            if(position == messages.size() - 1 || messages.get(position+1).getFromUid().equals(uid)){
                holder.card_avatar_friend.setVisibility(View.VISIBLE);
            }

            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(holder.container_message);
            constraintSet.connect(holder.txt_message.getId(),ConstraintSet.LEFT,holder.card_avatar_friend.getId(), ConstraintSet.RIGHT,0);
            constraintSet.connect(holder.image_message.getId(),ConstraintSet.LEFT,holder.card_avatar_friend.getId(), ConstraintSet.RIGHT,0);
            constraintSet.applyTo(holder.container_message);

            if(message.getType() != null && message.getType().equals("image")){
                holder.image_message.setVisibility(View.VISIBLE);
                holder.txt_message.setVisibility(View.INVISIBLE);

                Picasso.get().load(message.getContent()).placeholder(R.drawable.gif_loading).into(holder.image_message);

            }else {
                holder.image_message.setVisibility(View.INVISIBLE);

                Resources resource = context.getResources();
                final int resourceId = resource.getIdentifier("bg_message", "drawable",
                        context.getPackageName());
                holder.txt_message.setBackground(resource.getDrawable(resourceId));

                int colorId = resource.getIdentifier("black", "color", context.getPackageName());
                int desiredColor = resource.getColor(colorId);
                holder.txt_message.setTextColor(desiredColor);
            }
        }

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(message.getFromUid().equals(uid)){
                    DeleteMessageFragment deleteMessageFragment = new DeleteMessageFragment(context, message.getFromUid(), message.getToUid(), message.getId());
                    deleteMessageFragment.show(((ChatActivity)context).getSupportFragmentManager(), deleteMessageFragment.getTag());
                }

                return false;
            }
        });
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
        ImageView image_message;
        ConstraintLayout container_message;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            card_avatar_friend = itemView.findViewById(R.id.card_avatar_friend);
            img_avatar_friend = itemView.findViewById(R.id.img_avatar_friend);
            txt_message = itemView.findViewById(R.id.txt_message);
            image_message = itemView.findViewById(R.id.image_message);
            container_message = itemView.findViewById(R.id.container_message);
        }

    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
        notifyDataSetChanged();
    }
}