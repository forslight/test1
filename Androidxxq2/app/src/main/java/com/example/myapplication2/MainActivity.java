package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Adapter1 adapter = new Adapter1();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }
}
