package com.fishstar.mytodolist.Note;

import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.fishstar.mytodolist.Interface.NoteOperator;
import com.fishstar.mytodolist.R;
import com.fishstar.mytodolist.beans.Note;
import com.fishstar.mytodolist.beans.State;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NoteViewHolder extends RecyclerView.ViewHolder {
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT =
            new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss", Locale.ENGLISH);
    private final NoteOperator operator;

    private CheckBox cBox;
    private TextView contentText;
    private TextView dateText;
    private View deleteBtn;

    public NoteViewHolder(@NonNull View itemView , NoteOperator operator){
        super(itemView);
        this.operator=operator;

        cBox =itemView.findViewById(R.id.checkBox);
        contentText =itemView.findViewById(R.id.note_content);
        dateText = itemView.findViewById(R.id.note_date);
        deleteBtn = itemView.findViewById(R.id.deleteBtn);
    }

    public void bind(final Note note){

        contentText.setText(note.getContent());
        contentText.setOnLongClickListener((v)->{
            note.setState(State.DONE);
            operator.updateNote(note);
            return true;
        });
        dateText.setText(SIMPLE_DATE_FORMAT.format(note.getDate()));

        cBox.setOnCheckedChangeListener(null);
        cBox.setChecked(note.getState()==State.DONE);
        cBox.setOnCheckedChangeListener((buttonView,isChecked)->{
            Log.d("checkBox","changed:"+isChecked);

            note.setState(isChecked?State.DONE:State.TODO);
            operator.updateNote(note);
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operator.deleteNote(note);
            }
        });
        if(note.getState()==State.DONE){
            contentText.setTextColor(Color.GRAY);
            contentText.setPaintFlags(contentText.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        }
        else{
            contentText.setTextColor(Color.BLACK);
            contentText.setPaintFlags(contentText.getPaintFlags()&~Paint.STRIKE_THRU_TEXT_FLAG);
        }
        itemView.findViewById(R.id.colorLayout).setBackgroundColor(note.getPriority().color);
    }

}
