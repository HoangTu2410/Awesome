package com.rikkei.awesome.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rikkei.awesome.R;
import com.rikkei.awesome.model.RoomChat;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<RCViewHolder> {

    Context context;
    ArrayList<RoomChat> listMessage;

    public MessageAdapter(Context context, ArrayList<RoomChat> listMessage) {
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

}
