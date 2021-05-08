package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Devotional extends AppCompatActivity {
    MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devotional);

    }
    public void play(View v){
        if(player==null){
            switch (v.getId()){
                case R.id.ganesh:
                    player=MediaPlayer.create(this,R.raw.ganesh);
                    break;
                case R.id.gayatri:
                    player=MediaPlayer.create(this,R.raw.gayatri);
                    break;
                case R.id.hari:
                    player=MediaPlayer.create(this,R.raw.hoho);
                    break;
                case R.id.hanuman:
                    player=MediaPlayer.create(this,R.raw.hanuman);
                    break;
                case R.id.shiv:
                    player=MediaPlayer.create(this,R.id.shiv);
                    break;
            }
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopPlayer();
                }
            });
        }
        player.start();
    }
    public void pause(View v){
        if(player!=null){
            player.pause();

        }
    }
    public void stop(View v){
        stopPlayer();
    }
private void stopPlayer(){
    if(player!=null){
        player.release();
        player=null;
        Toast.makeText(this, "Media Player stopped", Toast.LENGTH_SHORT).show();
    }
}

    @Override
    protected void onStop() {
        super.onStop();
        stopPlayer();
    }
}

