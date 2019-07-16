package com.fishstar.mytodolist.Note;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fishstar.mytodolist.Interface.NoteOperator;
import com.fishstar.mytodolist.R;
import com.fishstar.mytodolist.beans.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteViewHolder> {
    private final NoteOperator operator;
    private final List<Note> notes = new ArrayList<>();

    public  NoteAdapter(NoteOperator operator){this.operator=operator;}

    public  void refresh(List<Note> newNotes){
        notes.clear();
        if(newNotes!=null){
            Log.d("noteAdapter",newNotes.size()+"");
            notes.addAll(newNotes);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item,parent,false);
        return new NoteViewHolder(itemView,operator);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder,int position){
        holder.bind(notes.get(position));
    }

    @Override
    public  int getItemCount(){return notes.size();}
}
