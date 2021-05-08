package com.example.chatapp;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class CalendarPart extends AppCompatActivity {
    ArrayList<CalendarDay> dates = new ArrayList<>();
    CalendarDatabase calendarDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_part);
        Toolbar toolbar = findViewById(R.id.calendarInclude);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        calendarDatabase = new CalendarDatabase(this);
        DateFormat dateFormat  = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        String newDate = dateFormat.format(date);
        String currYear = newDate.substring(0,4);
        String currMonth = newDate.substring(5,7);
        String currDay = newDate.substring(8);
        String trying = currYear+"-"+currMonth+"-"+currDay;
        final EditText addEventEdit = findViewById(R.id.addingEventEdit);
        final Button addingEventButton = findViewById(R.id.addingEventButton);
        final Button deleteEventButton = findViewById(R.id.deleteEventButton);
        Button viewEventButton = findViewById(R.id.viewEventButton);
        final MaterialCalendarView mvc = findViewById(R.id.mvc);
        addDeco(mvc);
        initial(mvc,trying);
        mvc.setCurrentDate(CalendarDay.from(Integer.parseInt(currYear),Integer.parseInt(currMonth),Integer.parseInt(currDay)),true);
        mvc.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull final CalendarDay date, boolean selected) {
                addEventEdit.setHint(Integer.toString(date.getDay())+"/"+Integer.toString(date.getMonth())+"/"+Integer.toString(date.getYear()));
                Cursor checkCursor = calendarDatabase.getSpecificData(Integer.toString(date.getDay()),Integer.toString(date.getMonth()),Integer.toString(date.getYear()));
                if(checkCursor.getCount()!=0){
                    checkCursor.moveToNext();
                    Toast.makeText(CalendarPart.this, checkCursor.getString(1), Toast.LENGTH_LONG).show();
                }
                checkCursor.close();
                addingEventButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(addEventEdit.getText().toString().equals(""))
                            Toast.makeText(CalendarPart.this, "Add Event Description", Toast.LENGTH_SHORT).show();
                        else if(calendarDatabase.getSpecificData(Integer.toString(date.getDay()),Integer.toString(date.getMonth()),Integer.toString(date.getYear())).getCount() != 0)
                            Toast.makeText(CalendarPart.this, "Event Already added", Toast.LENGTH_SHORT).show();
                        else {
                            calendarDatabase.insertData(addEventEdit.getText().toString(),Integer.toString(date.getDay()),Integer.toString(date.getMonth()),Integer.toString(date.getYear()));
                            Toast.makeText(CalendarPart.this, "Event Added", Toast.LENGTH_SHORT).show();
                            addEventEdit.setText("");
                            addDeco(mvc);
                        }
                    }
                });
                deleteEventButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(calendarDatabase.getSpecificData(Integer.toString(date.getDay()),Integer.toString(date.getMonth()),Integer.toString(date.getYear())).getCount() != 0){
                            calendarDatabase.deleteDetail(Integer.toString(date.getDay()),Integer.toString(date.getMonth()),Integer.toString(date.getYear()));
                            addDeco(mvc);
                            Toast.makeText(CalendarPart.this, "Delete Successful", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(CalendarPart.this, "No Event Added", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        viewEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CalendarPart.this,ListingEvents.class));
            }
        });
    }
    public void addDeco(MaterialCalendarView materialCalendarView){
        materialCalendarView.addDecorator(new DayViewDecorator() {
            @Override
            public boolean shouldDecorate(CalendarDay day) {
                Cursor cursor = calendarDatabase.getAllData();
                dates.clear();
                while(cursor.moveToNext()){
                    dates.add(CalendarDay.from(Integer.parseInt(cursor.getString(4)),Integer.parseInt(cursor.getString(3)),Integer.parseInt(cursor.getString(2))));
                }
                cursor.close();
                return dates.contains(day);
            }

            @Override
            public void decorate(DayViewFacade view) {
                view.addSpan(new DotSpan(5,Color.GREEN));
            }
        });
    }
    public void initial(MaterialCalendarView materialCalendarView, final String best){
        materialCalendarView.addDecorator(new DayViewDecorator() {
            @Override
            public boolean shouldDecorate(CalendarDay day) {
                return false;
            }

            @Override
            public void decorate(DayViewFacade view) {
                view.addSpan(new DotSpan(15,Color.RED));
            }
        });
    }

}
