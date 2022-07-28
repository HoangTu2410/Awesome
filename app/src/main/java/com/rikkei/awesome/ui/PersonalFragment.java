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
import com.rikkei.awesome.BuildConfig;
import com.rikkei.awesome.R;
import com.rikkei.training.morkproject.awesomechat.BuildConfig;
import com.rikkei.training.morkproject.awesomechat.R;

public class PersonalFragment extends Fragment {

    View view;
    Context context;
    TextView tv_version_number;
    ImageView btn_update_profile;
    BottomNavigationView navBottom;

    public PersonalFragment(Context context) {
        this.context = context;
    }

    public PersonalFragment() {
    }

    public PersonalFragment(Context context, BottomNavigationView navBottom) {
        this.context = context;
        this.navBottom = navBottom;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_personal, container, false);
        Init();

        tv_version_number.setText(BuildConfig.VERSION_NAME);

        btn_update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container_3, new UpdateInformationFragment(context, navBottom)).addToBackStack(null).commit();
                navBottom.setVisibility(View.INVISIBLE);
            }
        });
        return view;
    }

    void Init(){
        btn_update_profile = view.findViewById(R.id.btn_update_profile);
        tv_version_number = view.findViewById(R.id.tv_version_number);
        navBottom.setVisibility(View.VISIBLE);
    }
}
