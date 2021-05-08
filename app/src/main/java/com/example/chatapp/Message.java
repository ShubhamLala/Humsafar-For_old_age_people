package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Message extends AppCompatActivity {
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    CircleImageView circleImageView;
    EditText message;
    FloatingActionButton send;
    TextView displayToUser;
    ArrayList<ChatDetails> chatDetail = new ArrayList<>();
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        Toolbar toolbar = findViewById(R.id.messageToolBar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        message = findViewById(R.id.messageToSend);
        send = findViewById(R.id.sendButton);
        circleImageView = findViewById(R.id.messageProfileImage);
        displayToUser = findViewById(R.id.messageProfileUsername);
        Intent intent = getIntent();
        recyclerView = findViewById(R.id.messageRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        final String userId = intent.getStringExtra("userid");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(userId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Details details = dataSnapshot.getValue(Details.class);
                assert details != null;
                displayToUser.setText(details.getUsername());
                if(details.getImageURL().equals("default")) circleImageView.setImageResource(R.mipmap.ic_launcher);
                else Glide.with(getApplicationContext()).load(details.getImageURL()).into(circleImageView);
                readMessage(firebaseUser.getUid(),userId,details.getImageURL());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = message.getText().toString();
                if(!text.equals("")){
                    sendMessage(firebaseUser.getUid(),userId,text);
                    message.setText("");
                }else{
                    Toast.makeText(Message.this, "Empty Message", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


    public void sendMessage(String sender , String receiver, String chat){
        reference = FirebaseDatabase.getInstance().getReference();
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("sender",sender);
        hashMap.put("receiver",receiver);
        hashMap.put("chats",chat);
        reference.child("Chats").push().setValue(hashMap);
    }
    public void readMessage(final String senderid, final String myid, final String imageUrl){
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chatDetail.clear();
                for (DataSnapshot mySnapshot : dataSnapshot.getChildren()){
                    ChatDetails chatDetails = mySnapshot.getValue(ChatDetails.class);
                    assert chatDetails != null;
                    if((chatDetails.getReceiver().equals(myid) && chatDetails.getSender().equals(senderid)) || chatDetails.getReceiver().equals(senderid) && chatDetails.getSender().equals(myid)){
                        chatDetail.add(chatDetails);
                    }
                }
                recyclerView.setAdapter(new MessageRecyclerAdapter(chatDetail,getApplicationContext(),imageUrl));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Message.this,MainChatActivity.class));
    }
}
