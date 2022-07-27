package com.rikkei.awesome.ui.splash;

import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashPresenter {

    private SplashInterface mSplashInterface;

    public SplashPresenter(SplashInterface mSplashInterface) {
        this.mSplashInterface = mSplashInterface;
    }

    public void checkLogin() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user == null) {
                    mSplashInterface.notLogin();
                } else {
                    mSplashInterface.isLogin();
                }
            }
        },2000);
    }
}
