package com.rikkei.awesome.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.rikkei.awesome.R;

public class UpdateInformationFragment extends Fragment {

    View view;
    Context context;
    ImageView btn_back, btn_change_avatar;
    BottomNavigationView navBottom;
    TextView btn_save;


    public UpdateInformationFragment() {
    }



    public UpdateInformationFragment(Context context, BottomNavigationView navBottom) {
        this.context = context;
        this.navBottom = navBottom;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_update_information, container, false);

        init();

        btn_back.setOnClickListener(imgListener);
        btn_save.setOnClickListener(txtListenner);
        btn_change_avatar.setOnClickListener(avaListener);

        return view;
    }

    void init(){
        btn_back = view.findViewById(R.id.btn_back);
        btn_save = view.findViewById(R.id.btn_save);
        btn_change_avatar = view.findViewById(R.id.btn_change_avatar);
    }

    private View.OnClickListener imgListener = v -> {
        //bat su kien quay lai
        getFragmentManager().popBackStack();
        navBottom.setVisibility(View.VISIBLE);
    };

    private View.OnClickListener txtListenner = v -> {
        //bat su kien luu thay doi
    };

    private View.OnClickListener avaListener = v -> {
        //bat su kien doi avatar
    };
}
