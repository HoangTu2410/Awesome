package com.rikkei.awesome.ui.home.friend;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rikkei.awesome.R;
import com.rikkei.awesome.adapter.FriendHomeAdapter;
import com.rikkei.awesome.model.RelationShip;
import com.rikkei.awesome.model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FriendHomeFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private SearchView searchView;
    private TextView txtCancel;
    private List<User> mListUser;
    private List<RelationShip> mListRelationship;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_friend_home,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabLayout = view.findViewById(R.id.tab_layout_friend);
        viewPager = view.findViewById(R.id.view_pager_friend);
        searchView = view.findViewById(R.id.searchView);
        txtCancel = view.findViewById(R.id.txtCancel);

        mListUser = new ArrayList<>();
        mListRelationship = new ArrayList<>();

        FriendHomeAdapter adapter = new FriendHomeAdapter(this);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("BẠN BÈ");
                        break;
                    case 1:
                        tab.setText("TẤT CẢ");
                        break;
                    case 2:
                        tab.setText("YÊU CẦU");
                        break;
                }
            }
        }).attach();

        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                txtCancel.setVisibility(TextView.VISIBLE);
            }
        });

        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtCancel.setVisibility(TextView.GONE);
            }
        });
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        String myId = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference refUser = database.getReference("users");
//        refUser.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                User user = snapshot.getValue(User.class);
//                if (user != null && !user.getId().equals(myId)) {
//                    mListUser.add(user);
//                    sortByName();
//                }
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                User user = snapshot.getValue(User.class);
//                if (user == null) {
//                    return;
//                }
//
//                for (int i=0; i< mListUser.size(); i++) {
//                    if (user.getId().equals(mListUser.get(i).getId())) {
//                        mListUser.set(i,user);
//                        break;
//                    }
//                }
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//                User user = snapshot.getValue(User.class);
//                if (user == null) {
//                    return;
//                }
//
//                for (int i=0; i< mListUser.size(); i++) {
//                    if (user.getId().equals(mListUser.get(i).getId())) {
//                        mListUser.remove(i);
//                        break;
//                    }
//                }
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//        DatabaseReference refRelationship = database.getReference("relationships");
//        refRelationship.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                RelationShip relationShip = snapshot.getValue(RelationShip.class);
//                if (relationShip != null && (relationShip.getIdUserSend().equals(myId) ||
//                        relationShip.getIdUserReceived().equals(myId))) {
//                    mListRelationship.add(relationShip);
//                }
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                RelationShip relationShip = snapshot.getValue(RelationShip.class);
//                if (relationShip == null) {
//                    return;
//                }
//
//                for (int i=0; i< mListRelationship.size(); i++) {
//                    if (relationShip.getIdUserSend().equals(mListRelationship.get(i).getIdUserSend()) &&
//                            relationShip.getIdUserReceived().equals(mListRelationship.get(i).getIdUserReceived())) {
//                        mListRelationship.set(i,relationShip);
//                        break;
//                    }
//                }
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//                RelationShip relationShip = snapshot.getValue(RelationShip.class);
//
//                for (int i=0; i< mListRelationship.size(); i++) {
//                    if (relationShip.getIdUserSend().equals(mListRelationship.get(i).getIdUserSend()) &&
//                            relationShip.getIdUserReceived().equals(mListRelationship.get(i).getIdUserReceived())) {
//                        mListRelationship.remove(i);
//                        break;
//                    }
//                }
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }

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
