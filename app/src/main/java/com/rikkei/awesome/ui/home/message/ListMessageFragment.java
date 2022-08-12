package com.rikkei.awesome.ui.home.message;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.rikkei.awesome.R;
import com.rikkei.awesome.adapter.RoomChatAdapter;
import com.rikkei.awesome.model.Member;
import com.rikkei.awesome.model.RoomChat;
import com.rikkei.awesome.model.User;
import com.rikkei.awesome.utils.FirebaseQuery;
import com.rikkei.awesome.utils.ItemClickSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListMessageFragment extends Fragment {

    private SearchView searchView;
    private TextView txtCancel;
    private RecyclerView recyclerView;
    View view;
    final String Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private Context context;
    private final List<RoomChat> roomChats = new ArrayList<>();
    private User currentUser;
    private final List<Member> members = new ArrayList<>();
    private final List<User> users = new ArrayList<>();
    ImageView btn_create_roomchat;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list_message,container,false);
        context = getActivity().getApplicationContext();

        FirebaseDatabase.getInstance();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Init();

        searchView.setOnQueryTextFocusChangeListener((view1, b) -> txtCancel.setVisibility(TextView.VISIBLE));

        txtCancel.setOnClickListener(view12 -> txtCancel.setVisibility(TextView.GONE));

        btn_create_roomchat.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.home_container, new CreateRoomChatFragment(context, Uid), "Create_roomChat")
                    .addToBackStack("Create_roomChat")
                    .commit();
        });

        getListRoom(recyclerView, Uid);
    }

    void Init(){
        searchView = view.findViewById(R.id.searchView);
        txtCancel = view.findViewById(R.id.txtCancel);
        recyclerView = view.findViewById(R.id.recyclerView);
        btn_create_roomchat = view.findViewById(R.id.btn_create_roomChat);
    }

    public void showListRoomChatFailed() {
        Toast.makeText(context, R.string.getListFailed, Toast.LENGTH_SHORT).show();
    }

    public void openRoomChat(String roomID) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.home_container, new RoomChatFragment(context, roomID, Uid), "RoomChat")
                .addToBackStack("RoomChat")
                .commit();
    }

    public void getListRoom(RecyclerView recyclerView, String UId) {//lay danh sach cac phong chat cua nguoi dung kem cac thong tin lien quan

        FirebaseQuery.getListUser(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GenericTypeIndicator<HashMap<String, User>> objGTI = new GenericTypeIndicator<HashMap<String, User>>() {
                    @Override
                    public boolean equals(@Nullable Object obj) {
                        return super.equals(obj);
                    }
                };
                Map<String, User> objHM = snapshot.getValue(objGTI);
                final List<User> objAL = new ArrayList<>(objHM.values());
                for (User tmp : objAL) {
                    users.add(tmp);
                    if (tmp.getId().equals(UId)) currentUser = tmp; //get all information of current user
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseQuery.getListMember(UId, new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                members.clear();
                GenericTypeIndicator<HashMap<String, Member>> objGTI = new GenericTypeIndicator<HashMap<String, Member>>() {
                    @Override
                    public boolean equals(@Nullable Object obj) {
                        return super.equals(obj);
                    }
                };
                Map<String, Member> objHM = snapshot.getValue(objGTI);
                final List<Member> objAL = new ArrayList<>(objHM.values());
                for (Member tmp: objAL)
                    if (tmp.getUser1().equals(currentUser.getId())||tmp.getUser2().equals(currentUser.getId()))
                        members.add(tmp); // lay thong tin ve cac phong chat nguoi dung tham gia
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseQuery.getListRoomChatLast(UId, new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                roomChats.clear();
                GenericTypeIndicator<HashMap<String, RoomChat>> objGTI = new GenericTypeIndicator<HashMap<String, RoomChat>>() {
                    @Override
                    public boolean equals(@Nullable Object obj) {
                        return super.equals(obj);
                    }
                };
                Map<String, RoomChat> objHM = snapshot.getValue(objGTI);
                final List<RoomChat> objAL = new ArrayList<>(objHM.values());
                roomChats.clear();
                for (RoomChat tmp: objAL)
                    for (Member mem: members)
                        if (tmp.getId().equals(mem.getId()))
                            roomChats.add(tmp); //lay toan bo phong chat

                recyclerView.setAdapter(new RoomChatAdapter(context, roomChats, members, users, UId));
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                showListRoomChatFailed();
            }
        });//get all roomchat end with Uid

        ItemClickSupport.addTo(recyclerView)
                .setOnItemClickListener(((recyclerView1, position, v) -> openRoomChat(roomChats.get(position).getId()))); //them su kien khi click vao cac phong
    }
}
