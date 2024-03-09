package com.example.quicknotes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import io.grpc.okhttp.internal.Util;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class NoteAdapter extends FirestoreRecyclerAdapter<Note,NoteAdapter.NoteViewHolder> {


   Context context;

    public NoteAdapter(@NonNull FirestoreRecyclerOptions<Note> options,Context context) {
        super(options);
        this.context=context;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position, @NonNull Note note) {

        holder.titletextView.setText(note.title);
        holder.contentTextView.setText(note.content);
        holder.timestamptextView.setText(utility.timestampTostring(note.timestamp));

        holder.itemView.setOnClickListener(v->{

            Intent intent=new Intent(context,Add_Notes.class);
            intent.putExtra("title",note.title);
            intent.putExtra("content",note.content);

            String docId=this.getSnapshots().getSnapshot(position).getId();
            intent.putExtra("docId",docId);

            context.startActivity(intent);
        });

    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view,parent,false);
        return new NoteViewHolder(view);
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder{

        TextView titletextView, contentTextView, timestamptextView;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            titletextView = itemView.findViewById(R.id.note_title_text_view);
            contentTextView = itemView.findViewById(R.id.notes_content_text_view);
            timestamptextView = itemView.findViewById(R.id.note_timestamp_text_view);
        }
    }

}
