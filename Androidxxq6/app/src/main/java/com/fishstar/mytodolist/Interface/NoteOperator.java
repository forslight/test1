package com.fishstar.mytodolist.Interface;

import com.fishstar.mytodolist.beans.Note;

public interface NoteOperator {
    void deleteNote(Note note);

    void updateNote(Note note);
}
