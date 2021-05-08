package com.example.chatapp;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class  MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerLayout = findViewById(R.id.drawer);
        Toolbar toolbar = findViewById(R.id.include1);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
       actionBar.setDisplayHomeAsUpEnabled(true);
       actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        NavigationView nav = findViewById(R.id.nav);
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.event:
                        startActivity(new Intent(MainActivity.this,CalendarPart.class));
                        return true;
                    case R.id.addContacts:
                        startActivity(new Intent(MainActivity.this,AddingContact.class));
                        return true;
                    case R.id.callFamily:
                        startActivity(new Intent(MainActivity.this,Calling.class));
                    default:
                        return false;
                }
            }

        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void click(View view){
        switch (view.getId()){
            case R.id.diet:
                startActivity(new Intent(this,Diet.class));
                break;
            case R.id.chat:
                startActivity(new Intent(this,StartActivity.class));
                break;
            case R.id.emergency:
                startActivity(new Intent(this,EmergencyActivity.class));
                break;
            case R.id.yoga:
                startActivity(new Intent(this,Yoga.class));
                break;
            case R.id.recreation:
                startActivity(new Intent(this,Devotional.class));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);

    }
}
