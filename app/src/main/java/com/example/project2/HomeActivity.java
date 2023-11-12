package com.example.project2;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;

import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import android.Manifest;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class HomeActivity extends AppCompatActivity implements OnMapReadyCallback {
    private List<Store> stores = new ArrayList<>();
    private StoreActivity storeActivity = new StoreActivity();
    private RemindersActivity remindersActivity = new RemindersActivity();
    private LocationRequest.Builder locationRequestBuilder;
    private LocationRequest locationRequest;
    private double latitude;
    private double longitude;
    private boolean inStore;
    GoogleMap gMap;
    List<Reminder> reminders = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState){
        inStore = false;
        longitude = 0;
        latitude = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initStores();

        RecyclerView rView = findViewById(R.id.store_recycler);
        rView.setLayoutManager(new LinearLayoutManager(this));
        rView.setAdapter(new StoreAdapter(getApplicationContext(), stores));

        locationRequestBuilder = new LocationRequest.Builder(5000);
       // locationRequest = LocationRequest.Builder.build();
        locationRequest = locationRequestBuilder.build();
        getCurrentLocation();
        RecyclerView remindersView = findViewById(R.id.reminders_recycler);
        remindersView.setLayoutManager(new LinearLayoutManager(this));
        remindersView.setAdapter(new ReminderAdapter(getApplicationContext(), reminders));

        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.google_map);
        mapFragment.getMapAsync(this);

        displayAllReminders();
        remindUserNow();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel chan = new NotificationChannel("myChannel", "myChannel", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(chan);
        }
    }

    public void remindUserNow(){
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        int timeToFood = remindersActivity.calculateTime(10, 5);
        for(int i = 0; i < reminders.size(); i++){
           if((reminders.get(i).arrivalMinute - timeToFood == minute) && (reminders.get(i).arrivalHour == hour)){
               notifyUser("Leave for " + reminders.get(i).dest.title + " now!");
           }
        }
    }



    public void notifyUser(String contentTitle){

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "myChannel")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(contentTitle)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(1, builder.build());
    }
    public void displayReminderHelper(List<Reminder> reminders){
        RecyclerView rView = findViewById(R.id.reminders_recycler);
        rView.setLayoutManager(new LinearLayoutManager(this));
        rView.setAdapter(new ReminderSmallAdapter(getApplicationContext(), reminders));
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
                reminders.clear();
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
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
//        Log.i("HomeActivity.java", "map ready");
        gMap = googleMap;
        //TODO: get user's current location
        //we have lat and long
        
    }

   /* public static DirectionsApiRequest getDirections(
            GeoApiContext context, String origin, String destination) {
        return new DirectionsApiRequest(context).origin(origin).destination(destination);
    }*/

    //navigation bar methods start
    public void toHomeActivity(View view){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    public void toLoginActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void toReminderActivity(View view){
        Intent intent = new Intent(this, RemindersActivity.class);
        startActivity(intent);
    }
    /**
     * TODO
     * Function to log out and bring user back to signup/login page
     * @param view
     */
    public void handleLogout(View view){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        String current = "";
        if(mAuth.getCurrentUser() == null){
            current = "NULL";
        }
        else{
            current += mAuth.getCurrentUser().getUid();
        }
        Log.d("sign out","Signed out, current user = "+mAuth.getCurrentUser());
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    //navigation bar methods end

    private void initStores(){
        for(String key: storeActivity.storeLocs.keySet()){
            StoreActivity store = new StoreActivity(key);
            Log.d("opentimes", store.getOpenTime_asString());
            stores.add(new Store(R.drawable.cava, store.getOpenTime_asString(), store.getCloseTime_asString(), store.getStoreName(), store.getCurrentWaitTime()));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){

                if (isGPSEnabled()) {

                    getCurrentLocation();

                }else {

                    turnOnGPS();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {

                getCurrentLocation();
            }
        }
    }

    private void getCurrentLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                if (isGPSEnabled()) {
                    LocationServices.getFusedLocationProviderClient(HomeActivity.this)
                            .requestLocationUpdates(locationRequest, new LocationCallback() {
                                // @Override
                                public void onLocationResult(@NonNull LocationResult locationResult) {
                                    super.onLocationResult(locationResult);

                                    LocationServices.getFusedLocationProviderClient(HomeActivity.this)
                                            .removeLocationUpdates(this);
                                    if (locationResult != null && locationResult.getLocations().size() > 0) {

                                        int index = locationResult.getLocations().size() - 1;
                                        latitude = locationResult.getLocations().get(index).getLatitude();
                                        longitude = locationResult.getLocations().get(index).getLongitude();
                                        Log.d("LATITUDE", Double.toString(latitude));
                                        Log.d("LONGITUDE", Double.toString(longitude));

                                        //loop through all stores
                                        Set<String> keys = storeActivity.storeLocs.keySet();
                                        for(String store : storeActivity.storeLocs.keySet()){
                                            FirebaseDatabase db = FirebaseDatabase.getInstance();
                                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                            if(user==null){
                                                Log.d("","User null! Back to login.");
                                                toLoginActivity();
                                                return;
                                            }
                                            DatabaseReference storeRef = db.getReference("stores/"+store);
                                            //see if user already in store
                                            inStore = false;
                                            storeRef.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    String ref = storeRef.toString() + "/"+user.getUid().toString();
                                                    DataSnapshot snap = snapshot.child(user.getUid().toString());
                                                    //CHECK IF THIS USER ALREADY IN STORE
                                                    if(snap.exists()){
                                                        Log.d("numcustomers","User in store, ref: "+ref);
                                                        inStore = true;
                                                    }
                                                    else{
                                                        //Log.d("numcustomers","User not in store");
                                                    }

                                                    //CHECK FOR CURRENT LOCATION, ADD OR REMOVE FROM STORE'S CUSTOMER LIST
                                                    boolean userAtStore = isUserAtStore(store);
                                                    //Log.d("stores","User at store "+store+" = "+userAtStore);
                                                    if(userAtStore){
                                                        if(!inStore){
                                                            storeRef.child(user.getUid()).setValue("True")
                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            if(task.isSuccessful()){
                                                                                Log.d("DB","Added user to store!");
                                                                            }
                                                                            else{
                                                                                Log.d("DB","Error, user not added to store");
                                                                            }
                                                                        }
                                                                    });
                                                        }
                                                    }
                                                    else{
                                                        //check if user at store - if no longer at store, remove
                                                        if(inStore){
                                                            storeRef.child(user.getUid()).removeValue()
                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            if(task.isSuccessful()){
                                                                                Log.d("","Removed user from store!");
                                                                            }
                                                                            else{
                                                                                Log.d("","Error: did not remove user from store");
                                                                            }
                                                                        }
                                                                    });
                                                        }
                                                    }

                                                    //UPDATE REMINDER WAIT TIME
                                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                                    if(user==null){
                                                        toLoginActivity();
                                                        return;
                                                    }
                                                    DatabaseReference remRef = db.getReference("users/"+user.getUid()+"/reminders");
                                                    if(remRef!=null){
                                                        //iterate thru reminders
                                                        remRef.addValueEventListener(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                for(DataSnapshot ds : snapshot.getChildren()){
                                                                    String remStore = ds.child("dest").child("title").getValue(String.class);

                                                                    remStore = remStore.toLowerCase(Locale.ROOT);
                                                                    Log.d("remStore","remStore = "+remStore+" Store = "+store);
                                                                    if(remStore.equals(store)){
                                                                        storeRef.addValueEventListener(new ValueEventListener() {
                                                                            @Override
                                                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                                long numUsersInStore = snapshot.getChildrenCount();
                                                                                ds.child("waitTime").getRef().setValue(numUsersInStore * 2)
                                                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                            @Override
                                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                                if(task.isSuccessful()){
                                                                                                    Log.d("","Added wait time");
                                                                                                }
                                                                                                else{
                                                                                                    Log.d("","Failed to add wait time");
                                                                                                }
                                                                                            }
                                                                                        });
                                                                            }

                                                                            @Override
                                                                            public void onCancelled(@NonNull DatabaseError error) {

                                                                            }
                                                                        });
                                                                    }
                                                                }
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError error) {

                                                            }
                                                        });
                                                    }

                                                }
                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {}
                                            });


                                        }
                                    }
                                }
                            }, Looper.getMainLooper());
                } else {
                    turnOnGPS();
                }

            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
    }

    private boolean isUserAtStore(String storeName){
        Double lat = storeActivity.storeLat.get(storeName);
        Double lng = storeActivity.storeLong.get(storeName);
        Double lngDist = Math.pow(longitude - storeActivity.storeLong.get(storeName),2);
        Double latDist = Math.pow(latitude - storeActivity.storeLat.get(storeName),2);
        Double dist = Math.sqrt(lngDist+latDist);
        //Log.d("stores","Store name: "+storeName+"("+lat+","+lng+")");
        //Log.d("stores","My: ("+latitude+","+longitude+")");
        //Log.d("stores","Dist = "+dist);
        final double INSTORE_RADIUS = .0001;
        if(dist < INSTORE_RADIUS){
            Log.d("stores","Returning true for "+storeName);
            return true;
        }
        return false;
    }

    private void turnOnGPS() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(getApplicationContext())
                .checkLocationSettings(builder.build());

        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {

                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    Toast.makeText(HomeActivity.this, "GPS is already tured on", Toast.LENGTH_SHORT).show();

                } catch (ApiException e) {

                    switch (e.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                            try {
                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                                resolvableApiException.startResolutionForResult(HomeActivity.this, 2);
                            } catch (IntentSender.SendIntentException ex) {
                                ex.printStackTrace();
                            }
                            break;

                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            //Device does not have location
                            break;
                    }
                }
            }
        });

    }

    private boolean isGPSEnabled() {
        LocationManager locationManager = null;
        boolean isEnabled = false;

        if (locationManager == null) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        }

        isEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isEnabled;

    }



}

