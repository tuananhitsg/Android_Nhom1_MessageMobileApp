package com.example.nhom1_messagemobileapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom1_messagemobileapp.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class StickerAdapter  extends RecyclerView.Adapter<StickerAdapter.ViewHolder> {

    private static final String TAG = "RecyclerMyStickerAdapter";
    private Context context;

    List<String> stickers;

    public StickerAdapter(Context context) {
        this.context = context;
        this.stickers = new ArrayList<>();
    }

    public StickerAdapter(Context context, List<String> stickers) {
        this.context = context;
        this.stickers = stickers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_sticker, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Picasso.get().load(stickers.get(position)).into(holder.img);

//        holder.img.setOnClickListener(v -> {
//            ImageComponent imgComponent = new ImageComponent(context,
//                    ((NewPostActivity) context).getInsideCard(),
//                    100, 100, 300, 300);
//
//            imgComponent.setImageResource(R.drawable.img_boy);
//            imgComponent.setTitle("Sticker "+stickerList.get(position).getStickerID());
//            Picasso.get().load(new File(stickerList.get(position).getSrc())).into(imgComponent);
//            imgComponent.setImageCard(stickerList.get(position));
//            ((NewPostActivity)context).addComponent(imgComponent);
//        });
    }

    @Override
    public int getItemCount() {
        return stickers.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img);
        }

        @Override
        public void onClick(View view) {
        }
    }

    public void addItem(String sticker){
        stickers.add(sticker);
        notifyDataSetChanged();
    }

    public String getItem(int pos){
        return stickers.get(pos);
    }

    public List<String> getStickerList() {
        return stickers;
    }

    public void setStickerList(List<String> stickerList) {
        this.stickers = new ArrayList<>(stickerList);
        notifyDataSetChanged();
    }
}