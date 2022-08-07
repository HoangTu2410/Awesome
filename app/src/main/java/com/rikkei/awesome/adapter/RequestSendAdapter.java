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

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RequestSendAdapter extends RecyclerView.Adapter<RequestSendAdapter.RequestSendViewHolder>{

    Context context;
    List<RelationShip> mList;
    private CancelRequestFriendListener cancelRequestFriendListener;

    public RequestSendAdapter(Context context, List<RelationShip> mList) {
        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public RequestSendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_send_request,parent,false);
        return new RequestSendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestSendViewHolder holder, @SuppressLint("RecyclerView") int position) {
        RelationShip relationShip = mList.get(position);
        holder.friend_name.setText(relationShip.getUser2().getFullName());
        StorageReference mRef = FirebaseStorage.getInstance().getReference(relationShip.getUser2().getAvatar());
        GlideApp.with(context).load(mRef).into(holder.avatar_friend);
        holder.btn_cancel_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cancelRequestFriendListener != null) {
                    cancelRequestFriendListener.onClickCancelRequestFriend(view, relationShip.getId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mList != null) {
            return mList.size();
        }
        return 0;
    }

    public void setCancelRequestFriendListener(CancelRequestFriendListener cancelRequestFriendListener) {
        this.cancelRequestFriendListener = cancelRequestFriendListener;
    }




    public static class RequestSendViewHolder extends RecyclerView.ViewHolder {
        TextView friend_name;
        CircleImageView avatar_friend;
        MaterialButton btn_cancel_request;
        public RequestSendViewHolder(@NonNull View itemView) {
            super(itemView);
            friend_name = itemView.findViewById(R.id.friend_name);
            avatar_friend = itemView.findViewById(R.id.avatar_friend);
            btn_cancel_request = itemView.findViewById(R.id.btn_cancel_request);
        }
    }

    public interface CancelRequestFriendListener{
        void onClickCancelRequestFriend(View view,long idRelationship);
    }
}
