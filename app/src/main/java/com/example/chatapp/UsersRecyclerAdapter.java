package com.example.chatapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class UsersRecyclerAdapter extends RecyclerView.Adapter<UsersRecyclerAdapter.Holder> {
    Context context;
    ArrayList<Details> details;
    ArrayList<String> pic;

    public UsersRecyclerAdapter(Context context, ArrayList<Details> details) {
        this.context = context;
        this.details = details;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_recycler_layout,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        final Details currentDetail = details.get(position);
        holder.textView.setText(currentDetail.getUsername());
        if(currentDetail.getImageURL().equals("default")) holder.circleImageView.setImageResource(R.mipmap.ic_launcher);
        else Glide.with(context).load(currentDetail.getImageURL()).into(holder.circleImageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,Message.class);
                intent.putExtra("userid",currentDetail.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return details.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView textView;
        CircleImageView circleImageView;
        public Holder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.usernameRecyclerProfile);
            circleImageView = itemView.findViewById(R.id.usersRecyclerProfile);
        }
    }
}
