package com.example.project2;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StoreAdapter extends RecyclerView.Adapter<StoreViewHolder> {
    Context context;
    List<Store> stores;
    public StoreAdapter(Context context, List<Store> stores) {
        this.context = context;
        this.stores = stores;
    }

    @NonNull
    @Override
    public StoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StoreViewHolder(LayoutInflater.from(context).inflate(R.layout.store_card_design, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StoreViewHolder holder, int position) {
        Store s = stores.get(position);
        holder.nameView.setText(s.getTitle());
        holder.estTimeView.setText(s.getEstimatedWaitTime());
//        holder.hoursView.setText(s.getHoursAsString());
//        holder.imageView.setImageResource(s.getImage());

        /**
         * when a user clicks on a store/restaurant, display the route from the user's
         * location to that store
         */
        holder.nameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(context, StoreActivity.class); //view store details
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //add this line
//                intent.putExtra("store", s);
//                context.startActivity(intent);
                Log.i("StoreAdapter", "clicked on" + s.getTitle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return stores.size();
    }
}
