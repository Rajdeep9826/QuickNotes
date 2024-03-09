package com.example.quicknotes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;

public class utility {

    static void showToast(Context context, String message){
        Toast.makeText(context,message, Toast.LENGTH_SHORT).show();

    }

    static CollectionReference getCollectionReferenceForNotes(){

        FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();
      return   FirebaseFirestore.getInstance().collection("notes")
              .document(currentUser.getUid()).collection("my_notes");
    }
 @SuppressLint("SimpleDateFormat")
 static  String timestampTostring(Timestamp timestamp){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp.toDate());
 }

}

