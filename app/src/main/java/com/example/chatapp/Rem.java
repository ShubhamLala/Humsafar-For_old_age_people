package com.example.chatapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Rem extends RecyclerView.Adapter<Rem.RemH>{
    ArrayList<String> arr2 = new ArrayList<>(Arrays.asList("1.Fatty fish","2.Leafy greens","3.Cinnamon","4.Eggs","5.Chia Seeds","6.Turmeric","7.Yogurt","8.Nuts","9.Broccoli","10.Extra-Virgin olive oil","11.Flax Seeds","12.Applicider","13.Stawberries","14.Garlic","15.Squash"));
    @NonNull
    @Override
    public RemH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_layout,viewGroup,false);
        return new RemH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RemH remH, int i) {
        remH.textView.setText(arr2.get(i));

    }

    @Override
    public int getItemCount() {
        return arr2.size();
    }

    public class RemH extends RecyclerView.ViewHolder {
        TextView textView;
        public RemH(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.recyclerTextView);
        }
    }

}
