package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import de.hdodenhof.circleimageview.CircleImageView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainChatActivity extends AppCompatActivity {

    FirebaseUser user;
    DatabaseReference reference;
    CircleImageView profilePic;
    TextView profileUsername;
    CircleImageView signout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chat);
        Toolbar toolbar = findViewById(R.id.mainToolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        TabLayout tabLayout = findViewById(R.id.mainTabLayout);
        ViewPager viewPager = findViewById(R.id.mainViewPager);
        viewPager.setAdapter(new MainViewPagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
        signout = findViewById(R.id.signoutApp);
        profilePic = findViewById(R.id.messageProfileImage);
        user = FirebaseAuth.getInstance().getCurrentUser();
        profileUsername = findViewById(R.id.messageProfileUsername);
        reference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Details details = dataSnapshot.getValue(Details.class);
                assert details != null;
                if(details.getImageURL().equals("default")) profilePic.setImageResource(R.mipmap.ic_launcher);
                else Glide.with(MainChatActivity.this).load(details.getImageURL()).into(profilePic);
                profileUsername.setText(details.getUsername());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),StartActivity.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Logout before exiting", Toast.LENGTH_SHORT).show();
    }
}
