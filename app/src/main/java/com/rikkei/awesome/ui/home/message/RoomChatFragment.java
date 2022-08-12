package com.rikkei.awesome.ui.home.message;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.rikkei.awesome.R;
import com.rikkei.awesome.adapter.ImageAdapter;
import com.rikkei.awesome.adapter.MessageAdapter;
import com.rikkei.awesome.model.Member;
import com.rikkei.awesome.model.Message;
import com.rikkei.awesome.model.User;
import com.rikkei.awesome.utils.FirebaseQuery;
import com.rikkei.awesome.utils.GlideApp;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class RoomChatFragment extends Fragment{

    TextView txt_name_room_chat;
    ImageView btnBack, btn_sent_message;
    TextInputLayout txt_send_message;
    CircleImageView avatar;
    ImageButton btnImage;
    RecyclerView recyclerView, imageList;
    View view;
    Context context;
    MaterialButton btnSend;
    String Uid, roomID, friendName;
    private String Fid;
    private final List<Message> messageList = new ArrayList<>();
    private final List<File> imageListFile = new ArrayList<>();
    private User user1 = new User();
    private User user2 = new User();
    private final List<Member> members = new ArrayList<>();
    private SparseBooleanArray sparseBooleanArray;
    private ImageAdapter imageAdapter;
    private final List<File> listImageSelected = new ArrayList<>();

    public RoomChatFragment(Context context,  String roomID, String Uid){
        this.context = context;
        this.roomID = roomID;
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
        InitRoomChat(Uid, friendName);
        getListMessage(recyclerView);

        btnImage.setOnClickListener(v -> {
            imageList.setVisibility(View.VISIBLE);
            getListImage(imageList);
            ViewGroup.LayoutParams params = recyclerView.getLayoutParams();
            params.height = 300;
            recyclerView.setLayoutParams(params);
            btnSend.setVisibility(View.VISIBLE);
        });

        btnSend.setOnClickListener(v -> {
            sendPicture();
            btnSend.setVisibility(View.GONE);
            imageList.setVisibility(View.GONE);

        });

        btn_sent_message.setOnClickListener(v -> {
            String text = txt_send_message.getEditText().getText().toString().trim();
            if (text.isEmpty())
                return;

            txt_send_message.getEditText().setText("");
            btn_sent_message.setVisibility(View.GONE);
            sendMessage(text);
        });

        btnBack.setOnClickListener(v -> {
            getFragmentManager().popBackStack();
        });

        txt_send_message.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                btn_sent_message.setVisibility(View.VISIBLE);
                imageList.setVisibility(View.GONE);
                ViewGroup.LayoutParams params = recyclerView.getLayoutParams();
                params.height = ViewGroup.LayoutParams.MATCH_PARENT;
                recyclerView.setLayoutParams(params);
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

        InputMethodManager inputMethodManager = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0); // tu tat ban phim khi thoat khung nhạp


    }

    void Init(){
        btnBack = view.findViewById(R.id.btn_back_room_chat);
        btn_sent_message = view.findViewById(R.id.btn_send_message);
        txt_send_message = view.findViewById(R.id.txt_send_message);
        txt_name_room_chat = view.findViewById(R.id.txt_name_room_chat);
        avatar = view.findViewById(R.id.img_avatar_friend);
        imageList = view.findViewById(R.id.image_list);
        btnSend = view.findViewById(R.id.btn_send_picture);
        btnImage = view.findViewById(R.id.btn_picture);

        btn_sent_message.setVisibility(View.GONE);
        imageList.setVisibility(View.GONE);
        recyclerView = view.findViewById(R.id.message_list);
        btnSend.setVisibility(View.GONE);

        ViewGroup.LayoutParams params = recyclerView.getLayoutParams();
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        recyclerView.setLayoutParams(params);
    }


    public void onStart() {
        super.onStart();
    }

    public void setTitle(String friendName) {
        txt_name_room_chat.setText(friendName);
    }

    public void setAvatar(StorageReference url) {
        GlideApp.with(context).load(url).into(avatar);
    }

    public void openButton() {

    }

    public void getListMessage(RecyclerView recyclerView){
        FirebaseQuery.getListMessage(roomID, new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Message message = snapshot.getValue(Message.class);
                messageList.add(message);
                recyclerView.smoothScrollToPosition(messageList.size());
                recyclerView.setAdapter(new MessageAdapter(context, messageList, Uid, user2, roomID));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    } //lay danh sach tin nhan

    public void InitRoomChat(String Uid, String friendName) { //nhap dau vao phong chat gom danh sach thanh vien, thông tin thanh vien,
        FirebaseQuery.getListMember(roomID, new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GenericTypeIndicator<HashMap<String, Member>> objGTI = new GenericTypeIndicator<HashMap<String, Member>>() {
                    @Override
                    public boolean equals(@Nullable Object obj) {
                        return super.equals(obj);
                    }
                };
                Map<String, Member> objHM = snapshot.getValue(objGTI);
                final List<Member> objAL = new ArrayList<>(objHM.values());
                for (Member tmp: objAL)
                    if (tmp.getId().equals(roomID))
                        members.add(tmp);

                if (members.get(0).getUser1().equals(Uid)) Fid = members.get(0).getUser2();
                else Fid = members.get(0).getUser1();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
                if (objHM != null){
                    final List<User> objAL = new ArrayList<>(objHM.values());
                    for (User tmp : objAL) {
                        if (tmp.getId().equals(Fid)) {user2 = tmp;
                            StorageReference mRef = FirebaseStorage.getInstance().getReference(user2.getAvatar());
                            setAvatar(mRef);
                            setTitle(user2.getFullName());}
                        if (tmp.getId().equals(Uid)) user1 = tmp;
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void sendMessage(String text){ //gui tin nhan van ban
        FirebaseQuery.sendMessage(roomID, text, Uid, System.currentTimeMillis(), (error, ref) -> {
        }, "message" + (messageList.size() + 1));
    }

    public void getListImage(RecyclerView recyclerView){ // lay hinh anh
        Dexter.withContext(context).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, // xin quyen truy cap vao bo nho trong
                Manifest.permission.WRITE_EXTERNAL_STORAGE).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) { //neu nhu nguoi dung dong y cap quyen
                displayImage(recyclerView);
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }

    public ArrayList<File> findImage(File file){ //tim kiem toan bo hinh anh trong bo nho trong
        ArrayList<File> arrayList = new ArrayList<>();
        File[] files = file.listFiles();

        if (files != null) {
            for (File tmp: files){
                if (tmp.isDirectory() && !tmp.isHidden()){
                    arrayList.addAll(findImage(tmp));
                } else if (tmp.getName().endsWith(".jpg") || tmp.getName().endsWith("png") || tmp.getName().endsWith(".jpeg")){
                    arrayList.add(tmp);
                }
            }
        }
        return arrayList;
    }

    public void displayImage(RecyclerView recyclerView){ //hien thi hinh anh
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
        imageListFile.clear();
        imageListFile.addAll(findImage(Environment.getExternalStorageDirectory())); // lay toan bo anh trong bo nho trong
        imageAdapter = new ImageAdapter(context, imageListFile);
        sparseBooleanArray = imageAdapter.getImageStateArray();
        recyclerView.setAdapter(imageAdapter);
    }

    public void sendPicture(){
        listImageSelected.addAll(imageAdapter.getSelected());
        for (File file: listImageSelected){
            FirebaseQuery.sendImage(roomID, file, Uid, System.currentTimeMillis(), (error, ref) -> {
            }, "message" + (messageList.size() + 1), context);
        }
    }
}
