package com.rikkei.awesome.ui.roomchat;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.rikkei.awesome.R;
import com.rikkei.awesome.adapter.RCViewHolder;
import com.rikkei.awesome.model.Message;

import de.hdodenhof.circleimageview.CircleImageView;

public class RoomChatFragment extends Fragment implements RoomChatInterface{

    RoomChatPresenter roomChatPresenter;
    TextView txt_name_room_chat;
    ImageView btnBack, btn_sent_message;
    TextInputLayout txt_send_message;
    CircleImageView avatar;
    RecyclerView recyclerView;
    View view;
    BottomNavigationView navBottom;
    FirebaseRecyclerAdapter<Message, RCViewHolder> firebaseRecyclerAdapter;
    Context context;
    String Uid, roomID, friendName;

    public RoomChatFragment(Context context, BottomNavigationView navBottom, String roomID, String Uid){
        this.navBottom = navBottom;
        this.context = context;
        this.roomID = roomID;
        this.Uid = Uid;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_room_chat, container, false);
        roomChatPresenter = new RoomChatPresenter(this, context, roomID, Uid);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Init();
        roomChatPresenter.InitRoomChat(Uid, friendName);

        roomChatPresenter.getListMessage(recyclerView);


        btn_sent_message.setOnClickListener(v -> {
            String text = txt_send_message.getEditText().getText().toString().trim();
            if (text.isEmpty())
                return;

            txt_send_message.getEditText().setText("");
            btn_sent_message.setVisibility(View.GONE);
            roomChatPresenter.sendMessage(text);
        });

        btnBack.setOnClickListener(v -> {
            getFragmentManager().popBackStack();
            navBottom.setVisibility(View.VISIBLE);
        });

        txt_send_message.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.length() >= 0){
                    btn_sent_message.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

         LinearLayoutManager manager = new LinearLayoutManager(context);
         manager.setStackFromEnd(true);//đặt hướng stack từ dưới lên trên

        recyclerView.setLayoutManager(manager);

    }

    void Init(){
        btnBack = view.findViewById(R.id.btn_back_room_chat);
        btn_sent_message = view.findViewById(R.id.btn_send_message);
        txt_send_message = view.findViewById(R.id.txt_send_message);
        txt_name_room_chat = view.findViewById(R.id.txt_name_room_chat);

        btn_sent_message.setVisibility(View.GONE);
        recyclerView = view.findViewById(R.id.message_list);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void setTitle(String friendName) {
        txt_name_room_chat.setText(friendName);
    }
}
