package com.rikkei.awesome.ui.splash;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.rikkei.awesome.R;
import com.rikkei.awesome.ui.login.LoginFragment;
import com.rikkei.awesome.ui.main.MainFragment;

public class SplashFragment extends Fragment implements SplashInterface{

    private SplashPresenter mSplashPresenter;
    private FragmentTransaction transaction;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        transaction = getParentFragmentManager().beginTransaction();
        mSplashPresenter = new SplashPresenter(this);
        mSplashPresenter.checkLogin();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash,container,false);
    }

    @Override
    public void isLogin() {
        transaction.replace(R.id.home_container,new MainFragment(),"fragment_main");
        transaction.addToBackStack("fragment_main");
        transaction.commit();
    }

    @Override
    public void notLogin() {
        transaction.replace(R.id.home_container,new LoginFragment(),"fragment_login");
        transaction.addToBackStack("fragment_login");
        transaction.commit();
    }

}
