package com.example.quicknotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

public class Add_Notes extends AppCompatActivity {

    EditText titleeditText,contenteditText;
    Button savebtn;

    TextView pageTitle,deleteNoteTextview;

    String title,content,docId;

    boolean isEditMode=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);
        titleeditText=findViewById(R.id.notes_title_text);
        contenteditText=findViewById(R.id.notes_content_text);
        savebtn=findViewById(R.id.btn_save);
        pageTitle=findViewById(R.id.page_title);
        deleteNoteTextview=findViewById(R.id.Delete_note_text_view);


        title=getIntent().getStringExtra("title");
        content=getIntent().getStringExtra("content");
        docId=getIntent().getStringExtra("docId");

        if(docId!=null && !docId.isEmpty()){
            isEditMode=true;

        }

        titleeditText.setText(title);
        contenteditText.setText(content);

        if(isEditMode){
            pageTitle.setText("Edit Your Note");
            deleteNoteTextview.setVisibility(View.VISIBLE);
        }


        savebtn.setOnClickListener(v-> savenotes());

        deleteNoteTextview.setOnClickListener(v-> deletenoteFromfirebase());

    }
    void savenotes(){

        String noteTitle=titleeditText.getText().toString();
        String noteContent=contenteditText.getText().toString();

        if(noteTitle==null || noteTitle.isEmpty()){
            titleeditText.setError("Title is Required");
            return;
        }

        Note note=new Note();
        note.setTitle(noteTitle);
        note.setContent(noteContent);
        note.setTimestamp(Timestamp.now());

        saveToFirebase(note);
    }

    void saveToFirebase(Note note) {

        DocumentReference documentReference;
        if (isEditMode) {
            documentReference = utility.getCollectionReferenceForNotes().document(docId);
        } else{

            documentReference = utility.getCollectionReferenceForNotes().document();
        }

        documentReference.set(note).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    utility.showToast(Add_Notes.this,"Note added successfully");
                    finish();
                }else {

                    utility.showToast(Add_Notes.this,"Failed while adding notes");
                }
            }
        });

    }

    void deletenoteFromfirebase(){

        DocumentReference documentReference;

            documentReference = utility.getCollectionReferenceForNotes().document(docId);


        documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    utility.showToast(Add_Notes.this,"Note deleted successfully");
                    finish();
                }else {

                    utility.showToast(Add_Notes.this,"`Failed while deleting note");
                }
            }
        });

    }

}