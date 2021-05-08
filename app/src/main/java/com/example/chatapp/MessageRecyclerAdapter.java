package com.example.chatapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class MessageRecyclerAdapter extends RecyclerView.Adapter<MessageRecyclerAdapter.MessageHolder> {
    ArrayList<ChatDetails> chatDetails;
    Context context;
    String imageUrl;
    int type;

    public MessageRecyclerAdapter(ArrayList<ChatDetails> chatDetails, Context context,String imageUrl) {
        this.chatDetails = chatDetails;
        this.context = context;
        this.imageUrl = imageUrl;
    }

    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        type = viewType;
        if(viewType == 0) view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_right,parent,false);
        else view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_left,parent,false);
        return new MessageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageHolder holder, int position) {
        holder.showmessage.setText(chatDetails.get(position).getChats());
        if (type == 1){
        if(imageUrl.equals("default")) holder.profile.setImageResource(R.mipmap.ic_launcher);
        else Glide.with(context).load(imageUrl).into(holder.profile);
    }
    }

    @Override
    public int getItemCount() {
        return chatDetails.size();
    }

    public class MessageHolder extends RecyclerView.ViewHolder {
        TextView showmessage;
        CircleImageView profile;
        public MessageHolder(@NonNull View itemView) {
            super(itemView);
            showmessage = itemView.findViewById(R.id.showMessage);
            profile = itemView.findViewById(R.id.leftProfileImage);
        }
    }

    @Override
    public int getItemViewType(int position) {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        if(firebaseUser.getUid().equals(chatDetails.get(position).getSender())) return 0;
        return 1;

    }
}
