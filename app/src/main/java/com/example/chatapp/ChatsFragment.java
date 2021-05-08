package com.example.chatapp;


import android.os.Bundle;
import android.app.Fragment;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedHashSet;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatsFragment extends androidx.fragment.app.Fragment {

    FirebaseUser firebaseUser;
    DatabaseReference reference;
    ArrayList<String> chatUsers = new ArrayList<>();
    ArrayList<Details> userDetails = new ArrayList<>();
    RecyclerView recyclerView;
    LinkedHashSet<Details> removeDup = new LinkedHashSet<>();
    public ChatsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chats, container, false);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
         recyclerView = view.findViewById(R.id.chatsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot mySnapshot : dataSnapshot.getChildren()){
                    ChatDetails chatDetails = mySnapshot.getValue(ChatDetails.class);
                    assert chatDetails != null;
                    if(chatDetails.getSender().equals(firebaseUser.getUid())){
                        chatUsers.add(chatDetails.getReceiver());
                    }else if(chatDetails.getReceiver().equals(firebaseUser.getUid())){
                        chatUsers.add(chatDetails.getSender());
                    }
                }
                readChats();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;
    }
    public void readChats(){
       DatabaseReference newReference = FirebaseDatabase.getInstance().getReference("Users");
        newReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userDetails.clear();
                for(DataSnapshot myDataSnapshot:dataSnapshot.getChildren()){
                    Details details = myDataSnapshot.getValue(Details.class);
                    for(String id:chatUsers){
                        assert details != null;
                        if(details.getId().equals(id)){
                            userDetails.add(details);
                        }
                    }
                }
                removeDup.addAll(userDetails);
                userDetails.clear();
                userDetails.addAll(removeDup);
                recyclerView.setAdapter(new UsersRecyclerAdapter(getContext(),userDetails));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
