package com.example.chatapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class CalendarDatabase extends SQLiteOpenHelper {
    public CalendarDatabase(@Nullable Context context) {
        super(context, "Calendar", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists eventInfo(id integer primary key autoincrement,description text,date text,month text,year text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void insertData(String title,String date,String month,String year){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("description",title);
        contentValues.put("date",date);
        contentValues.put("month",month);
        contentValues.put("year",year);
        database.insert("eventInfo",null,contentValues);
    }
    public Cursor getAllData(){
        SQLiteDatabase database = this.getWritableDatabase();
        return database.rawQuery("select * from eventInfo",null);
    }
    public Cursor getSpecificData(String date,String month,String year){
        SQLiteDatabase database = this.getWritableDatabase();
        return database.rawQuery("select * from eventInfo where date='"+date+"' and month='"+month+"' and year='"+year+"'",null);
    }
    public void deleteDetail(String date,String month,String year){
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("delete from eventInfo where date='"+date+"' and month='"+month+"' and year='"+year+"'");
    }
}
