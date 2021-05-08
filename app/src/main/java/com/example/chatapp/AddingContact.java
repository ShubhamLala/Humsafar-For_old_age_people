package com.example.chatapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddingContact extends AppCompatActivity {

    String name="";
    String number="";
    String pin="";
    CallContactDatabase callContactDatabase = new CallContactDatabase(this);
    EmergencyContactDatabase emergencyContactDatabase = new EmergencyContactDatabase(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_contact);
        Toolbar toolbar = findViewById(R.id.include2);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        final EditText nameEdit = findViewById(R.id.contactName);
        final EditText numberEdit = findViewById(R.id.contactNumber);
        final EditText pinEdit = findViewById(R.id.pincodeContact);
        Button submit = findViewById(R.id.addContactButton);
        Button delete = findViewById(R.id.deleteContactButton);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameEdit.getText().toString();
                number = numberEdit.getText().toString();
                pin = pinEdit.getText().toString();
                if(name.equals(""))
                    Toast.makeText(AddingContact.this, "Name Cannot be empty", Toast.LENGTH_LONG).show();
                else if(number.equals(""))
                    Toast.makeText(AddingContact.this, "Phone Number cannot be empty", Toast.LENGTH_LONG).show();
                else if(number.length()!=10)
                    Toast.makeText(AddingContact.this, "Invalid Number", Toast.LENGTH_SHORT).show();
                else{
                    if(pin.equals("")){
                        Cursor cursor = callContactDatabase.getAddingData(name);
                        if(cursor.getCount() == 0){
                            if(callContactDatabase.insertData(name,number))
                                Toast.makeText(AddingContact.this, "Successful", Toast.LENGTH_SHORT).show();
                            else Toast.makeText(AddingContact.this, "Unsuccessful", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(AddingContact.this, "Name Already Added", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Cursor cursor = callContactDatabase.getAddingData(name);
                        if(cursor.getCount() == 0){
                            if(callContactDatabase.insertData(name,number)){
                                emergencyContactDatabase.insertValues(name,number,pin);
                                Toast.makeText(AddingContact.this, "Successful", Toast.LENGTH_SHORT).show();
                            }
                            else Toast.makeText(AddingContact.this, "Unsuccessful", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(AddingContact.this, "Name Already Added", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameEdit.getText().toString();
                number = numberEdit.getText().toString();
                pin = pinEdit.getText().toString();
                if(name.equals(""))
                    Toast.makeText(AddingContact.this, "Name is required", Toast.LENGTH_LONG).show();
                else {
                    callContactDatabase.deleteData(name);
                    emergencyContactDatabase.deleteData(name);
                    Toast.makeText(AddingContact.this, "Successful", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
