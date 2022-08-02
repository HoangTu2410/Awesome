package com.rikkei.awesome.ui.login;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.rikkei.awesome.MainActivity;
import com.rikkei.awesome.R;
import com.rikkei.awesome.model.User;
import com.rikkei.awesome.ui.main.MainFragment;
import com.rikkei.awesome.ui.signup.SignupFragment;

import java.util.regex.Pattern;

public class LoginFragment extends Fragment implements LoginInterface{

    private TextInputLayout txtEmail, txtPassword;
    private TextView txtForgotPassword, txtRegisterAccount, txtAlert;
    private Button btnLogin;
    private LoginPresenter mLoginPresenter;
    Context context;

    public LoginFragment(Context context) {
        this.context = context;
    }

    public LoginFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoginPresenter = new LoginPresenter((MainActivity) getActivity(),this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtEmail = view.findViewById(R.id.txtEmail);
        txtPassword = view.findViewById(R.id.txtPassword);
        txtForgotPassword = view.findViewById(R.id.txtForgotPassword);
        txtAlert = view.findViewById(R.id.txtAlert);
        btnLogin = view.findViewById(R.id.btnLogin);
        txtRegisterAccount = view.findViewById(R.id.txtRegisterAccount);
        addEvents();
    }

    private void addEvents() {
        txtForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Forgot password", Toast.LENGTH_SHORT).show();
            }
        });

        txtRegisterAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.home_container,new SignupFragment(context),"fragment_signup");
                transaction.addToBackStack("fragment_signup");
                transaction.commit();
            }
        });

        txtEmail.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (txtPassword.getEditText().getText().toString().trim().length() > 0) {
                    btnLogin.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (txtEmail.getEditText().getText().toString().trim().length() == 0 ||
                        txtPassword.getEditText().getText().toString().trim().length() == 0) {
                    btnLogin.setEnabled(false);
                }
            }
        });
        txtPassword.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (txtEmail.getEditText().getText().toString().trim().length() > 0) {
                    btnLogin.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (txtEmail.getEditText().getText().toString().trim().length() == 0 ||
                        txtPassword.getEditText().getText().toString().trim().length() == 0) {
                    btnLogin.setEnabled(false);
                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = txtEmail.getEditText().getText().toString().trim();
                String password = txtPassword.getEditText().getText().toString().trim();
                if (!mLoginPresenter.checkValidateEmail(email)) {
                    txtAlert.setText(R.string.emailIncorrect);
                    return;
                }
                if (!mLoginPresenter.checkValidatePassword(password)) {
                    txtAlert.setText(R.string.passwordIncorrect);
                    return;
                }
                mLoginPresenter.login(email, password);
            }
        });
    }

    @Override
    public void loginSuccessful(String UId) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.home_container,new MainFragment(context, UId),"fragment_main");
        transaction.addToBackStack("fragment_main");
        transaction.commit();
    }

    @Override
    public void loginFailed() {
        txtAlert.setText(R.string.loginFailed);
    }

}
