package com.fishstar.mytodolist.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TodoDBManager extends SQLiteOpenHelper {
    private static final String DB_NAME = "todo.db";
    private static final int DB_VERSION = 2;
    public TodoDBManager(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){db.execSQL(TodoContract.SQL_CREATE_NOTE);}

    public void onUpgrade(SQLiteDatabase db, int oldVersion,int newVersion){
        for(int i=oldVersion;i<newVersion;i++){
            switch(i){
                case 1:
                    db.execSQL(TodoContract.SQL_ADD_PRIORITY_COLUMN);
                    break;
            }
        }
    }
}
