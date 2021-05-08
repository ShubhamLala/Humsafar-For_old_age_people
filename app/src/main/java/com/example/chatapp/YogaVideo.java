package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.VideoView;

public class YogaVideo extends AppCompatActivity {

    VideoView videoView;
    String yogaName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yoga_video);
        videoView=findViewById(R.id.VideoView);
        Intent intent = getIntent();
        yogaName = intent.getStringExtra("yogaName");
        videoplay();
    }
    public void videoplay()
    {
        switch (yogaName){
            case "cp":
                String videopath="android.resource://com.example.chatapp/"+R.raw.cp;
                Uri uri=Uri.parse(videopath);
                videoView.setVideoURI(uri);
                videoView.start();
                break;
            case "cop":
                String videopath1="android.resource://com.example.chatapp/"+R.raw.cop;
                Uri uri1=Uri.parse(videopath1);
                videoView.setVideoURI(uri1);
                videoView.start();
                break;
            case "mp":
                String videopath2="android.resource://com.example.chatapp/"+R.raw.mp;
                Uri uri2=Uri.parse(videopath2);
                videoView.setVideoURI(uri2);
                videoView.start();
                break;
            case "bp":
                String videopath3="android.resource://com.example.chatapp/"+R.raw.bp;
                Uri uri3=Uri.parse(videopath3);
                videoView.setVideoURI(uri3);
                videoView.start();
                break;
        }
    }
}
