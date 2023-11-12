package com.example.project2;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ReminderViewHolder extends RecyclerView.ViewHolder {
    TextView dest;
    TextView leaveTime;
    TextView arriveTime;
    TextView id;
    TextView deleteBtn;
    public ReminderViewHolder(@NonNull View itemView) {
        super(itemView);
        dest = itemView.findViewById(R.id.destination);
        leaveTime = itemView.findViewById(R.id.leave_time);
        arriveTime = itemView.findViewById(R.id.arrive_time);
        id = itemView.findViewById(R.id.reminder_id);
        deleteBtn = itemView.findViewById(R.id.delete);
    }
}
