package com.rikkei.awesome.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.rikkei.awesome.R;
import com.rikkei.awesome.model.User;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.MyHolderView> {

    Context context;
    ArrayList<User> arrayList;

    public FriendAdapter(Context context, ArrayList<User> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHolderView(LayoutInflater.from(context).inflate(R.layout.item_recyclerview_2, parent, false));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull MyHolderView holder, int position) {
        Collections.sort(arrayList, Comparator.comparing(User::getFullName));
        holder.section.setText(arrayList.get(position).getFullName().substring(0, 1));
        holder.friend_name.setText(arrayList.get(position).getFullName());
        if (position > 0){
            int i = position - 1;
            if ( arrayList.get(position).getFullName().substring(0,1).equals(arrayList.get(i).getFullName().substring(0,1)))
                holder.getSection().setVisibility(TextView.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class MyHolderView extends RecyclerView.ViewHolder {

        TextView section, friend_name;
        CircleImageView avatar_friend;

        public MyHolderView(@NonNull View itemView) {
            super(itemView);
            section = itemView.findViewById(R.id.section);
            friend_name = itemView.findViewById(R.id.friend_name);
            avatar_friend = itemView.findViewById(R.id.avatar_friend);
        }

        public TextView getSection() {
            return section;
        }
    }
}
