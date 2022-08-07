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
import com.rikkei.awesome.R;
import com.rikkei.awesome.adapter.ListFriendAdapter;
import com.rikkei.awesome.model.RelationShip;
import com.rikkei.awesome.model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ListFriendFragment extends Fragment {

    private ListFriendAdapter adapter;
    private List<User> mListFiend;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("relationships");
        String myID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mListFiend = new ArrayList<>();
        myRef.orderByChild("status").equalTo(RelationShip.FRIEND).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                RelationShip relationShip = snapshot.getValue(RelationShip.class);
                if (relationShip==null || (!relationShip.getUser1().getId().equals(myID)
                        && !relationShip.getUser2().getId().equals(myID)))
                    return;
                if (relationShip.getUser1().getId().equals(myID)) {
                    mListFiend.add(relationShip.getUser2());
                }
                if (relationShip.getUser2().getId().equals(myID)) {
                    mListFiend.add(relationShip.getUser1());
                }
                sortByName();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                RelationShip relationShip = snapshot.getValue(RelationShip.class);
                if (relationShip==null || (!relationShip.getUser1().getId().equals(myID)
                        && !relationShip.getUser2().getId().equals(myID)))
                    return;
                if (relationShip.getUser1().getId().equals(myID)) {
                    boolean exist = false;
                    for (int i=0; i<mListFiend.size(); i++) {
                        if (mListFiend.get(i).getId().equals(relationShip.getUser2().getId())) {
                            mListFiend.set(i, relationShip.getUser2());
                            exist = true;
                            break;
                        }
                    }
                    if (!exist) mListFiend.add(relationShip.getUser2());
                }
                if (relationShip.getUser2().getId().equals(myID)) {
                    boolean exist = false;
                    for (int i=0; i<mListFiend.size(); i++) {
                        if (mListFiend.get(i).getId().equals(relationShip.getUser1().getId())) {
                            mListFiend.set(i, relationShip.getUser1());
                            exist = true;
                            break;
                        }
                    }
                    if (!exist) mListFiend.add(relationShip.getUser1());
                }
                sortByName();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                RelationShip relationShip = snapshot.getValue(RelationShip.class);
                if (!relationShip.getUser1().getId().equals(myID) && !relationShip.getUser2().getId().equals(myID))
                    return;
                for (int i=0; i<mListFiend.size(); i++) {
                    if (mListFiend.get(i).getId().equals(relationShip.getUser1().getId()) ||
                            mListFiend.get(i).getId().equals(relationShip.getUser2().getId())) {
                        mListFiend.remove(i);
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
        return inflater.inflate(R.layout.fragment_list_friend,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        adapter = new ListFriendAdapter(getContext(),mListFiend);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    private void sortByName() {
        Collections.sort(mListFiend, new Comparator<User>() {
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
