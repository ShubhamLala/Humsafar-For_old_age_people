package com.example.chatapp;

import android.database.Cursor;
import android.os.Bundle;

import java.util.ArrayList;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ListingEvents extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_events);
        Toolbar toolbar = findViewById(R.id.listEventInclude);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        CalendarDatabase calendarDatabase = new CalendarDatabase(this);

        Cursor cursor = calendarDatabase.getAllData();
        ArrayList<String> description = new ArrayList<>();
        ArrayList<String> years = new ArrayList<>();
        while(cursor.moveToNext()){
            description.add(cursor.getString(1));
            years.add(cursor.getString(2)+"/"+cursor.getString(3)+"/"+cursor.getString(4));
        }
        cursor.close();
        RecyclerView recyclerView = findViewById(R.id.showingEventsRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new EventsRecyclerAdapter(description,years));
    }
}
