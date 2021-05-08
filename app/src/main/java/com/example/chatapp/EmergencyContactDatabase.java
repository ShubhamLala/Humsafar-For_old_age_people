package com.example.chatapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class EmergencyContactDatabase extends SQLiteOpenHelper {
    public EmergencyContactDatabase(@Nullable Context context) {
        super(context, "EmergencyContact", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists Contacts(ID integer primary key autoincrement,name text,phonenumber text,pincode text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public boolean insertValues(String val1,String val2,String val3){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",val1);
        contentValues.put("phonenumber",val2);
        contentValues.put("pincode",val3);
        long result = database.insert("Contacts",null,contentValues);
        if (result==-1) return false;
        else return true;
    }
    public Cursor getAddingData(String val){
        SQLiteDatabase database = this.getWritableDatabase();
        return database.rawQuery("select * from Contacts where name='"+val+"'",null);

    }
    public Cursor getAllData(){
        SQLiteDatabase database = this.getWritableDatabase();
        return database.rawQuery("select * from Contacts",null);
    }
    public void deleteData(String val){
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("delete from Contacts where name='"+val+"'");
    }
    public Cursor getCallingNumber(String val){
        SQLiteDatabase database = this.getWritableDatabase();
        return database.rawQuery("select * from Contacts where pincode='"+val+"'",null);
    }
}
