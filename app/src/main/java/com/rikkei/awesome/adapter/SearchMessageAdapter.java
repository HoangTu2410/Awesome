package com.rikkei.awesome.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.rikkei.awesome.R;
import com.rikkei.awesome.model.Member;
import com.rikkei.awesome.model.Message;
import com.rikkei.awesome.model.RoomChat;
import com.rikkei.awesome.model.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class SearchMessageAdapter extends RecyclerView.Adapter<SearchMessageHolder> {

    Context context;
    List<RoomChat> listRoomChat;
    List<Member> listMember;
    List<User> listUser ;
    List<Message> listMessage;
    String Uid;

    public SearchMessageAdapter(Context context, List<RoomChat> listRoomChat, List<Member> listMember, List<User> listUser, String Uid) {
        this.context = context;
        this.listRoomChat = listRoomChat;
        this.listMember = listMember;
        this.Uid =Uid;
        this.listUser = listUser;
    }



    @NonNull
    @Override
    public SearchMessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchMessageHolder(LayoutInflater.from(context).inflate(R.layout.item_recyclerview_4, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchMessageHolder holder, int position) {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference gsRef = storage.getReferenceFromUrl("gs://awesome-chat-aa87a.appspot.com/images/avatars/default_avatar.png");
        Glide.with(context).load(gsRef).into(holder.img_avatar);

        if (!listMessage.isEmpty())
            holder.title_search.setVisibility(View.VISIBLE);
        else {
            holder.title_search.setVisibility(View.GONE);

        }

        String name = "";
        for (Member member: listMember){
            if (member.getId().equals(listRoomChat.get(position).getId()))
                if (member.getUser1().equals(Uid)){
                    for (User user: listUser)
                        if (user.getId().equals(member.getUser2()))
                            name = user.getFullName();
                } else {
                    for (User user: listUser)
                        if (user.getId().equals(member.getUser1()))
                            name = user.getFullName();
                }

        }
        holder.tv_ten_banbe.setText(name);


    }

    @Override
    public int getItemCount() {
        return listRoomChat.size();
    }

    void setAvatar(String path){

    }

}
