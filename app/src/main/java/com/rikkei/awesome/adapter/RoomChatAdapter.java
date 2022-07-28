package com.rikkei.awesome.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rikkei.awesome.R;
import com.rikkei.awesome.ui.roomchat.RoomChatFragment;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RoomChatAdapter extends RecyclerView.Adapter<RoomChatAdapter.RCViewHolder> {

    Context context;
    ArrayList<String> listMessage;

    public RoomChatAdapter(Context context, ArrayList<String> listMessage) {
        this.context = context;
        this.listMessage = listMessage;
    }

    @NonNull
    @Override
    public RCViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RCViewHolder(LayoutInflater.from(context).inflate(R.layout.chat_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RCViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return listMessage.size();
    }

    public class RCViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout friend_item, user_item;
        CircleImageView avatar;
        TextView message_friend, message_user, txt_time_friend, txt_time_user;

        public RCViewHolder(@NonNull View itemView) {
            super(itemView);
            friend_item = itemView.findViewById(R.id.friend_item);
            user_item = itemView.findViewById(R.id.user_item);
            avatar = itemView.findViewById(R.id.profile_image_friend);
            message_friend = itemView.findViewById(R.id.show_message_friend);
            message_user = itemView.findViewById(R.id.show_message);
            txt_time_friend = itemView.findViewById(R.id.txt_time_friend_send);
            txt_time_user = itemView.findViewById(R.id.txt_time_user_send);
        }
    }
}
