package com.example.chatapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CallingContactRecyclerAdapter extends RecyclerView.Adapter<CallingContactRecyclerAdapter.VHolder> {
    ArrayList<String> names;
    ArrayList<String> numbers;
    Context context;
    public CallingContactRecyclerAdapter(ArrayList<String> names, ArrayList<String> numbers,Context context) {
        this.names = names;
        this.numbers = numbers;
        this.context = context;
    }


    @NonNull
    @Override

    public VHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calling_contact_recycler_layout,parent,false);
        return new VHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VHolder holder, int position) {
        holder.textView.setText(Integer.toString(position+1)+". "+names.get(position));
        holder.textView2.setText(numbers.get(position));

    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public class VHolder extends RecyclerView.ViewHolder {
        TextView textView,textView2;
        LinearLayout linearLayout;
        public VHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.callingContactsRecyclerTextView);
            textView2 = itemView.findViewById(R.id.numberRecycler);
            linearLayout = itemView.findViewById(R.id.callLayout);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+numbers.get(getAdapterPosition())));
                    context.startActivity(intent);
                }
            });
        }
    }
}
