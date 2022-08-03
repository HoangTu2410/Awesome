package com.rikkei.awesome.ui.signup;

import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.rikkei.awesome.MainActivity;
import com.rikkei.awesome.model.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class SignupPresenter {

    private SignupInterface mSignupInterface;
    private MainActivity mMainActivity;

    public SignupPresenter(MainActivity mainActivity,SignupInterface mSignupInterface) {
        this.mSignupInterface = mSignupInterface;
        this.mMainActivity = mainActivity;
    }

    public void signup(User user) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(user.getEmail(),user.getPassword())
                .addOnCompleteListener(mMainActivity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            user.setId(mAuth.getCurrentUser().getUid());
                            addUserToDatabase(user);
                            mSignupInterface.signupSuccessful();
                        } else {
                            mSignupInterface.signupFailed();
                        }
                    }
                });
    }

    private void addUserToDatabase(User user) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");

        Date date = new Date();
        String dob = new SimpleDateFormat("dd/MM/yyyy").format(date);
        user.setDob(dob);
        user.setPhoneNumber("88888888");

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference pathReference = storage.getReference().child("images/avatars/default_avatar.png");
        pathReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String defaultAvatar = uri.toString();
                user.setAvatar(defaultAvatar);
                myRef.child(user.getId()).setValue(user);
            }
        });
    }

    public boolean checkValidateEmail(String email) {
        String regexPattern = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        return Pattern.compile(regexPattern).matcher(email).matches();
    }

    public boolean checkValidatePassword(String password) {
        String regexPattern = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,20})";
        return Pattern.compile(regexPattern).matcher(password).matches();
    }
}
