package com.rikkei.awesome.ui.message;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.rikkei.awesome.MainActivity;
import com.rikkei.awesome.adapter.MessageAdapter;
import com.rikkei.awesome.R;
import com.rikkei.awesome.adapter.RoomChatAdapter;
import com.rikkei.awesome.model.RoomChat;
import com.rikkei.awesome.model.User;
import com.rikkei.awesome.ui.roomchat.RoomChatFragment;
import com.rikkei.awesome.utils.ItemClickSupport;

import java.util.ArrayList;

public class MessageFragment extends Fragment implements MessageInterface{

    View view;
    Context context;
    RecyclerView recyclerView;
    MessagePresenter messagePresenter;
    String UId;

    public MessageFragment(){}
    public MessageFragment(Context context, String UId){
        this.context = context;
        this.UId = UId;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        messagePresenter = new MessagePresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_child_message, container, false);
        Init();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        messagePresenter.getListRoom(UId);
    }

    void Init(){
        recyclerView = view.findViewById(R.id.recycler_message);
    }


    @Override
    public void showListRoomChat(ArrayList<RoomChat> roomChats) {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new MessageAdapter(context, roomChats));

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container_3, new RoomChatFragment(), "RoomChat").addToBackStack("RoomChat").commit();
            }
        });
    }

    @Override
    public void showListRoomChatFailed() {
        Toast.makeText(context, R.string.getListFailed, Toast.LENGTH_SHORT).show();
    }
}
