package com.fishstar.mytodolist.beans;

import android.graphics.Color;

public enum Priority {

    Letter(0,0xffdaeaef);

    public  final int intValue;
    public  final int color;
    Priority(int intValue,int color){
        this.color=color;
        this.intValue=intValue;
    }
    public static Priority from(int intValue){
        for(Priority priority:Priority.values()){
            if(priority.intValue == intValue){
                return priority;
            }
        }
        return Priority.Letter;
    }
}
