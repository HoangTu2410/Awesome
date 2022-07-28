package com.rikkei.awesome.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rikkei.training.morkproject.awesomechat.Model.Message;
import com.rikkei.training.morkproject.awesomechat.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyHolder> {

    Context context;
    ArrayList<Message> listFriend;

    public MessageAdapter(Context context, ArrayList<Message> listFriend) {
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
        holder.tv_ten_banbe.setText(listFriend.get(position).getUser1());
        holder.tv_tgian_tin_cuoi.setText(listFriend.get(position).getDate());
        holder.tv_tin_cuoi.setText(listFriend.get(position).getMessage());
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
