package com.rikkei.awesome.ui.signup;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.TextInputLayout;
import com.rikkei.awesome.MainActivity;
import com.rikkei.awesome.R;
import com.rikkei.awesome.model.User;
import com.rikkei.awesome.ui.main.MainFragment;

public class SignupFragment extends Fragment implements SignupInterface{

    private ImageView imageBack;
    private TextInputLayout txtFullName, txtEmail, txtPassword;
    private CheckBox checkBoxLegal;
    private Button btnRegister;
    private TextView txtLogin, txtAlert;
    private SignupPresenter mSignupPresenter;
    Context context;

    public SignupFragment() {
    }

    public SignupFragment(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSignupPresenter = new SignupPresenter((MainActivity) getActivity(),this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageBack = view.findViewById(R.id.imageBack);
        txtFullName = view.findViewById(R.id.txtFullName);
        txtEmail = view.findViewById(R.id.txtEmail);
        txtPassword = view.findViewById(R.id.txtPassword);
        checkBoxLegal = view.findViewById(R.id.checkBoxLegal);
        btnRegister = view.findViewById(R.id.btnRegister);
        txtLogin = view.findViewById(R.id.txtLogin);
        txtAlert = view.findViewById(R.id.txtAlert);
        addEvents();
    }

    private void addEvents() {
        FragmentManager manager = getParentFragmentManager();
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manager.popBackStack();
            }
        });
        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manager.popBackStack();
            }
        });

        txtFullName.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (txtEmail.getEditText().getText().toString().trim().length() > 0 &&
                        txtPassword.getEditText().getText().toString().trim().length() > 0 &&
                        checkBoxLegal.isChecked()) {
                    btnRegister.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (txtFullName.getEditText().getText().toString().trim().length() == 0||
                        txtEmail.getEditText().getText().toString().trim().length() == 0 ||
                        txtPassword.getEditText().getText().toString().trim().length() == 0 ||
                        !checkBoxLegal.isChecked()) {
                    btnRegister.setEnabled(false);
                }
            }
        });
        txtEmail.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (txtPassword.getEditText().getText().toString().trim().length() > 0 && 
                        txtFullName.getEditText().getText().toString().trim().length() > 0 && 
                        checkBoxLegal.isChecked()) {
                    btnRegister.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (txtFullName.getEditText().getText().toString().trim().length() == 0||
                        txtEmail.getEditText().getText().toString().trim().length() == 0 ||
                        txtPassword.getEditText().getText().toString().trim().length() == 0 ||
                        !checkBoxLegal.isChecked()) {
                    btnRegister.setEnabled(false);
                }
            }
        });
        txtPassword.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (txtEmail.getEditText().getText().toString().trim().length() > 0 && 
                        txtFullName.getEditText().getText().toString().trim().length() > 0 && 
                        checkBoxLegal.isChecked()) {
                    btnRegister.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (txtFullName.getEditText().getText().toString().trim().length() == 0||
                        txtEmail.getEditText().getText().toString().trim().length() == 0 ||
                        txtPassword.getEditText().getText().toString().trim().length() == 0 ||
                        !checkBoxLegal.isChecked()) {
                    btnRegister.setEnabled(false);
                }
            }
        });
        checkBoxLegal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!checkBoxLegal.isChecked()) {
                    btnRegister.setEnabled(false);
                } else {
                    if (checkBoxLegal.isChecked() && 
                            txtFullName.getEditText().getText().toString().trim().length()>0 && 
                            txtEmail.getEditText().getText().toString().trim().length()>0 &&
                            txtPassword.getEditText().getText().toString().trim().length()>0)
                        btnRegister.setEnabled(true);
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullName = txtFullName.getEditText().getText().toString().trim();
                String email = txtEmail.getEditText().getText().toString().trim();
                String password = txtPassword.getEditText().getText().toString().trim();
                if (!mSignupPresenter.checkValidateEmail(email)) {
                    txtAlert.setText("Email incorrect!");
                    return;
                }
                if (!mSignupPresenter.checkValidatePassword(password)) {
                    txtAlert.setText("Password incorrect!");
                    return;
                }

                User user = new User();
                user.setFullName(fullName);
                user.setEmail(email);
                user.setPassword(password);
                mSignupPresenter.signup(user);
            }
        });
    }

    @Override
    public void signupSuccessful() {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.home_container,new MainFragment(context),"fragment_main");
        transaction.addToBackStack("fragment_main");
        transaction.commit();
    }

    @Override
    public void signupFailed() {
        txtAlert.setText("Register failed!");
    }
}
