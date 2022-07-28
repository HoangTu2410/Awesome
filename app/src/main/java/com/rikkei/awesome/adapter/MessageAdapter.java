package com.rikkei.awesome.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rikkei.awesome.R;
import com.rikkei.awesome.model.RoomChat;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyHolder> {

    Context context;
    ArrayList<RoomChat> listFriend;

    public MessageAdapter(Context context, ArrayList<RoomChat> listFriend) {
        this.context = context;
        this.listFriend = listFriend;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(context).inflate(R.layout.item_recyclerview_1, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        if (listFriend.get(position).getDescription() != ""){
            holder.tv_ten_banbe.setText(listFriend.get(position).getDescription());
        } else
            holder.tv_ten_banbe.setText(listFriend.get(position).getUser1());
        holder.tv_tgian_tin_cuoi.setText(listFriend.get(position).getTime());
        holder.tv_tin_cuoi.setText(listFriend.get(position).getLastMessage());
    }

    @Override
    public int getItemCount() {
        return listFriend.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {

        TextView tv_ten_banbe, tv_tgian_tin_cuoi, tv_tin_cuoi;
        CircleImageView img_avatar;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            img_avatar = itemView.findViewById(R.id.img_avatar);
            tv_ten_banbe = itemView.findViewById(R.id.tv_ten_banbe);
            tv_tgian_tin_cuoi = itemView.findViewById(R.id.tv_tgian_tin_cuoi);
            tv_tin_cuoi = itemView.findViewById(R.id.tv_tin_cuoi);
        }
    }
}
