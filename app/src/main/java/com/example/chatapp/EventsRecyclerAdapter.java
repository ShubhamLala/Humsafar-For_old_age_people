package com.example.chatapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EventsRecyclerAdapter extends RecyclerView.Adapter<EventsRecyclerAdapter.VHolder> {
    ArrayList<String> eventDescription;
    ArrayList<String> dates;

    public EventsRecyclerAdapter(ArrayList<String> eventDescription, ArrayList<String> dates) {
        this.eventDescription = eventDescription;
        this.dates = dates;
    }

    @NonNull
    @Override
    public VHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.showing_events_recycler_layout,parent,false);
        return new VHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VHolder holder, int position) {
        holder.description.setText(Integer.toString(position+1)+". "+eventDescription.get(position));
        holder.year.setText(dates.get(position));
    }

    @Override
    public int getItemCount() {
        return eventDescription.size();
    }

    public class VHolder extends RecyclerView.ViewHolder {
        TextView description,year;
        public VHolder(@NonNull View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.descriptionRecyclerText);
            year = itemView.findViewById(R.id.yearRecyclerText);
        }
    }
}
