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
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context context;
    List<User> mUser;

    public UserAdapter(Context context, List<User> mUser) {
        this.context = context;
        this.mUser = mUser;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_user, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User userMessage = mUser.get(position);
        holder.txt_name.setText(userMessage.getName());
        Picasso.get().load(userMessage.getAvatar()).into(holder.img_avatar_friend);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChatActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("friend", userMessage);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mUser.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView img_avatar_friend;
        TextView txt_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_avatar_friend = itemView.findViewById(R.id.img_user_avt);
            txt_name = itemView.findViewById(R.id.txt_user_name);
        }

    }



}

