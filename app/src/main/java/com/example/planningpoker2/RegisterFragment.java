package com.example.planningpoker2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterFragment extends Fragment {

    private EditText mEmail, mPassword, mFullName;
    private Button btnSignUp;
    private TextView mBack;
    FirebaseAuth mFireBaseAuth;
    public RegisterFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        btnSignUp = view.findViewById(R.id.btnSignUp);
        mFireBaseAuth = FirebaseAuth.getInstance();
        mEmail = view.findViewById(R.id.et_email);
        mPassword = view.findViewById(R.id.et_password);
        mFullName = view.findViewById(R.id.et_fullname);
        mBack = view.findViewById(R.id.tv_back_to_login);


        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new SignInFragment(),null).commit();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();
                String fullname = mFullName.getText().toString();
                FirebaseUser currentUser = mFireBaseAuth.getCurrentUser();

                checkIfFieldIsEmpty(email, password,fullname);
            }
        });
        return view;
    }

    public void checkIfFieldIsEmpty(final String email, final String password, final String fullname){
        if (fullname.isEmpty()){
            mFullName.setError("Please enter the full name");
            mFullName.requestFocus();
        }
        else if (email.isEmpty()){
            mEmail.setError("Please enter the email address");
            mEmail.requestFocus();
        }
        else if (password.isEmpty()){
            mPassword.setError("Please enter your password");
            mPassword.requestFocus();
        }
        else if (!(email.isEmpty()) && !(password.isEmpty())){
            mFireBaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful()){
                        Toast.makeText(getActivity(), "Sign in Unsuccessful. Please try again", Toast.LENGTH_LONG).show();
                    }else{
                        User user = new User(
                                fullname,
                                email
                        );
                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (!task.isSuccessful()){
                                    Toast.makeText(getActivity(),"Insert to database unsuccessful", Toast.LENGTH_LONG).show();
                                }
                                else{
                                    Toast.makeText(getActivity(), "Login in success", Toast.LENGTH_LONG).show();
                                    //MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new NewScoringFragment(),null).commit();
                                }
                            }
                        });
                    }
                }
            });
        }else{
            Toast.makeText(getActivity(), "Error occured", Toast.LENGTH_SHORT).show();
        }
    }
}
