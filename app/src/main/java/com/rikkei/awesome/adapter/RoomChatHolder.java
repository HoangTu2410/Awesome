package com.rikkei.awesome.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rikkei.awesome.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class RoomChatHolder extends RecyclerView.ViewHolder {

    TextView tv_ten_banbe, tv_tgian_tin_cuoi, tv_tin_cuoi;
    CircleImageView img_avatar;

    public RoomChatHolder(@NonNull View itemView) {
        super(itemView);
        img_avatar = itemView.findViewById(R.id.img_avatar);
        tv_ten_banbe = itemView.findViewById(R.id.tv_ten_banbe);
        tv_tgian_tin_cuoi = itemView.findViewById(R.id.tv_tgian_tin_cuoi);
        tv_tin_cuoi = itemView.findViewById(R.id.tv_tin_cuoi);
    }
}
