package com.rikkei.awesome.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.rikkei.training.morkproject.awesomechat.R;

public class SearchMessageFragment extends Fragment {

    View view;
    Context context;
    TextView btn_cancel;
    RelativeLayout block_no_results;

    public SearchMessageFragment() {
    }

    public SearchMessageFragment(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search_message, container, false);

        init();

        btn_cancel.setOnClickListener(v -> {
            getFragmentManager().popBackStack();
        });

        return view;
    }

    void init(){
        btn_cancel = view.findViewById(R.id.btn_cancel_search_message);
        block_no_results  = view.findViewById(R.id.block_no_results);

        block_no_results.setVisibility(View.GONE);
    }
}
