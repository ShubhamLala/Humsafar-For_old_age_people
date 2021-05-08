package com.example.chatapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Normal extends AppCompatActivity {
    RecyclerView re;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal);
        re = findViewById(R.id.recyclerView);
        re.setLayoutManager(new LinearLayoutManager(this));
        re.setAdapter(new RecyclerAdapter());

    }
}
