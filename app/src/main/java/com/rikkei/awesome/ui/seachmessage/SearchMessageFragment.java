package com.rikkei.awesome.ui.seachmessage;

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
import androidx.recyclerview.widget.RecyclerView;

import com.rikkei.awesome.R;


public class SearchMessageFragment extends Fragment implements SearchMessageInterface{

    View view;
    Context context;
    TextView btn_cancel;
    RecyclerView recyclerView;
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
        recyclerView = view.findViewById(R.id.recycler_search);

        block_no_results.setVisibility(View.GONE);
    }
}
