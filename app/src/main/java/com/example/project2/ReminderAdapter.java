package com.example.project2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderViewHolder> {
    Context context;
    List<Reminder> reminders;

    public ReminderAdapter(Context context, List<Reminder> reminders) {
        this.context = context;
        this.reminders = reminders;
    }

    @NonNull
    @Override
    public ReminderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReminderViewHolder(LayoutInflater.from(context).inflate(R.layout.reminder_card_design, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ReminderViewHolder holder, int position) {
        Reminder r = reminders.get(position);
        holder.dest.setText(r.getDest().getTitle());
        holder.id.setText(r.getId());
        //parse the time to leave
        holder.leaveTime.setText(r.getTimeAsString(r.getLeaveHour(), r.getLeaveMinute())); //TODO: calculate leave time
        holder.arriveTime.setText(r.getTimeAsString(r.getArrivalHour(), r.getArrivalMinute()));

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() { //DELETE REMINDER
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String ref = "users/"+user.getUid()+"/reminders/"+r.getId();
                DatabaseReference remRef = FirebaseDatabase.getInstance().getReference(ref);
                remRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Log.d("reminder",snapshot.getRef().toString());
                        snapshot.getRef().removeValue()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Log.d("reminder","reminder deleted!");
                                        }
                                    }
                                });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return reminders.size();
    }
}
