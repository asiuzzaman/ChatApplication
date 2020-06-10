package com.example.chatapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseUser currentUser;
    private Button loginButton,phoneLoginButton;
    private EditText UserEmail,UserPassword;
    private TextView NeedNewAccountLink,ForgetPasswordLink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        InitializeFields();
        NeedNewAccountLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendUserToTheRegisterActivity();
            }
        });
    }

    private void InitializeFields() {
        loginButton=findViewById(R.id.login_button);
        phoneLoginButton=findViewById(R.id.phone_login_button);
        UserEmail=findViewById(R.id.login_email);
        UserPassword=findViewById(R.id.login_password);
        NeedNewAccountLink=findViewById(R.id.login_using);
        ForgetPasswordLink=findViewById(R.id.forget_password_link);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (currentUser!=null){
            SendToTheMainActivity();
        }
    }

    private void SendToTheMainActivity() {
        startActivity(new Intent(LoginActivity.this,MainActivity.class));
    }
    private void SendUserToTheRegisterActivity(){
        startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
    }
}