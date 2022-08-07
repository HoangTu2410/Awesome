package com.rikkei.awesome.ui.home.friend;

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
import com.google.firebase.database.ValueEventListener;
import com.rikkei.awesome.R;
import com.rikkei.awesome.adapter.AllUserAdapter;
import com.rikkei.awesome.model.RelationShip;
import com.rikkei.awesome.model.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AllUserFragment extends Fragment implements AllUserAdapter.SendRequestFriendListener{

    private AllUserAdapter adapter;
    private List<User> mListUser;
    private List<RelationShip> mListRelationship;
    private User myAccount;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListUser = new ArrayList<>();
        mListRelationship = new ArrayList<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myAccount = snapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference refUser = database.getReference("users");
        refUser.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                User user = snapshot.getValue(User.class);
                if (user != null && !user.getId().equals(myAccount.getId())) {
                    mListUser.add(user);
                    sortByName();
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                User user = snapshot.getValue(User.class);
                if (user == null) {
                    return;
                }

                for (int i=0; i< mListUser.size(); i++) {
                    if (user.getId().equals(mListUser.get(i).getId())) {
                        mListUser.set(i,user);
                        adapter.notifyDataSetChanged();
                        break;
                    }
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (user == null) {
                    return;
                }

                for (int i=0; i< mListUser.size(); i++) {
                    if (user.getId().equals(mListUser.get(i).getId())) {
                        mListUser.remove(i);
                        adapter.notifyDataSetChanged();
                        break;
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

        DatabaseReference refRelationship = database.getReference("relationships");
        refRelationship.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                RelationShip relationShip = snapshot.getValue(RelationShip.class);
                if (relationShip != null && (relationShip.getUser1().getId().equals(myAccount.getId()) ||
                        relationShip.getUser2().getId().equals(myAccount.getId()))) {
                    mListRelationship.add(relationShip);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                RelationShip relationShip = snapshot.getValue(RelationShip.class);
                if (relationShip == null) {
                    return;
                }
                for (int i=0; i< mListRelationship.size(); i++) {
                    if (relationShip.getId()==mListRelationship.get(i).getId()) {
                        mListRelationship.set(i,relationShip);
                        adapter.notifyDataSetChanged();
                        break;
                    }
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                RelationShip relationShip = snapshot.getValue(RelationShip.class);

                for (int i=0; i< mListRelationship.size(); i++) {
                    if (relationShip.getId()==mListRelationship.get(i).getId()) {
                        mListRelationship.remove(i);
                        adapter.notifyDataSetChanged();
                        break;
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
        return inflater.inflate(R.layout.fragment_all_user,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        adapter = new AllUserAdapter(getContext(),mListUser,mListRelationship);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setSendRequestFriendListener(this);
    }

    @Override
    public void onClickSendRequestFriend(View view, int position) {
        DatabaseReference refWriteRelation = FirebaseDatabase.getInstance().getReference("relationships");
        User user1 = new User(myAccount.getId(),myAccount.getFullName(),myAccount.getAvatar());
        User user2 = new User(mListUser.get(position).getId(),mListUser.get(position).getFullName(),mListUser.get(position).getAvatar());
        long id = Calendar.getInstance().getTime().getTime();
        RelationShip relationShip = new RelationShip(id,user1,user2,RelationShip.REQUEST_FRIEND);
        refWriteRelation.child(String.valueOf(id)).setValue(relationShip);
        view.setEnabled(false);
    }

    private void sortByName() {
        Collections.sort(mListUser, new Comparator<User>() {
            @Override
            public int compare(User user, User t1) {
                String[] fullName1 = user.getFullName().split(" ");
                String[] fullName2 = t1.getFullName().split(" ");
                String name1 = fullName1[fullName1.length-1] + " "+ user.getFullName();
                String name2 = fullName2[fullName2.length-1] + " " + t1.getFullName();
                return name1.compareTo(name2);
            }
        });
    }

}
