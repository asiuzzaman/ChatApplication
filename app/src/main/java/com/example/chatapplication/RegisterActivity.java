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

public class RegisterActivity extends AppCompatActivity {

    private Button registerButton;
    private EditText UserEmail,UserPassword;
    private TextView alreadyAccount;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth=FirebaseAuth.getInstance();

        InitializeFields();
        alreadyAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendUserToTheLoginActivity();
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateNewAccount();
            }
        });
    }

    private void CreateNewAccount() {
       String email= UserEmail.getText().toString().trim();
       String password=UserPassword.getText().toString().trim();


        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please Fill the Fields Properly", Toast.LENGTH_SHORT).show();
        }
        else{
            loadingBar.setTitle("Creating New Account");
            loadingBar.setMessage("Please wait while creating new Account");
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.show();
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        SendUserToTheLoginActivity();
                        Toast.makeText(RegisterActivity.this,"Account Created Successfully",Toast.LENGTH_SHORT).show();

                    }
                    else{
                        String error=task.getException().toString();
                        Toast.makeText(RegisterActivity.this,"Error: "+error,Toast.LENGTH_SHORT).show();
                    }
                    loadingBar.dismiss();
                }
            });
        }
    }


    private void SendUserToTheLoginActivity() {
        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
    }

    private void InitializeFields() {
        registerButton=findViewById(R.id.register_button);
        UserEmail=findViewById(R.id.register_email);
        UserPassword=findViewById(R.id.register_password);
        alreadyAccount=findViewById(R.id.already_have_account);
        loadingBar=new ProgressDialog(this);
    }
}