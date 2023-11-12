package com.example.project2;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
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

import java.sql.Time;
//import org.threeten.bp.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RemindersActivity extends AppCompatActivity {
//    private int id;
//    private int leaveTime;
//    private int arrivalTime;
    private StoreActivity dest;
    List<Reminder> reminders = new ArrayList<>();
    public List<Store> stores = new ArrayList<Store>(); //nia
    Spinner storeSpinner;
    Spinner minSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);
        Intent intent = getIntent();
        initStores();
        initSpinners();
        //get reminders that have already been set previously from DB
        displayAllReminders();
    }
    private void initSpinners(){
        storeSpinner = findViewById(R.id.spinner_store);
        minSpinner = findViewById(R.id.spinner_minutes);
        ArrayAdapter<Store> adapter = new ArrayAdapter<Store>(this, android.R.layout.simple_spinner_item, stores);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        storeSpinner.setSelection(0);
        storeSpinner.setAdapter(adapter);
        List<Integer> minutes = new ArrayList<Integer>();
        for(int i = 0; i<60; i++){
            minutes.add((Integer)i);
        }
        ArrayAdapter<Integer> minuteAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, minutes);
        minSpinner.setAdapter(minuteAdapter);
        displayAllReminders();

    }
    private void initStores(){
        stores.add(new Store(R.drawable.cava, 10, 0, 20, 45, "Cava"));
        stores.add(new Store(R.drawable.chinese_street_food, 10, 0, 20, 0, "Chinese Street Food"));
        stores.add(new Store("Amazon"));
        stores.add(new Store("Bank of America"));
        stores.add(new Store("City Tacos"));
        stores.add(new Store("Corepower Yoga"));
        stores.add(new Store("CVS"));
        stores.add(new Store("Dulce"));
        stores.add(new Store("FedEx"));
        stores.add(new Store("Fruit + Candy"));
        stores.add(new Store("Greenleaf"));
        stores.add(new Store("Honeybird"));
        stores.add(new Store("Il Giardino"));
        stores.add(new Store("Insomnia Cookies"));
        stores.add(new Store("Kaitlyn"));
        stores.add(new Store("Kobunga"));
        stores.add(new Store("Lululemon"));
        stores.add(new Store("Mac Repair"));
        stores.add(new Store("Ramen Kenji"));
        stores.add(new Store("Sammiche"));
        stores.add(new Store("Sole Bicycles"));
        stores.add(new Store("Starbucks"));
        stores.add(new Store("Stout"));
        stores.add(new Store("Sunlife Organics"));
        stores.add(new Store("Target"));
        stores.add(new Store("Simply Nail Bar"));
        stores.add(new Store("Trader Joes"));
        stores.add(new Store("Village Cobbler"));
    }
    public void displayReminderHelper(List<Reminder> reminders){
        RecyclerView rView = findViewById(R.id.reminders_recycler);
        rView.setLayoutManager(new LinearLayoutManager(this));
        rView.setAdapter(new ReminderAdapter(getApplicationContext(), reminders));
    }

    public void displayAllReminders(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user==null){
            return;
        }
        DatabaseReference reminderRef = db.getReference("users/"+user.getUid()+"/reminders");
        reminderRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Reminder> reminders = new ArrayList<>();
                for(DataSnapshot rems : snapshot.getChildren()){
                    Reminder rem = rems.getValue(Reminder.class);
                    reminders.add(rem);
                }
                displayReminderHelper(reminders);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public boolean validateReminder(StoreActivity dest, int arrivalTime){
        if(arrivalTime >= dest.getCloseTime_asInt() || arrivalTime < dest.getOpenTime_asInt()) return false;
        return true;
    }


    public int calculateTime(int travel, int waitTime){
        return travel + waitTime;
    }

    public StoreActivity getDest(){ return dest; }


    /**
     * TODO
     * Function to log out and bring user back to signup/login page
     * @param view
     */
    public void handleLogout(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void addReminderToDB(View view,Reminder reminder){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user==null){
            //if not logged in, redirect to signup
            handleLogout(view);
        }
        DatabaseReference userRems = db.getReference("users/"+user.getUid()
                +"/reminders");
        userRems.push().setValue(reminder)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d("reminder","Added reminder!");
                    }
                });
    }

    public void createReminder(View view){
        int hour, min; //time you want to arrive at the store by
        Spinner AMPMSpinner = (Spinner) findViewById(R.id.spinner_ampm);
        Spinner hourSpinner = (Spinner) findViewById(R.id.spinner_hour);
        hour = Integer.valueOf(hourSpinner.getSelectedItem().toString());
        if(AMPMSpinner.getSelectedItem().equals("PM")) hour += 12;
//        Spinner minSpinner = (Spinner) findViewById(R.id.spinner_minutes);
        min = Integer.valueOf(minSpinner.getSelectedItem().toString());
        Store store = (Store)storeSpinner.getSelectedItem();
        Reminder r = new Reminder(hour, min, store);
        //TODO: validate reminder
        //add reminder to database
        addReminderToDB(view,r);
        Intent intent = new Intent(this, RemindersActivity.class);
        intent.putExtra("reminder", r);
        startActivity(intent);
    }
    //navigation bar methods start
    public void toHomeActivity(View view){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
    public void toReminderActivity(View view){
        Intent intent = new Intent(this, RemindersActivity.class);
        startActivity(intent);
    }

    //navigation bar methods end
}
