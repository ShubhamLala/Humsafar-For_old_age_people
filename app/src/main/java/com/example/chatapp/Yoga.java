package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Yoga extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button cp;
        Button mp;
        Button cop;
        Button bp;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yoga);
        cp=findViewById(R.id.cp);
        mp=findViewById(R.id.mp);
        cop=findViewById(R.id.cop);
        bp=findViewById(R.id.bp);

    }
    public void clickevent(View view)
    {
        String tagName = view.getTag().toString();
        Intent intent = new Intent(this,YogaVideo.class);
        intent.putExtra("yogaName",tagName);
        startActivity(intent);
    }
}
