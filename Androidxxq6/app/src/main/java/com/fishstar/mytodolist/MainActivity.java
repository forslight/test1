package com.fishstar.mytodolist;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.fishstar.mytodolist.Interface.NoteOperator;
import com.fishstar.mytodolist.Note.NoteAdapter;
import com.fishstar.mytodolist.beans.Note;
import com.fishstar.mytodolist.beans.Priority;
import com.fishstar.mytodolist.beans.State;
import com.fishstar.mytodolist.db.TodoContract;
import com.fishstar.mytodolist.db.TodoDBManager;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private  static final int REQUEST_CODE_ADD = 1002;
    private RecyclerView recyclerView;
    private NoteAdapter noteAdapter;

    private TodoDBManager dbManager;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbManager = new TodoDBManager(this);
        database = dbManager.getWritableDatabase();


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                startActivityForResult(
                        new Intent(MainActivity.this,NoteActivity.class),
                        REQUEST_CODE_ADD
                );
            }
        });
        recyclerView = findViewById(R.id.todos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,false));
        recyclerView.addItemDecoration(
                new DividerItemDecoration(this,DividerItemDecoration.VERTICAL)
        );
        noteAdapter = new NoteAdapter(new NoteOperator() {
            @Override
            public void deleteNote(Note note) {
                MainActivity.this.deleteNote(note);
            }

            @Override
            public void updateNote(Note note) {
                MainActivity.this.updateNode(note);
            }
        });
        recyclerView.setAdapter(noteAdapter);
        noteAdapter.refresh(loadNotesFromDatabase());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD
                && resultCode == Activity.RESULT_OK) {
            noteAdapter.refresh(loadNotesFromDatabase());
        }
    }



    private List<Note> loadNotesFromDatabase(){
        if(database==null){

            return Collections.emptyList();
        }
        List<Note>result = new LinkedList<>();
        Cursor cursor = null;
        try{
            cursor= database.query(TodoContract.TodoNote.TABLE_NAME,null,
                    null,null,null,null,
                    TodoContract.TodoNote.COLUMN_PRIORITY+" DESC");
            while(cursor.moveToNext()){
                long id = cursor.getLong(cursor.getColumnIndex(TodoContract.TodoNote._ID));
                String content = cursor.getString(cursor.getColumnIndex(TodoContract.TodoNote.COLUMN_CONTENT));
                long dateMs = cursor.getLong(cursor.getColumnIndex(TodoContract.TodoNote.COLUMN_DATE));
                int intState =cursor.getInt(cursor.getColumnIndex(TodoContract.TodoNote.COLUMN_STATE));
                int intPriority = cursor.getInt(cursor.getColumnIndex(TodoContract.TodoNote.COLUMN_PRIORITY));

                Note note = new Note(id);
                note.setState(State.from(intState));
                note.setContent(content);
                note.setDate(new Date(dateMs));
                note.setPriority(Priority.from(intPriority));
                Log.d("Dataload",id+" "+(note.getState().intValue));
                result.add(note);
            }
        }finally {
            if(cursor!=null){
                cursor.close();
            }
        }
        return result;
    }

    private void deleteNote(Note note){
        if(database==null){
            return;
        }
        int rows = database.delete(TodoContract.TodoNote.TABLE_NAME,
                TodoContract.TodoNote._ID+"=?",
                new String[]{String.valueOf(note.id)});
        if(rows>0){
            noteAdapter.refresh(loadNotesFromDatabase());
        }
    }

    private void updateNode(Note note){
        if(database==null){
            Log.d("update","database not exist!");
            return;
        }
        ContentValues values =new ContentValues();
        Log.d("update",""+(note.getState().intValue));
        values.put(TodoContract.TodoNote.COLUMN_STATE,note.getState().intValue);
        int rows = database.update(TodoContract.TodoNote.TABLE_NAME,values,
                TodoContract.TodoNote._ID+"=?",
                new String[]{String.valueOf(note.id)});
        Log.d("update",rows+"");
        if(rows>0){
            noteAdapter.refresh(loadNotesFromDatabase());
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch(id){
            case R.id.action_settings:{
                Toast.makeText(MainActivity.this,"we are developing",Toast.LENGTH_SHORT).show();
                return true;
            }
            case R.id.action_debug:{
                startActivity(new Intent(this,DebugActivity.class));
                return true;
            }
            default:
                break;
        }


        return super.onOptionsItemSelected(item);
    }
}
