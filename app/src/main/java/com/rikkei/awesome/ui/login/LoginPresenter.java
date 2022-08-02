package com.rikkei.awesome.ui.login;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.rikkei.awesome.MainActivity;
import com.rikkei.awesome.model.User;

import java.util.regex.Pattern;

public class LoginPresenter {

    private LoginInterface mLoginInterface;
    private MainActivity mMainActivity;

    public LoginPresenter(MainActivity mainActivity, LoginInterface mLoginInterface) {
        this.mLoginInterface = mLoginInterface;
        this.mMainActivity = mainActivity;
    }

    public void login(String email, String password){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(mMainActivity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mLoginInterface.loginSuccessful(mAuth.getUid());
                        } else {
                           mLoginInterface.loginFailed();
                        }
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
