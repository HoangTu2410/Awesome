package com.rikkei.awesome.Adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class AllFriendAdapter extends RecyclerView.Adapter<AllFriendAdapter.MyHolderView> {

    Context context;
    ArrayList<User> arrayList;
    private final int SHOW_MENU = 1;
    private final int HIDE_MENU = 2;

    public AllFriendAdapter(Context context, ArrayList<User> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(context).inflate(R.layout.item_recyclerview_3, parent, false);
        return new MyHolderView(v);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull MyHolderView holder, int position) {
        Collections.sort(arrayList, Comparator.comparing(User::getFullName));
        holder.section.setText(arrayList.get(position).getFullName().substring(0, 1));
        holder.friend_name.setText(arrayList.get(position).getFullName());
        holder.btn_refuse_friend.setVisibility(View.GONE);
        if (position > 0){
            int i = position - 1;
            if (i < arrayList.size() && arrayList.get(position).getFullName().substring(0,1).equals(arrayList.get(i).getFullName().substring(0,1)))
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
        Button btn_add_friend, btn_refuse_friend;

        public MyHolderView(@NonNull View itemView) {
            super(itemView);
            section = itemView.findViewById(R.id.section);
            friend_name = itemView.findViewById(R.id.friend_name);
            avatar_friend = itemView.findViewById(R.id.avatar_friend);
            btn_add_friend = itemView.findViewById(R.id.btn_add_friend);
        }

        public TextView getSection() {
            return section;
        }
    }
}
