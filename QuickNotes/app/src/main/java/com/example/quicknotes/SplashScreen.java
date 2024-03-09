package com.example.quicknotes;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



@SuppressLint("CustomSplashScreen")
public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();

                    if(currentUser==null){

                        Intent intent=new Intent(SplashScreen.this, Login_Activity.class);
                        startActivity(intent);

                    }else {
                        Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                    }
                }
            },2500);
        }

    }
