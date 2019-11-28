package com.example.planningpoker2;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

public class SignInFragment extends Fragment {


    private EditText mEmail, mPassword;
    private Button btnLogin;
    private TextView mRegister;
    private CheckBox mShowPassword;
    FirebaseAuth mFireBaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    public SignInFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        btnLogin = view.findViewById(R.id.btnLogin);
        mFireBaseAuth = FirebaseAuth.getInstance();
        mEmail = view.findViewById(R.id.et_email_login);
        mPassword = view.findViewById(R.id.et_password_login);
        mShowPassword = view.findViewById(R.id.cb_show_password);
        mRegister = view.findViewById(R.id.tv_register);
        checkShowPassword();
        authStateListener();

        enableBackArrow(false);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableBackArrow(true);
                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();
                //login(email,password);
              
                MainActivity.mFragmentManager.beginTransaction().replace(R.id.fragment_container, new AddGroupsFragment(),null).commit();
            }
        });

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableBackArrow(true);
                MainActivity.mFragmentManager.beginTransaction().replace(
                        R.id.fragment_container, new RegisterFragment(),null).commit();
            }
        });
        return view;
    }

    public void login(String email, String password){
        if (email.isEmpty()){
            mEmail.setError("Please enter the email address");
            mEmail.requestFocus();
        }
        else if (password.isEmpty()){
            mPassword.setError("Please enter your password");
            mPassword.requestFocus();
        }
        else if (!(email.isEmpty()) && !(password.isEmpty())){
            mFireBaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()){
                        Toast.makeText(getActivity(), "Please check your email or password", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(getActivity(), "Login Success", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }else{
            Toast.makeText(getActivity(), "Error occured", Toast.LENGTH_SHORT).show();
        }
    }

    public void checkShowPassword(){
        mPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        mShowPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    mPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else {
                    mPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

    public void authStateListener(){

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFireBaseAuth.getCurrentUser();
                if (mFirebaseUser != null){
                    Toast.makeText(getActivity(), "You are logged in", Toast.LENGTH_LONG).show();
                    MainActivity.mFragmentManager.beginTransaction().replace(R.id.fragment_container, new GroupsFragment(),null).commit();
                }
                else{
                    Toast.makeText(getActivity(), "Please log in", Toast.LENGTH_LONG).show();
                }
            }
        };
    }

    private void enableBackArrow(boolean enable){
        ActionBar supportActionBar = ((MainActivity) getActivity()).getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(enable);
        supportActionBar.setDisplayHomeAsUpEnabled(enable);
        supportActionBar.setDisplayShowHomeEnabled(enable);
    }
}
