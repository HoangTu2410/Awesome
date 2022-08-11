package com.rikkei.awesome.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rikkei.awesome.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class RCViewHolder extends RecyclerView.ViewHolder {

    public RelativeLayout friend_item, user_item;
    public CircleImageView avatar;
    public ImageView img_user, img_friend;
    public TextView message_friend, message_user, txt_time_friend, txt_time_user;

    public RCViewHolder(@NonNull View itemView) {
        super(itemView);
        friend_item = itemView.findViewById(R.id.friend_item);
        user_item = itemView.findViewById(R.id.user_item);
        avatar = itemView.findViewById(R.id.profile_image_friend);
        message_friend = itemView.findViewById(R.id.show_message_friend);
        message_user = itemView.findViewById(R.id.show_message);
        txt_time_friend = itemView.findViewById(R.id.txt_time_friend_send);
        txt_time_user = itemView.findViewById(R.id.txt_time_user_send);
        img_friend = itemView.findViewById(R.id.img_friend);
        img_user = itemView.findViewById(R.id.img_user);
    }
}
