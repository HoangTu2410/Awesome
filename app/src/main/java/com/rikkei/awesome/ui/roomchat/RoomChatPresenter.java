package com.rikkei.awesome.ui.roomchat;

import android.Manifest;
import android.content.Context;
import android.os.Environment;
import android.util.SparseBooleanArray;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.rikkei.awesome.adapter.ImageAdapter;
import com.rikkei.awesome.adapter.MessageAdapter;
import com.rikkei.awesome.model.Member;
import com.rikkei.awesome.model.Message;
import com.rikkei.awesome.model.User;
import com.rikkei.awesome.ui.message.MessagePresenter;
import com.rikkei.awesome.utils.FirebaseQuery;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoomChatPresenter {
    private final RoomChatInterface roomChatInterface;
    private final Context context;
    private final List<Message> messageList = new ArrayList<>();
    private final List<File> imageList = new ArrayList<>();
    private final String roomID, Uid;
    private String Fid;
    private User user1 = new User();
    private User user2 = new User();
    private final List<Member> members = new ArrayList<>();
    private  Member member;
    private Message message;
    private SparseBooleanArray sparseBooleanArray;
    private ImageAdapter imageAdapter;
    private final List<File> listImageSelected = new ArrayList<>();



    public RoomChatPresenter(RoomChatInterface roomChatInterface, Context context, String roomID, String uid) {
        this.roomChatInterface = roomChatInterface;
        this.context = context;
        this.roomID = roomID;
        this.Uid = uid;
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
                            roomChatInterface.setAvatar(mRef);
                            roomChatInterface.setTitle(user2.getFullName());}
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
        imageList.clear();
        imageList.addAll(findImage(Environment.getExternalStorageDirectory())); // lay toan bo anh trong bo nho trong
        imageAdapter = new ImageAdapter(context, imageList);
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
