package com.rikkei.awesome.adapter;

import android.annotation.SuppressLint;
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
import com.rikkei.awesome.GlideApp;
import com.rikkei.awesome.R;
import com.rikkei.awesome.model.RelationShip;
import com.rikkei.awesome.model.User;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AllUserAdapter extends RecyclerView.Adapter<AllUserAdapter.ItemUserViewHolder> {

    Context context;
    List<User> mListUser;
    List<RelationShip> mListRelationship;
    private SendRequestFriendListener sendRequestFriendListener;

    public AllUserAdapter(Context context, List<User> mListUser, List<RelationShip> mListRelationship) {
        this.mListUser = mListUser;
        this.mListRelationship = mListRelationship;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_friend,parent,false);
        return new ItemUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemUserViewHolder holder, @SuppressLint("RecyclerView") int position) {
        User user = mListUser.get(position);
        String[] fullName = user.getFullName().split(" ");
        String name = fullName[fullName.length-1];

        holder.friend_name.setText(user.getFullName());
        StorageReference mRef = FirebaseStorage.getInstance().getReference(user.getAvatar());
        GlideApp.with(context).load(mRef).into(holder.avatar_friend);
        holder.btn_add_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sendRequestFriendListener != null) {
                    sendRequestFriendListener.onClickSendRequestFriend(view,position);
                }
            }
        });

        if (position == 0) {
            holder.section.setVisibility(TextView.VISIBLE);
            holder.section.setText(String.valueOf(name.charAt(0)));
        } else {
            User user2 = mListUser.get(position-1);
            String[] fullName2 = user2.getFullName().split(" ");
            String name2 = fullName2[fullName2.length-1];
            if (String.valueOf(name.charAt(0)).equalsIgnoreCase(String.valueOf(name2.charAt(0)))) {
                holder.section.setVisibility(TextView.GONE);
            } else {
                holder.section.setVisibility(TextView.VISIBLE);
                holder.section.setText(String.valueOf(name.charAt(0)));
            }
        }

        int checkFriend = -1;
        for (RelationShip relationShip : mListRelationship) {
            if ((relationShip.getUser1().getId().equals(user.getId()) && relationShip.getStatus()==RelationShip.REQUEST_FRIEND) ||
                    (relationShip.getUser2().getId().equals(user.getId()) && relationShip.getStatus()==RelationShip.REQUEST_FRIEND) ){
                checkFriend = 0;
                break;
            }
            if ((relationShip.getUser1().getId().equals(user.getId()) && relationShip.getStatus()==RelationShip.FRIEND) ||
                    (relationShip.getUser2().getId().equals(user.getId()) && relationShip.getStatus()==RelationShip.FRIEND) ){
                checkFriend = 1;
                break;
            }
        }
        switch (checkFriend) {
            case -1:
                holder.btn_add_friend.setVisibility(MaterialButton.VISIBLE);
                holder.btn_add_friend.setEnabled(true);
                break;
            case RelationShip.REQUEST_FRIEND:
                holder.btn_add_friend.setVisibility(MaterialButton.VISIBLE);
                holder.btn_add_friend.setEnabled(false);
                break;
            case RelationShip.FRIEND:
                holder.btn_add_friend.setVisibility(MaterialButton.INVISIBLE);
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (mListUser != null) {
            return mListUser.size();
        }
        return 0;
    }

    public static class ItemUserViewHolder extends RecyclerView.ViewHolder {
        TextView section, friend_name;
        CircleImageView avatar_friend;
        MaterialButton btn_add_friend;
        public ItemUserViewHolder(@NonNull View itemView) {
            super(itemView);
            section = itemView.findViewById(R.id.section);
            friend_name = itemView.findViewById(R.id.friend_name);
            avatar_friend = itemView.findViewById(R.id.avatar_friend);
            btn_add_friend = itemView.findViewById(R.id.btn_add_friend);
        }
    }

    public void setSendRequestFriendListener(SendRequestFriendListener sendRequestFriendListener) {
        this.sendRequestFriendListener = sendRequestFriendListener;
    }

    public interface SendRequestFriendListener{
        void onClickSendRequestFriend(View view,int position);
    }
}
