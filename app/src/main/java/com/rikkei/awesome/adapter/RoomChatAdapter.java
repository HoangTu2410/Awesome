package com.rikkei.awesome.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.type.DateTime;
import com.google.type.DateTimeOrBuilder;
import com.rikkei.awesome.R;
import com.rikkei.awesome.model.Member;
import com.rikkei.awesome.model.RoomChat;
import com.rikkei.awesome.model.User;
import com.rikkei.awesome.utils.FirebaseQuery;
import com.rikkei.awesome.utils.GlideApp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;

public class RoomChatAdapter extends RecyclerView.Adapter<RoomChatHolder> {

    Context context;
    List<RoomChat> listRoomChat;
    List<Member> listMember;
    List<User> listUser ;
    String Uid;

    public RoomChatAdapter(Context context, List<RoomChat> listRoomChat, List<Member> listMember, List<User> listUser, String Uid) {
        this.context = context;
        this.listRoomChat = listRoomChat;
        this.listMember = listMember;
        this.Uid =Uid;
        this.listUser = listUser;
    }



    @NonNull
    @Override
    public RoomChatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RoomChatHolder(LayoutInflater.from(context).inflate(R.layout.item_recyclerview_message, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RoomChatHolder holder, int position) {





        FirebaseStorage storage = FirebaseStorage.getInstance();

        final long mb = 1024*1024;

        String name = "";
        for (Member member: listMember){
            if (member.getId().equals(listRoomChat.get(position).getId()))
                if (member.getUser1().equals(Uid)){
                    for (User user: listUser)
                        if (user.getId().equals(member.getUser2())){
                            StorageReference gsRef = storage.getReference(String.valueOf(Uri.parse(user.getAvatar())));
                            GlideApp.with(context).load(gsRef).into(holder.img_avatar);
                            name = user.getFullName();}
                } else {
                    for (User user: listUser)
                        if (user.getId().equals(member.getUser1())){
                            StorageReference gsRef = storage.getReference(String.valueOf(Uri.parse(user.getAvatar())));
                            Glide.with(context).load(gsRef).into(holder.img_avatar);
                            name = user.getFullName();}
                }

        }

        holder.tv_ten_banbe.setText(name);

        if (listRoomChat.get(position).getSendBy().equals(Uid))
            holder.tv_tin_cuoi.setText(context.getString(R.string.tag) + listRoomChat.get(position).getLastMessage());
        else
            holder.tv_tin_cuoi.setText(listRoomChat.get(position).getLastMessage());
        long ts = Long.parseLong(listRoomChat.get(position).getTime());

        if (Math.abs(System.currentTimeMillis() - ts) <= 172800000){
            if (Math.abs(System.currentTimeMillis() - ts) <= 86400000){
                Date date = new Date(ts);
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                sdf.setTimeZone(TimeZone.getDefault());
                holder.tv_tgian_tin_cuoi.setText(sdf.format(date));
            } else
            holder.tv_tgian_tin_cuoi.setText(R.string.yesterday);
        } else if (Math.abs(System.currentTimeMillis() - ts) > 172800000){
            Date date = new Date(ts);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            holder.tv_tgian_tin_cuoi.setText(sdf.format(date));
        }

    }

    @Override
    public int getItemCount() {
        return listRoomChat.size();
    }

    void setAvatar(String path){

    }

}
