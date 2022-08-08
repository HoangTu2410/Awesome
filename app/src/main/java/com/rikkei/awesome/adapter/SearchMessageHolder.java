package com.rikkei.awesome.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rikkei.awesome.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchMessageHolder extends RecyclerView.ViewHolder {

    TextView tv_ten_banbe, title_search, tv_tin_cuoi;
    CircleImageView img_avatar;

    public SearchMessageHolder(@NonNull View itemView) {
        super(itemView);
        img_avatar = itemView.findViewById(R.id.img_avatar);
        tv_ten_banbe = itemView.findViewById(R.id.tv_ten_banbe);
        title_search = itemView.findViewById(R.id.title_search);
        tv_tin_cuoi = itemView.findViewById(R.id.tv_tin_cuoi);
    }
}
