package com.rikkei.awesome.adapter;

import android.content.Context;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.type.DateTime;
import com.google.type.DateTimeOrBuilder;
import com.rikkei.awesome.R;
import com.rikkei.awesome.model.RoomChat;
import com.rikkei.awesome.model.User;
import com.rikkei.awesome.utils.FirebaseQuery;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

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

        FirebaseQuery.getUser(listFriend.get(position).getSendBy(), new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                holder.tv_ten_banbe.setText(user.getFullName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                holder.tv_ten_banbe.setText("Nguoi dung vo danh");
            }
        });

        holder.tv_ten_banbe.setText(listFriend.get(position).getSendBy());

        holder.tv_tin_cuoi.setText(listFriend.get(position).getLastMessage());

        long ts = Long.parseLong(listFriend.get(position).getTime());
        long es = System.currentTimeMillis() - ts;

        if ((System.currentTimeMillis() - es) <= 172800000){
            if ((System.currentTimeMillis() - es) <= 86400000){
                Date date = new Date(es);
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
                holder.tv_tgian_tin_cuoi.setText(sdf.format(date));
            } else
            holder.tv_tgian_tin_cuoi.setText(R.string.yesterday);
        } else if ((System.currentTimeMillis() - es) > 86400000){
            Date date = new Date(es);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            holder.tv_tgian_tin_cuoi.setText(sdf.format(date));
        }

    }

    @Override
    public int getItemCount() {
        return listFriend.size();
    }

}
