package com.example.chatapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class CallContactDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "CallContact.db";
    public CallContactDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists Contacts(ID integer primary key autoincrement,Name text,PhoneNumber text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public boolean insertData(String val1,String val2){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name",val1);
        contentValues.put("PhoneNumber",val2);
        long result = database.insert("Contacts",null,contentValues);
        if (result==-1) return false;
        else return true;
    }
    public Cursor getAddingData(String val){
        SQLiteDatabase database = this.getWritableDatabase();
        return database.rawQuery("select * from Contacts where Name='"+val+"'",null);

    }
    public Cursor getAllData(){
        SQLiteDatabase database = this.getWritableDatabase();
        return database.rawQuery("select * from Contacts",null);
    }
    public void deleteData(String val){
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("delete from Contacts where Name='"+val+"'");
    }
}
