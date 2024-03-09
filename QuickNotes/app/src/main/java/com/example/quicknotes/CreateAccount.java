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



public class CreateAccount extends AppCompatActivity {

    EditText email, passwpord, confirmpassword;
    Button create;
    TextView login;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        email = findViewById(R.id.edit_text_email);
        passwpord = findViewById(R.id.edit_text_password);
        confirmpassword = findViewById(R.id.edit_text_Confirmpassword);
      //  login = findViewById(R.id.textview_login);
        create = findViewById(R.id.btn_create);

        create.setOnClickListener(v -> createAccount());
       // login.setOnClickListener(v -> login());

    }


//    void login(){
 //        Intent intent =new Intent(CreateAccount.this,MainActivity.class);
 //        startActivity(intent);
   // }

    void createAccount() {

        String emails = email.getText().toString();
        String passwords = passwpord.getText().toString();
        String confirm  = confirmpassword.getText().toString();

        boolean isvalidate=validateData(emails,passwords,confirm);

        if(!isvalidate){
            return;
        }

        //

        createAccountInFirebase(emails,passwords);



    }

    void createAccountInFirebase(String emails,String passwords){

        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(emails,passwords).addOnCompleteListener(CreateAccount.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    utility.showToast(CreateAccount.this, "successful create account,Check email to verify");

                    firebaseAuth.getCurrentUser().sendEmailVerification();
                    firebaseAuth.signOut();

                    Intent intent=new Intent(CreateAccount.this,Login_Activity.class);
                    startActivity(intent);
                    
                }
                else
                {

                    utility.showToast(CreateAccount.this,task.getException().getLocalizedMessage());


                }


            }
        });

    }





    boolean validateData(String emails, String passwords, String confirm) {

        if (!Patterns.EMAIL_ADDRESS.matcher(emails).matches()) {
            email.setError("Email is invalid");
            return false;
        }
        if(passwpord.length()<6){
            passwpord.setError("password length is invalid");
            return false;

        }

        if(!passwords.equals(confirm)){

            confirmpassword.setError("password not matched");
            return false;

        }

        return true;

    }
}
