package com.rikkei.awesome.ui.home.message;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.rikkei.awesome.R;
import com.rikkei.awesome.adapter.ListFriendCreateRoomAdapter;
import com.rikkei.awesome.model.RelationShip;
import com.rikkei.awesome.model.User;
import com.rikkei.awesome.utils.FirebaseQuery;
import com.rikkei.awesome.utils.GlideApp;
import com.rikkei.awesome.utils.ItemClickSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class CreateRoomChatFragment extends Fragment {

    Context context;
    String Uid, Fid, roomid;
    View view;
    ConstraintLayout selection_block;
    TextView btnCancel;
    ImageView btnBack;
    RecyclerView all_user_list;
    final List<User> listFriend = new ArrayList<>();
    CircleImageView friend_selected;
    MaterialButton btn_cancel_selection;
    ImageButton btn_create_roomchat;

    public CreateRoomChatFragment(Context context, String uid, String roomid) {
        this.context = context;
        Uid = uid;
        this.roomid = roomid;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_create_roomchat, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();

        selection_block.setVisibility(View.GONE);

        btnCancel.setOnClickListener(onBackClickListener);

        btnBack.setOnClickListener(onBackClickListener);

        btn_cancel_selection.setOnClickListener(v -> selection_block.setVisibility(View.GONE));

        btn_create_roomchat.setOnClickListener(createRoomListener);

        getAllFriendList();
    }

    private final View.OnClickListener onBackClickListener = v -> getActivity().getSupportFragmentManager().popBackStack();

    private void init() {
        selection_block= view.findViewById(R.id.selection_block);
        btnCancel = view.findViewById(R.id.btn_cancel);
        btnBack = view.findViewById(R.id.btn_back);
        all_user_list = view.findViewById(R.id.all_user_list);
        friend_selected = view.findViewById(R.id.friend_selected);
        btn_cancel_selection = view.findViewById(R.id.btn_cancel_selection);
        btn_create_roomchat = view.findViewById(R.id.btn_create_roomchat);
    }

    void getAllFriendList(){
        FirebaseQuery.getAllFriend(Uid, new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listFriend.clear();
                GenericTypeIndicator<HashMap<String, RelationShip>> objGTI = new GenericTypeIndicator<HashMap<String, RelationShip>>() {
                    @Override
                    public int hashCode() {
                        return super.hashCode();
                    }
                };
                Map<String, RelationShip> objHM = snapshot.getValue(objGTI);
                final List<RelationShip> relationShips = new ArrayList<>(objHM.values());
                for (RelationShip relationShip: relationShips){
                    if (relationShip==null || (!relationShip.getUser1().getId().equals(Uid)
                            && !relationShip.getUser2().getId().equals(Uid)))
                        continue;
                    else if (relationShip.getUser1().getId().equals(Uid)){
                        listFriend.add(relationShip.getUser2());
                    } else if (relationShip.getUser2().getId().equals(Uid))
                        listFriend.add(relationShip.getUser1());
                }
                all_user_list.setAdapter(new ListFriendCreateRoomAdapter(context, listFriend));
                all_user_list.setLayoutManager(new LinearLayoutManager(context));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ItemClickSupport.addTo(all_user_list).setOnItemClickListener((recyclerView, position, v) -> {
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference myRef = storage.getReference(listFriend.get(position).getAvatar());
            Fid = listFriend.get(position).getId();
            selection_block.setVisibility(View.VISIBLE);
            GlideApp.with(context).load(myRef).into(friend_selected);
        });
    }

    private final View.OnClickListener createRoomListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FirebaseQuery.createRoomchat(Uid, Fid, roomid, System.currentTimeMillis(), context, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                }
            });
            getActivity().getSupportFragmentManager().popBackStack();
        }
    };
}
