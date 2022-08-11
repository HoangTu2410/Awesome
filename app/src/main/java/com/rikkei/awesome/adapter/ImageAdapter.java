package com.rikkei.awesome.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rikkei.awesome.R;
import com.rikkei.awesome.utils.GlideApp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder>  {

    Context context;
    List<File> listUrlImage;
    SparseBooleanArray imageStateArray = new SparseBooleanArray();
    List<File> selected = new ArrayList<>();

    public ImageAdapter(Context context, List<File> listUrlImage) {
        this.context = context;
        this.listUrlImage = listUrlImage;
    }

    public void setImage(List<File> fileImage){
        listUrlImage = new ArrayList<>();
        this.listUrlImage = fileImage;
        notifyDataSetChanged();
    }

    public List<File> getAll(){
        return listUrlImage;
    }

    public List<File> getSelected(){

//        for(int i = 0; i < listUrlImage.size(); i++){
//            if (imageStateArray.get(i))
//                selected.add(listUrlImage.get(i));
//        }
        return selected;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImageViewHolder(LayoutInflater.from(context).inflate(R.layout.image_item,
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        GlideApp.with(context)
                .load(listUrlImage.get(position)
                        .getAbsolutePath())
                .override(150, 150)
                .centerCrop()
                .into(holder.imgView);
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return listUrlImage.size();
    }

    public SparseBooleanArray getImageStateArray() {
        return imageStateArray;
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imgView;
        CheckedTextView checkedTextView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imgView = itemView.findViewById(R.id.img_picture);
            checkedTextView = itemView.findViewById(R.id.checked_view);
            itemView.setOnClickListener(this);
        }

        void bind(int position){
            checkedTextView.setChecked(imageStateArray.get(position, false));

        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (!imageStateArray.get(position, false)){
                checkedTextView.setChecked(true);
                imageStateArray.put(position, true);
                selected.add(listUrlImage.get(position));
            } else {
                checkedTextView.setChecked(false);
                imageStateArray.put(position, false);
                selected.remove(listUrlImage.get(position));
            }
        }


    }
}
