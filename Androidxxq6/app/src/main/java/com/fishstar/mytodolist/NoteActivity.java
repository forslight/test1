package com.fishstar.mytodolist;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.fishstar.mytodolist.beans.Priority;
import com.fishstar.mytodolist.beans.State;
import com.fishstar.mytodolist.db.TodoContract;
import com.fishstar.mytodolist.db.TodoDBManager;

public class NoteActivity extends AppCompatActivity {

    private EditText eText;
    private Button addBtn;
    private RadioGroup RG;
    private RadioButton RBtn;

    private TodoDBManager dbManager;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        setTitle(R.string.add_a_note);

        dbManager = new TodoDBManager(this);
        database = dbManager.getWritableDatabase();

        //绑定控件
        eText = findViewById(R.id.eText);


        addBtn = findViewById(R.id.add);

        eText.setFocusable(true);
        eText.requestFocus();
        RBtn.setChecked(true);
        //
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if(inputManager!=null){
            inputManager.showSoftInput(eText,0);
        }





        addBtn.setOnClickListener((v)->{
            CharSequence text = eText.getText();
            if(TextUtils.isEmpty(text)){
                Toast.makeText(NoteActivity.this,
                        "the content is empty!",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            boolean succeed = saveNote2Database(text.toString().trim(),
                    GetSelectedPriority());
            if(succeed) {
                Toast.makeText(NoteActivity.this, "Note Was Created Successfully!", Toast.LENGTH_SHORT).show();
                setResult(Activity.RESULT_OK);
            }
            else {
                Toast.makeText(NoteActivity.this, "Failed to Create New Note!", Toast.LENGTH_SHORT).show();
            }
            finish();
        });
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        database.close();
        database=null;
        dbManager.close();
        dbManager=null;
    }

    private boolean saveNote2Database(String content , Priority priority){
        if(database==null||TextUtils.isEmpty(content)){
            return false;
        }
        ContentValues values = new ContentValues();
        values.put(TodoContract.TodoNote.COLUMN_CONTENT,content);
        values.put(TodoContract.TodoNote.COLUMN_STATE, State.TODO.intValue);
        values.put(TodoContract.TodoNote.COLUMN_DATE,System.currentTimeMillis());
        values.put(TodoContract.TodoNote.COLUMN_PRIORITY,priority.intValue);
        long rowId=database.insert(TodoContract.TodoNote.TABLE_NAME,null,values);
        return rowId!=-1;
    }

    private Priority GetSelectedPriority(){

                return Priority.Letter;

    }

}
