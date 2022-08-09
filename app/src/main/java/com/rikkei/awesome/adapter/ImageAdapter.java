package com.rikkei.awesome.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rikkei.awesome.R;
import com.rikkei.awesome.utils.GlideApp;

import java.io.File;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder>  {

    Context context;
    List<File> listUrlImage;
    SparseBooleanArray imageStateArray = new SparseBooleanArray();

    public ImageAdapter(Context context, List<File> listUrlImage) {
        this.context = context;
        this.listUrlImage = listUrlImage;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImageViewHolder(LayoutInflater.from(context).inflate(R.layout.image_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        GlideApp.with(context).load(listUrlImage.get(position).getAbsolutePath()).override(150, 150).centerCrop().into(holder.imgView);
    }

    @Override
    public int getItemCount() {
        return listUrlImage.size();
    }



    public static class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imgView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imgView = itemView.findViewById(R.id.img_picture);
            imgView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }


    }
}
