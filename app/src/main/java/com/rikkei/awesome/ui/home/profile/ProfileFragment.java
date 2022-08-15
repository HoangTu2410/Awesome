package com.rikkei.awesome.ui.home.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.rikkei.awesome.GlideApp;
import com.rikkei.awesome.R;
import com.rikkei.awesome.model.User;
import com.rikkei.awesome.ui.login.LoginFragment;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    private CircleImageView mAvatar;
    private TextView tvFullName, tvEmail, txtLogout;
    private ImageView imgBackgroundProfile, imgUpdateProfile;
    private User mAccount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAvatar = view.findViewById(R.id.avatar_profile);
        tvFullName = view.findViewById(R.id.tv_profile_name);
        tvEmail = view.findViewById(R.id.tv_email_profile);
        imgBackgroundProfile = view.findViewById(R.id.img_background_profile);
        imgUpdateProfile = view.findViewById(R.id.img_update_profile);
        txtLogout = view.findViewById(R.id.txtLogout);

        loadProfile();

        imgUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.home_container,new UpdateInformationFragment(mAccount),"fragment_update").addToBackStack("fragment_update").commit();
            }
        });

        txtLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.home_container,new LoginFragment(), "fragment_login").commit();
            }
        });
    }

    private void loadProfile() {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mAccount = snapshot.getValue(User.class);
                tvFullName.setText(mAccount.getFullName());
                tvEmail.setText(mAccount.getEmail());
                StorageReference imgRef = FirebaseStorage.getInstance().getReference(mAccount.getAvatar());
                GlideApp.with(getContext()).load(imgRef).into(mAvatar);
                GlideApp.with(getContext()).load(imgRef).autoClone().into(imgBackgroundProfile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
