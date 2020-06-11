package com.example.chatapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    final String TAG="Main Activity";
    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private TabAccessorAdapter mTabAccessorAdapter;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate method");
        setContentView(R.layout.activity_main);
        mToolbar=findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Whats App");
        mAuth=FirebaseAuth.getInstance();
        currentUser=mAuth.getCurrentUser();

        mViewPager=findViewById(R.id.main_tabs_pager);
        mTabAccessorAdapter=new TabAccessorAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mTabAccessorAdapter);
        mTabLayout=findViewById(R.id.main_tabs);
        mTabLayout.setupWithViewPager(mViewPager);
    }


    @Override
    protected void onStart() {
        Log.d(TAG,"onStart method");
        super.onStart();
        // if user is not authenticated.... then its calls
        if(currentUser==null){
            SendUserToLoginActivity();
        }
    }

    private void SendUserToLoginActivity() {
        Log.d(TAG,"SendUserToLoginActivity");
        startActivity(new Intent(MainActivity.this,LoginActivity.class));
    }
}