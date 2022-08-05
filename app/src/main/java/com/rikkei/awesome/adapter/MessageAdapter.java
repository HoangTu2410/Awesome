package com.rikkei.awesome.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rikkei.awesome.R;
import com.rikkei.awesome.model.Message;
import com.rikkei.awesome.model.RoomChat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

public class MessageAdapter extends RecyclerView.Adapter<RCViewHolder> {

    Context context;
    List<Message> listMessage;
    String Uid;

    public MessageAdapter(Context context, List<Message> listMessage, String Uid) {
        this.context = context;
        this.listMessage = listMessage;
        this.Uid = Uid;
    }

    @NonNull
    @Override
    public RCViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RCViewHolder(LayoutInflater.from(context).inflate(R.layout.chat_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RCViewHolder holder, int position) {
        long ts = Long.parseLong(listMessage.get(position).getTime());
        long ts1 = 0;
        if (position < listMessage.size() - 1)
            ts1 = Long.parseLong(listMessage.get(position + 1).getTime());
        else ts1 = 0;
        String time = "";
        if ((System.currentTimeMillis() - ts) <= 172800000){
            if ((System.currentTimeMillis() - ts) <= 86400000){
                Date date = new Date(ts);
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                sdf.setTimeZone(TimeZone.getDefault());
                time = sdf.format(date);

            } else
                time = "Yesterday";
        } else if ((System.currentTimeMillis() - ts) > 172800000){
            Date date = new Date(ts);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            time = sdf.format(date);
        }
        if (Math.abs(ts - ts1) < 200000){
            time = "";
        }

        if (Objects.equals(listMessage.get(position).getSentby(), Uid)){
            holder.message_user.setText(listMessage.get(position).getContent());
            holder.message_friend.setVisibility(View.GONE);
            holder.avatar.setVisibility(View.GONE);
            holder.txt_time_friend.setVisibility(View.GONE);
            if (time.equals("")){
                holder.txt_time_user.setVisibility(View.GONE);
                holder.message_user.setBackgroundResource(R.drawable.background_messenger_right_center);
            } else holder.txt_time_user.setText(time);
        } else {
            holder.message_friend.setText(listMessage.get(position).getContent());
            holder.message_user.setVisibility(View.GONE);
            holder.txt_time_user.setVisibility(View.GONE);
            if (time.equals("")){
                holder.txt_time_friend.setVisibility(View.GONE);
                holder.avatar.setVisibility(View.INVISIBLE);
                holder.message_friend.setBackgroundResource(R.drawable.background_messenger_left_center);
            }
            else holder.txt_time_friend.setText(time);
        }

        //holder.txt_time_friend.setText(model.getTime());
    }

    @Override
    public int getItemCount() {
        return listMessage.size();
    }

}
