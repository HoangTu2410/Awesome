package com.rikkei.awesome.ui.roomchat;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.rikkei.awesome.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class RoomChatFragment extends Fragment {

    ImageView btnBack, btn_sent_message;
    TextInputLayout txt_send_message;
    CircleImageView avatar;
    RecyclerView recyclerView;
    View view;
    BottomNavigationView navBottom;
    Context context;
    String Uid;

    public RoomChatFragment(Context context, BottomNavigationView navBottom, String Uid){
        this.navBottom = navBottom;
        this.context = context;
        this.Uid = Uid;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_room_chat, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Init();
        btnBack.setOnClickListener(v -> {
            getFragmentManager().popBackStack();
            navBottom.setVisibility(View.VISIBLE);
        });

        txt_send_message.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_sent_message.setVisibility(View.VISIBLE);
            }
        });
    }

    void Init(){
        btnBack = view.findViewById(R.id.btn_back_room_chat);
        btn_sent_message = view.findViewById(R.id.btn_send_message);
        txt_send_message = view.findViewById(R.id.txt_send_message);
        btn_sent_message.setVisibility(View.GONE);
    }
}
