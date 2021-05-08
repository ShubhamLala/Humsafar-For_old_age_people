package com.example.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class Diet extends AppCompatActivity {
    EditText fast,eat;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet);
        Toolbar toolbar = findViewById(R.id.include);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        fast=findViewById(R.id.editText);
        eat=findViewById(R.id.editText2);
        btn=findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int f,e;
                f=Integer.parseInt(fast.getText().toString());
                e=Integer.parseInt(eat.getText().toString());
                if(f<91 && e<140){
                    Toast.makeText(Diet.this, "Congratulation You are on safe side", Toast.LENGTH_SHORT).show();
                    Intent intent =new Intent(Diet.this,Normal.class);
                    startActivity(intent);
                }
                else if((f>=91 && f<=99) || (e>=140 && e<=180)){
                    Toast.makeText(Diet.this, "Warning! You are in border", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Diet.this,Medium.class);
                    startActivity(intent);

                }
                else{
                         Toast.makeText(Diet.this, "You need attention to your Health!!", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(Diet.this,High.class);
                        startActivity(intent);
            }
            }
        });
    }
}
