package com.example.quicknotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login_Activity extends AppCompatActivity {
EditText email,password;
Button Login;
TextView signin;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email=findViewById(R.id.edit_text_email1);
        password=findViewById(R.id.edit_text_password1);
        Login=findViewById(R.id.btn_login);
        signin=findViewById(R.id.textview_signin);

        Login.setOnClickListener(v-> loginUser());
        signin.setOnClickListener(V->startActivity(new Intent(Login_Activity.this, CreateAccount.class)));



    }

    void loginUser(){

        String emails = email.getText().toString();
        String passwords = password.getText().toString();


        boolean isvalidate=validateData(emails,passwords);

        if(!isvalidate){
            return;
        }

        //

        loginAccountInFirebase(emails,passwords);

    }


    void loginAccountInFirebase(String emails,String passwords) {


        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(emails,passwords).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    if (firebaseAuth.getCurrentUser().isEmailVerified()) {

                        startActivity(new Intent(Login_Activity.this,MainActivity.class));
                        finish();

                    } else {
                        utility.showToast(Login_Activity.this, "Email not verified,please verify your email");
                    }
                } else {
                    utility.showToast(Login_Activity.this, task.getException().getMessage());
                }
            }
        });

    }



    boolean validateData(String emails, String passwords) {

        if (!Patterns.EMAIL_ADDRESS.matcher(emails).matches()) {
            email.setError("Email is invalid");
            return false;
        }
        if(password.length()<6){
            password.setError("password length is invalid");
            return false;

        }


        return true;

    }
}
