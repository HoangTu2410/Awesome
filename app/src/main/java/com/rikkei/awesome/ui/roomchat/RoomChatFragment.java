package com.rikkei.awesome.ui.roomchat;

import android.content.Context;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rikkei.awesome.MainActivity;
import com.rikkei.awesome.R;
import com.rikkei.awesome.adapter.MessageAdapter;
import com.rikkei.awesome.adapter.RCViewHolder;
import com.rikkei.awesome.model.Message;
import com.rikkei.awesome.utils.FirebaseQuery;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;

public class RoomChatFragment extends Fragment implements RoomChatInterface{

    RoomChatPresenter roomChatPresenter;
    ImageView btnBack, btn_sent_message;
    TextInputLayout txt_send_message;
    CircleImageView avatar;
    RecyclerView recyclerView;
    View view;
    BottomNavigationView navBottom;
    FirebaseRecyclerAdapter<Message, RCViewHolder> firebaseRecyclerAdapter;
    Context context;
    List<Message> messageList = new ArrayList<>();
    String Uid, roomID;

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
        roomChatPresenter = new RoomChatPresenter(context, this, roomID, Uid);
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

//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("message_text");
//
//        FirebaseRecyclerOptions<Message> options = new FirebaseRecyclerOptions.Builder<Message>()
//                .setQuery(myRef.child("room2"), Message.class).build();
//
//         firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Message, RCViewHolder>(options) {
//            @Override
//            protected void onBindViewHolder(@NonNull RCViewHolder holder, int position, @NonNull Message model) {
//                if (Objects.equals(model.getSentby(), Uid)){
//                    holder.message_user.setText(model.getContent());
//                    holder.message_friend.setVisibility(View.GONE);
//                    holder.avatar.setVisibility(View.GONE);
//                } else {
//                    holder.message_friend.setText(model.getContent());
//                    holder.message_user.setVisibility(View.GONE);
//                    holder.txt_time_user.setVisibility(View.GONE);
//                }
//                long ts = Long.parseLong(model.getTime());
//
//                if ((System.currentTimeMillis() - ts) <= 172800000){
//                    if ((System.currentTimeMillis() - ts) <= 86400000){
//                        Date date = new Date(ts);
//                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
//                        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
//                        holder.txt_time_friend.setText(sdf.format(date));
//                    } else
//                        holder.txt_time_friend.setText(R.string.yesterday);
//                } else if ((System.currentTimeMillis() - ts) > 172800000){
//                    Date date = new Date(ts);
//                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//                    holder.txt_time_friend.setText(sdf.format(date));
//                }
//                //holder.txt_time_friend.setText(model.getTime());
//            }
//
//            @NonNull
//            @Override
//            public RCViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                return new RCViewHolder(LayoutInflater.from(context).inflate(R.layout.chat_item, parent, false));
//            }
//        };



         LinearLayoutManager manager = new LinearLayoutManager(context);
         manager.setStackFromEnd(true);//đặt hướng stack từ dưới lên trên

        recyclerView.setLayoutManager(manager);
        //recyclerView.setAdapter(firebaseRecyclerAdapter);

    }

    void Init(){
        btnBack = view.findViewById(R.id.btn_back_room_chat);
        btn_sent_message = view.findViewById(R.id.btn_send_message);
        txt_send_message = view.findViewById(R.id.txt_send_message);
        btn_sent_message.setVisibility(View.GONE);
        recyclerView = view.findViewById(R.id.message_list);
    }

    @Override
    public void onStart() {
        super.onStart();
        //firebaseRecyclerAdapter.startListening();
    }
}