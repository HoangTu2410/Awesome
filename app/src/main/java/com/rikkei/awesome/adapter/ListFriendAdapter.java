package com.rikkei.awesome.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.rikkei.awesome.R;
import com.rikkei.awesome.model.User;
import com.rikkei.awesome.utils.GlideApp;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListFriendAdapter extends RecyclerView.Adapter<ListFriendAdapter.ItemFriendViewHolder> {

    List<User> mList;
    Context context;

    public ListFriendAdapter(Context context,List<User> mList) {
        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public ItemFriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_friend,parent,false);
        return new ItemFriendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemFriendViewHolder holder, int position) {
        User user = mList.get(position);
        String[] fullName = user.getFullName().split(" ");
        String name = fullName[fullName.length-1];

        holder.friend_name.setText(user.getFullName());
        StorageReference mRef = FirebaseStorage.getInstance().getReference(user.getAvatar());
        GlideApp.with(context).load(mRef).into(holder.avatar_friend);
        holder.btn_add_friend.setVisibility(View.GONE);
        if (position == 0) {
            holder.section.setVisibility(TextView.VISIBLE);
            holder.section.setText(String.valueOf(name.charAt(0)));
        } else {
            User user2 = mList.get(position-1);
            String[] fullName2 = user2.getFullName().split(" ");
            String name2 = fullName2[fullName2.length-1];
            if (String.valueOf(name.charAt(0)).equalsIgnoreCase(String.valueOf(name2.charAt(0)))) {
                holder.section.setVisibility(TextView.GONE);
            } else {
                holder.section.setVisibility(TextView.VISIBLE);
                holder.section.setText(String.valueOf(name.charAt(0)));
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mList != null) {
            return mList.size();
        }
        return 0;
    }

    public static class ItemFriendViewHolder extends RecyclerView.ViewHolder {
        TextView section, friend_name;
        CircleImageView avatar_friend;
        MaterialButton btn_add_friend;
        public ItemFriendViewHolder(@NonNull View itemView) {
            super(itemView);
            section = itemView.findViewById(R.id.section);
            friend_name = itemView.findViewById(R.id.friend_name);
            avatar_friend = itemView.findViewById(R.id.avatar_friend);
            btn_add_friend = itemView.findViewById(R.id.btn_add_friend);
        }
    }
}
