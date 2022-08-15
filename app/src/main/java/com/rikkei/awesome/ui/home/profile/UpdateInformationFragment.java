package com.rikkei.awesome.ui.home.profile;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
import com.rikkei.awesome.GlideApp;
import com.rikkei.awesome.MainActivity;
import com.rikkei.awesome.R;
import com.rikkei.awesome.model.User;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class UpdateInformationFragment extends Fragment implements ImageResultListener{

    private User mAccount;
    private ImageView btnBack, btnChangeAvatar;
    private TextView btnSave;
    private CircleImageView avatarProfile;
    private TextInputLayout txtFullName, txtPhoneNumber, txtDob;
    private Uri avatarUri;

    public UpdateInformationFragment(User mAccount) {
        this.mAccount = mAccount;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_update_information,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnBack = view.findViewById(R.id.btn_back);
        btnChangeAvatar= view.findViewById(R.id.btn_change_avatar);
        btnSave = view.findViewById(R.id.btn_save);
        avatarProfile = view.findViewById(R.id.avatar_profile);
        txtFullName = view.findViewById(R.id.txt_fullName);
        txtPhoneNumber = view.findViewById(R.id.txt_phone_number);
        txtDob = view.findViewById(R.id.txt_dob);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        btnChangeAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPermission();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = txtFullName.getEditText().getText().toString().trim();
                String phone = txtPhoneNumber.getEditText().getText().toString().trim();
                String dob = txtDob.getEditText().getText().toString().trim();
                if (!checkValidateName(name)) {
                    txtFullName.setError("Tên không được để trống!");
                    return;
                }
                mAccount.setFullName(name);
                if (!checkValidatePhoneNumber(phone)) {
                    txtPhoneNumber.setError("Số điện thoại không đúng!");
                    return;
                }
                mAccount.setPhoneNumber(phone);
                if (!checkValidateDOB(dob)) {
                    txtDob.setError("Ngày sinh không đúng!");
                    return;
                }
                mAccount.setDob(dob);
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("users");

                if (avatarUri == null ) {
                    myRef.child(mAccount.getId()).setValue(mAccount);
                    Snackbar.make(((MainActivity)getActivity()).getLayoutActivity(),"Update information success!",Snackbar.LENGTH_LONG).show();
                    getActivity().getSupportFragmentManager().popBackStack();
                } else {
                    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
                    String path = "images/avatars/"+mAccount.getId()+"_"+System.currentTimeMillis();
                    ProgressDialog progressDialog = new ProgressDialog(getContext());
                    progressDialog.setMessage("Updating......");
                    progressDialog.show();
                    storageReference.child(path).putFile(avatarUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            mAccount.setAvatar(path);
                            myRef.child(mAccount.getId()).setValue(mAccount);
                            progressDialog.dismiss();
                            Snackbar.make(((MainActivity)getActivity()).getLayoutActivity(),"Update information success!",Snackbar.LENGTH_LONG).show();
                            getActivity().getSupportFragmentManager().popBackStack();
                        }
                    });
                }
            }
        });
        StorageReference imgRef = FirebaseStorage.getInstance().getReference(mAccount.getAvatar());
        GlideApp.with(getContext()).load(imgRef).into(avatarProfile);
        txtFullName.getEditText().setText(mAccount.getFullName());
        txtPhoneNumber.getEditText().setText(mAccount.getPhoneNumber());
        txtDob.getEditText().setText(mAccount.getDob());
    }

    private void requestPermission() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                ImagePicker.with(getActivity()).crop().start();
                ((MainActivity)getActivity()).setImageResultListener(UpdateInformationFragment.this);
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
            }
        };

        TedPermission.create()
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();
    }

    private boolean checkValidateName(String name) {
        return (name!=null && !name.isEmpty());
    }

    private boolean checkValidatePhoneNumber(String phoneNumber) {
        String regexPattern = "[0-9]{10,11}";
        return Pattern.compile(regexPattern).matcher(phoneNumber).matches();
    }

    private boolean checkValidateDOB(String dob) {
        String regexPattern = "((0[1-9]|[12]\\d|3[01])/(0[1-9]|1[0-2])/[12]\\d{3})";
        return Pattern.compile(regexPattern).matcher(dob).matches();
    }

    @Override
    public void onResultImage(Uri uri) {
        try {
            avatarProfile.setImageURI(uri);
            avatarUri = uri;
        }
        catch (Exception e){
        }
    }
}
