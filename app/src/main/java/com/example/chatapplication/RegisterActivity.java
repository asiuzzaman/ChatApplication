package com.example.chatapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    private Button registerButton;
    private EditText UserEmail,UserPassword;
    private TextView alreadyAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        InitializeFields();
        alreadyAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendUserToTheLoginActivity();
            }
        });
    }

    private void SendUserToTheLoginActivity() {
        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
    }

    private void InitializeFields() {
        registerButton=findViewById(R.id.register_button);
        UserEmail=findViewById(R.id.register_email);
        UserPassword=findViewById(R.id.register_password);
        alreadyAccount=findViewById(R.id.already_have_account);
    }
}