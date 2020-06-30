package com.example.chatapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {

    private Button updateSettingButton;
    private EditText userName,userStatus;
    private CircleImageView userProfileImage;
    private String currentUserId;
    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initializeFields();
        mAuth=FirebaseAuth.getInstance();
        currentUserId=mAuth.getCurrentUser().getUid();
        RootRef= FirebaseDatabase.getInstance().getReference();

        updateSettingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateSettings();
            }
        });

        RetriveUserInfo();
    }

    private void RetriveUserInfo() {
        RootRef.child("User").child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if( (dataSnapshot.exists()) && (dataSnapshot.hasChild("name")) && (dataSnapshot.hasChild("image")) ){
                    String retrivedName=dataSnapshot.child("name").getValue().toString();
                    String retrivedStatus=dataSnapshot.child("status").getValue().toString();
//                    String retrivedProfileImage=dataSnapshot.child("image").getValue().toString();
                    userName.setText(retrivedName);
                    userStatus.setText(retrivedStatus);
                }else if((dataSnapshot.exists()) && (dataSnapshot.hasChild("name"))) {

                    String retrivedName=dataSnapshot.child("name").getValue().toString();
                    String retrivedStatus=dataSnapshot.child("status").getValue().toString();
                    userName.setText(retrivedName);
                    userStatus.setText(retrivedStatus);

                }else{
                    Toast.makeText(SettingsActivity.this,"Please set your name and image and status",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void UpdateSettings() {
        String setUserName=userName.getText().toString();
        String setUserStatus=userStatus.getText().toString();

        if(TextUtils.isEmpty(setUserName)){
            Toast.makeText(this,"Please Write your UserName....",Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(setUserStatus)){
            Toast.makeText(this,"Please Write your Status...",Toast.LENGTH_SHORT).show();
        }else{
            HashMap<String,String>profileMap=new HashMap<>();
            profileMap.put("uid",currentUserId);
            profileMap.put("name",setUserName);
            profileMap.put("status",setUserStatus);
            RootRef.child("User").child(currentUserId).setValue(profileMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                         SendUserToMainActivity();
                         Toast.makeText(SettingsActivity.this,"Profile Update",Toast.LENGTH_SHORT).show();
                    }else{
                        String message=task.getException().toString();
                        Toast.makeText(SettingsActivity.this,"ERROR: "+message,Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    private void SendUserToMainActivity() {
        Intent intent=new Intent(SettingsActivity.this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        finish();
    }

    private void initializeFields() {
        updateSettingButton=findViewById(R.id.settings_button);
        userName=findViewById(R.id.editTextTextPersonName);
        userStatus=findViewById(R.id.editTextTextPersonName2);
        userProfileImage=findViewById(R.id.circleImageView);
    }
}