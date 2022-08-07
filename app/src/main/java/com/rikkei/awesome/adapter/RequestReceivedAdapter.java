package com.rikkei.awesome.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.rikkei.awesome.GlideApp;
import com.rikkei.awesome.R;
import com.rikkei.awesome.model.RelationShip;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RequestReceivedAdapter extends RecyclerView.Adapter<RequestReceivedAdapter.RequestReceivedViewHolder> {

    Context context;
    List<RelationShip> mList;
    private AcceptRequestFriendListener acceptRequestFriendListener;
    private RefuseRequestFriendListener refuseRequestFriendListener;

    public RequestReceivedAdapter(Context context, List<RelationShip> mList) {
        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public RequestReceivedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_received_request,parent,false);
        return new RequestReceivedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestReceivedViewHolder holder, @SuppressLint("RecyclerView") int position) {
        RelationShip relationShip = mList.get(position);
        holder.friend_name.setText(relationShip.getUser1().getFullName());
        StorageReference mRef = FirebaseStorage.getInstance().getReference(relationShip.getUser1().getAvatar());
        GlideApp.with(context).load(mRef).into(holder.avatar_friend);
        holder.btn_accept_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (acceptRequestFriendListener != null) {
                    acceptRequestFriendListener.onClickAcceptRequestFriend(view,relationShip.getId());
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

    public void setAcceptRequestFriendListener(AcceptRequestFriendListener acceptRequestFriendListener) {
        this.acceptRequestFriendListener = acceptRequestFriendListener;
    }

    public void setRefuseRequestFriendListener(RefuseRequestFriendListener refuseRequestFriendListener) {
        this.refuseRequestFriendListener = refuseRequestFriendListener;
    }




    public static class RequestReceivedViewHolder extends RecyclerView.ViewHolder {
        TextView friend_name;
        CircleImageView avatar_friend;
        MaterialButton btn_accept_request;
        public RequestReceivedViewHolder(@NonNull View itemView) {
            super(itemView);
            friend_name = itemView.findViewById(R.id.friend_name);
            avatar_friend = itemView.findViewById(R.id.avatar_friend);
            btn_accept_request = itemView.findViewById(R.id.btn_accept_request);
        }
    }

    public interface AcceptRequestFriendListener {
        void onClickAcceptRequestFriend(View view, long idRelationship);
    }

    public interface RefuseRequestFriendListener {
        void onClickRefuseRequestFriend(View view, long idRelationship);
    }
}
