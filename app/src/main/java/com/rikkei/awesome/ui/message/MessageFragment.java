package com.rikkei.awesome.ui.message;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rikkei.awesome.adapter.MessageAdapter;
import com.rikkei.awesome.R;
import com.rikkei.awesome.model.Message;
import com.rikkei.awesome.model.User;

import java.util.ArrayList;

public class MessageFragment extends Fragment implements MessageInterface{

    View view;
    Context context;
    RecyclerView recyclerView;

    public MessageFragment(){}
    public MessageFragment(Context context){
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_child_message, container, false);

        Init();

        ArrayList<Message> arrayList = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new MessageAdapter(context, arrayList));

        return view;
    }

    void Init(){
        recyclerView = view.findViewById(R.id.recycler_message);
    }

    @Override
    public void getListRoom(String username, ValueEventListener valueEventListener) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("room_chat");
    }

    @Override
    public void onUserLongClicked(User user, int position) {

    }

    @Override
    public void onMessageClicked(User user) {

    }
}
