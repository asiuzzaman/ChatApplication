package com.example.chatapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button loginButton,phoneLoginButton;
    private EditText UserEmail,UserPassword;
    private TextView NeedNewAccountLink,ForgetPasswordLink;
    private ProgressDialog loginLoading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth= FirebaseAuth.getInstance();

        InitializeFields();
        NeedNewAccountLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendUserToTheRegisterActivity();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllowUserToLogin();
            }
        });
    }

    private void AllowUserToLogin() {
        String email=UserEmail.getText().toString().trim();
        String password=UserPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)|| TextUtils.isEmpty(password)){
            Toast.makeText(LoginActivity.this,"Please Enter the fields Properly...",Toast.LENGTH_SHORT).show();
        }else {
            loginLoading.setTitle("Login into Account");
            loginLoading.setMessage("Please wait until login");
            loginLoading.setCanceledOnTouchOutside(true);
            loginLoading.show();

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(LoginActivity.this,"Login successfull",Toast.LENGTH_SHORT).show();
                        SendUserToTheMainActivity();
                        loginLoading.dismiss();
                    }else{
                        String Error=task.getException().toString();
                        Toast.makeText(LoginActivity.this,"Login Failed with Error "+Error,Toast.LENGTH_SHORT).show();
                        loginLoading.dismiss();
                    }
                }
            });
        }

    }

    private void InitializeFields() {
        loginButton=findViewById(R.id.login_button);
        phoneLoginButton=findViewById(R.id.phone_login_button);
        UserEmail=findViewById(R.id.login_email);
        UserPassword=findViewById(R.id.login_password);
        NeedNewAccountLink=findViewById(R.id.login_using);
        ForgetPasswordLink=findViewById(R.id.forget_password_link);
        loginLoading=new ProgressDialog(this);

    }

    private void SendUserToTheMainActivity() {
        Intent mainIntent=new Intent(LoginActivity.this,MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }
    private void SendUserToTheRegisterActivity(){
        startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
    }
}