package com.example.chatapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Medium extends AppCompatActivity {
    RecyclerView rem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medium);
        rem=findViewById(R.id.rem);
        rem.setLayoutManager(new LinearLayoutManager(this));
        rem.setAdapter(new Rem());
    }
}
