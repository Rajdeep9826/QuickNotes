package com.example.quicknotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.Query;

public class MainActivity extends AppCompatActivity {
FloatingActionButton add_notes_btn;
RecyclerView recyclerView;
ImageButton menubtn;

NoteAdapter noteAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add_notes_btn=findViewById(R.id.add_note_btn);
        recyclerView=findViewById(R.id.recycler_view);
        menubtn=findViewById(R.id.menu_btn);


        add_notes_btn.setOnClickListener(v-> startActivity(new Intent(MainActivity.this,Add_Notes.class)));

      menubtn.setOnClickListener(v-> showMenu());

  setupRecyclerView();
    }

    void showMenu(){

        PopupMenu popupMenu=new PopupMenu(MainActivity.this,menubtn);

        popupMenu.getMenu().add("Logout");
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getTitle()=="Logout"){

                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(MainActivity.this,Login_Activity.class));
                    finish();
                    return true;
                }

                return false;
            }
        });

    }

    void setupRecyclerView(){

       Query query= utility.getCollectionReferenceForNotes().orderBy("timestamp",Query.Direction.DESCENDING);
       FirestoreRecyclerOptions<Note>options=new FirestoreRecyclerOptions.Builder<Note>().setQuery(query,Note.class).build();

       recyclerView.setLayoutManager(new LinearLayoutManager(this) );
       noteAdapter=new NoteAdapter(options,this);
       recyclerView.setAdapter(noteAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        noteAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        noteAdapter.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        noteAdapter.notifyDataSetChanged();
    }
}