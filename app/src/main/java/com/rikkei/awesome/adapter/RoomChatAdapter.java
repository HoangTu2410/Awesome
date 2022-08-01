package com.rikkei.awesome.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rikkei.awesome.R;
import com.rikkei.awesome.model.RoomChat;

import java.util.List;

public class RoomChatAdapter extends RecyclerView.Adapter<RoomChatHolder> {

    Context context;
    List<RoomChat> listFriend;

    public RoomChatAdapter(Context context, List<RoomChat> listFriend) {
        this.context = context;
        this.listFriend = listFriend;
    }

    @NonNull
    @Override
    public RoomChatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RoomChatHolder(LayoutInflater.from(context).inflate(R.layout.item_recyclerview_1, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RoomChatHolder holder, int position) {
        holder.tv_ten_banbe.setText(listFriend.get(position).getSendBy());
        holder.tv_tgian_tin_cuoi.setText(listFriend.get(position).getTime());
        holder.tv_tin_cuoi.setText(listFriend.get(position).getLastMessage());
    }

    @Override
    public int getItemCount() {
        return listFriend.size();
    }

}
