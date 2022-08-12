package com.rikkei.awesome.utils;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rikkei.awesome.R;
import com.rikkei.awesome.model.Member;
import com.rikkei.awesome.model.Message;
import com.rikkei.awesome.model.User;

import java.io.File;
import java.security.acl.Group;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FirebaseQuery<T> {

    public static final String USERS = "users";
    public static final String MEMBERS = "member";
    public static final String ROOMCHATS = "room_chat";
    public static final String MESSAGES = "message_text";
    public static final String description = "description";
    public static final String lastMessage = "lastMessage";
    public static final String sendBy = "sendBy";
    public static final String time = "time";

    public static String USERNAME = "";

    public static void getListRoomChatFirst(String username, ValueEventListener valueEventListener){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(ROOMCHATS);
        myRef.orderByChild("id").addValueEventListener(valueEventListener);//get all roomchat
    }
    public static void getListRoomChatLast(String username, ValueEventListener valueEventListener){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(ROOMCHATS);
        myRef.keepSynced(true);
        myRef.orderByKey().addValueEventListener(valueEventListener);//get all roomchat
    }

    public static void getListMessage(String path, ChildEventListener childEventListener){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(MESSAGES).child(path);
        myRef.keepSynced(true);
        myRef.orderByChild("time").addChildEventListener(childEventListener); // lay toan bo tin nhan sap xep theo thoi gian
    }

    public static void getUser(String Uid, ValueEventListener valueEventListener){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(USERS);
        myRef.orderByChild("id").equalTo(Uid).addListenerForSingleValueEvent(valueEventListener);
    }

    public static void getListUser(ValueEventListener valueEventListener){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(USERS);
        myRef.keepSynced(true);
        myRef.orderByChild("id").addValueEventListener(valueEventListener); // lay danh sach nguoi dung sap xep theo id
    }

    public static void getListMember(String Uid, ValueEventListener valueEventListener){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(MEMBERS);
        myRef.keepSynced(true); //giu dong bo
        myRef.addValueEventListener(valueEventListener); //lay danh sach thanh vien cua cac phong chat
    }

    public static void sendMessage(String roomId, String text, String userid, long currentTimeMillis, DatabaseReference.CompletionListener completionListener, String messID){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRefGroup = database.getReference(ROOMCHATS).child(roomId);

        Map<String, Object> writeMes = new HashMap<>();
        writeMes.put(description, "");
        writeMes.put("id", roomId);
        writeMes.put(lastMessage, text);
        writeMes.put(sendBy, userid);
        writeMes.put(time, Long.toString(currentTimeMillis));

        myRefGroup.updateChildren(writeMes);//ghi lai metadata cua phong chat
        myRefGroup.keepSynced(true);

        DatabaseReference myRefMes = database.getReference(MESSAGES).child(roomId);
        Message message = new Message(text, Long.toString(currentTimeMillis), userid);
        myRefMes.keepSynced(true);
        myRefMes.child(messID).setValue(message);//luu tin nhan vao phong chat
    }
    public static void sendImage(String roomId, File imageFile, String userid, long currentTimeMillis, DatabaseReference.CompletionListener completionListener, String messID, Context context){
        String text = "images/messages/" + roomId + "/" + imageFile.getName(); // duong link den file
        Uri file = Uri.fromFile(new File(imageFile.getAbsolutePath()));
        StorageReference myRefImg = FirebaseStorage.getInstance().getReference("images/messages").child(roomId+"/"+file.getLastPathSegment());
        StorageMetadata metadata = new StorageMetadata.Builder().setContentType("image/jpg").build();
        UploadTask uploadTask = myRefImg.putFile(file, metadata);
        Task<Uri> urlTask = uploadTask.continueWithTask(task -> {
            if (!task.isSuccessful()){
                throw task.getException();
            }
            return myRefImg.getDownloadUrl();
        }).addOnSuccessListener(uri -> {
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRefGroup = database.getReference(ROOMCHATS).child(roomId);

        String str = context.getString(R.string.LastMessageImage);
        Map<String, Object> writeMes = new HashMap<>();
        writeMes.put(description, "");
        writeMes.put("id", roomId);
        writeMes.put(lastMessage, str);
        writeMes.put(sendBy, userid);
        writeMes.put(time, Long.toString(currentTimeMillis));

        myRefGroup.updateChildren(writeMes);

        DatabaseReference myRefMes = database.getReference(MESSAGES).child(roomId);
        Message message = new Message(text, Long.toString(currentTimeMillis), userid);
        myRefMes.child(messID).setValue(message);
    }

    public static void createRoomchat(String userid, String friendid, String roomid, long currentTimeMillis, Context context){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRefRoom = database.getReference(ROOMCHATS);

        String str = context.getString(R.string.getGreeting);
        Map<String, Object> writeMes = new HashMap<>();
        writeMes.put(description, "");
        writeMes.put("id", roomid);
        writeMes.put(lastMessage, str);
        writeMes.put(sendBy, "");
        writeMes.put(time, Long.toString(currentTimeMillis));

        myRefRoom.child(roomid).setValue(writeMes);

        DatabaseReference myRefMem = database.getReference(MEMBERS);
        Member member = new Member(userid, friendid);
        myRefMem.child(roomid).setValue(member);
    }
}
