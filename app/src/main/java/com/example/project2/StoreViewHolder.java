package com.example.project2;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StoreViewHolder extends RecyclerView.ViewHolder {
//    ImageView imageView;
    TextView nameView, estTimeView;

    public StoreViewHolder(@NonNull View itemView){
        super(itemView);
//        imageView = itemView.findViewById(R.id.imageView);
        nameView = itemView.findViewById(R.id.name);
        estTimeView = itemView.findViewById(R.id.estimated_wait_time);
//        hoursView = itemView.findViewById(R.id.hours);
    }
}
