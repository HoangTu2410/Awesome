package com.rikkei.awesome.ui.home.friend;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rikkei.awesome.R;
import com.rikkei.awesome.adapter.RequestReceivedAdapter;
import com.rikkei.awesome.adapter.RequestSendAdapter;
import com.rikkei.awesome.model.RelationShip;

import java.util.ArrayList;
import java.util.List;

public class RequestFragment extends Fragment implements RequestSendAdapter.CancelRequestFriendListener,
        RequestReceivedAdapter.AcceptRequestFriendListener, RequestReceivedAdapter.RefuseRequestFriendListener {

    List<RelationShip> listRequestSend;
    List<RelationShip> listRequestReceived;
    private RequestSendAdapter sendAdapter;
    private RequestReceivedAdapter receivedAdapter;
    private String myId;
    private FirebaseDatabase database;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listRequestSend = new ArrayList<>();
        listRequestReceived = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("relationships");
        myId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        myRef.orderByChild("status").equalTo(RelationShip.REQUEST_FRIEND).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                RelationShip relationShip = snapshot.getValue(RelationShip.class);
                if (relationShip==null || (!relationShip.getUser1().getId().equals(myId) &&
                        !relationShip.getUser2().getId().equals(myId)))
                    return;
                if (relationShip.getUser1().getId().equals(myId)) {
                    listRequestSend.add(0,relationShip);
                    sendAdapter.notifyDataSetChanged();
                }
                if (relationShip.getUser2().getId().equals(myId)) {
                    listRequestReceived.add(0,relationShip);
                    receivedAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                RelationShip relationShip = snapshot.getValue(RelationShip.class);
                if (relationShip==null || (!relationShip.getUser1().getId().equals(myId) &&
                        !relationShip.getUser2().getId().equals(myId)))
                    return;
                if (relationShip.getUser1().getId().equals(myId)) {
                    boolean exist = false;
                    for (int i=0; i<listRequestSend.size(); i++) {
                        if (listRequestSend.get(i).getId()==relationShip.getId()) {
                            listRequestSend.set(i, relationShip);
                            exist = true;
                            break;
                        }
                    }
                    if (!exist)  listRequestSend.add(0,relationShip);
                    sendAdapter.notifyDataSetChanged();
                }
                if (relationShip.getUser2().getId().equals(myId)) {
                    boolean exist = false;
                    for (int i=0; i<listRequestReceived.size(); i++) {
                        if (listRequestReceived.get(i).getId()==relationShip.getId()) {
                            listRequestReceived.set(i, relationShip);
                            exist = true;
                            break;
                        }
                    }
                    if (!exist) listRequestReceived.add(0,relationShip);
                    receivedAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                RelationShip relationShip = snapshot.getValue(RelationShip.class);
                if ((!relationShip.getUser1().getId().equals(myId) &&
                        !relationShip.getUser2().getId().equals(myId))) {
                    return;
                }
                if (relationShip.getUser1().getId().equals(myId)){
                    for (int i=0; i<listRequestSend.size(); i++) {
                        if (listRequestSend.get(i).getUser2().getId().equals(relationShip.getUser2().getId())) {
                            listRequestSend.remove(i);
                            sendAdapter.notifyDataSetChanged();
                            break;
                        }
                    }
                } else {
                    for (int i = 0; i < listRequestReceived.size(); i++) {
                        if (listRequestReceived.get(i).getUser1().getId().equals(relationShip.getUser1().getId())) {
                            listRequestReceived.remove(i);
                            receivedAdapter.notifyDataSetChanged();
                            break;
                        }
                    }
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_request,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerViewReceived = view.findViewById(R.id.recyclerViewReceived);
        receivedAdapter = new RequestReceivedAdapter(getContext(),listRequestReceived);
        LinearLayoutManager managerReceived = new LinearLayoutManager(getContext());
        recyclerViewReceived.setLayoutManager(managerReceived);
        recyclerViewReceived.setAdapter(receivedAdapter);
        receivedAdapter.setAcceptRequestFriendListener(this);
        receivedAdapter.setRefuseRequestFriendListener(this);

        RecyclerView recyclerViewSend = view.findViewById(R.id.recyclerViewSend);
        sendAdapter = new RequestSendAdapter(getContext(),listRequestSend);
        LinearLayoutManager managerSend = new LinearLayoutManager(getContext());
        recyclerViewSend.setLayoutManager(managerSend);
        recyclerViewSend.setAdapter(sendAdapter);
        sendAdapter.setCancelRequestFriendListener(this);
    }

    @Override
    public void onClickCancelRequestFriend(View view, long idRelationship) {
        new AlertDialog.Builder(getContext())
                .setTitle(getString(R.string.app_name))
                .setMessage("Are you sure you want to cancel this friend request?")
                .setIcon(R.drawable.image_remove)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DatabaseReference myRef = database.getReference("relationships").child(String.valueOf(idRelationship));
                        myRef.removeValue();
                    }
                })
                .setNegativeButton("No",null)
                .show();
    }

    @Override
    public void onClickAcceptRequestFriend(View view, long idRelationship) {
        DatabaseReference myRef = database.getReference("relationships").child(String.valueOf(idRelationship));
        myRef.child("status").setValue(RelationShip.FRIEND);
    }

    @Override
    public void onClickRefuseRequestFriend(View view, long idRelationship) {
        DatabaseReference myRef = database.getReference("relationships").child(String.valueOf(idRelationship));
        myRef.removeValue();
    }
}
