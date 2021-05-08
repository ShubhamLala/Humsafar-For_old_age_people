package com.example.chatapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class Calling extends AppCompatActivity {

    CallContactDatabase callContactDatabase = new CallContactDatabase(this);
    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> numbers = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calling);
        Toolbar toolbar = findViewById(R.id.include3);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        Cursor cursor = callContactDatabase.getAllData();
        if(cursor.getCount() == 0) {
            Toast.makeText(this, "No Contact Added", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this,AddingContact.class));
        }else{
            while (cursor.moveToNext()){
                names.add(cursor.getString(1));
                numbers.add(cursor.getString(2));
            }
        }
        RecyclerView recyclerView = findViewById(R.id.CallingContactsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CallingContactRecyclerAdapter(names,numbers,this));
    }
}
