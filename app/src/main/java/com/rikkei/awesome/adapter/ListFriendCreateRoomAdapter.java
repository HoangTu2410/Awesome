package com.rikkei.awesome.adapter;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.rikkei.awesome.R;
import com.rikkei.awesome.model.User;
import com.rikkei.awesome.utils.GlideApp;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListFriendCreateRoomAdapter extends RecyclerView.Adapter<ListFriendCreateRoomAdapter.ItemFriendViewHolder> {

    List<User> mList;
    Context context;

    public ListFriendCreateRoomAdapter(Context context, List<User> mList) {
        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public ItemFriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recyclerview_friend_create_roomchat, parent,false);
        return new ItemFriendViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull ItemFriendViewHolder holder, int position) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference myRef = storage.getReference(mList.get(position).getAvatar());

        Collections.sort(mList, Comparator.comparing(User::getFullName));
        holder.friend_name.setText(mList.get(position).getFullName());
        GlideApp.with(context)
                .load(myRef)
                .into(holder.avatar_friend);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ItemFriendViewHolder extends RecyclerView.ViewHolder {
        TextView  friend_name;
        CircleImageView avatar_friend;
        public ItemFriendViewHolder(@NonNull View itemView) {
            super(itemView);
            friend_name = itemView.findViewById(R.id.tv_ten_banbe);
            avatar_friend = itemView.findViewById(R.id.img_avatar_friend);
        }
    }
}
